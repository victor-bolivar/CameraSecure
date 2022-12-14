package com.pucp.camerasecure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.dto.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private int REQUEST_CODE_GET_LATITUDELONGITUDE = 1;
    private int REQUEST_CODE_GET_LATITUDELONGITUDE_MAPS = 2;

    private TextView textViewDireccion;
    private String longitude = null;
    private String latitude = null;
    private String direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // to hide title bar (la vaina que esta arriba que dice el nombre de la app "PrestoPucp")
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // binding
        textViewDireccion = findViewById(R.id.register_direccion);

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        // SE VERIFICA QUE SI ESTA LOGUEADO, SE CIERRA ESTA ACTIVIDAD
        if (mAuth.getCurrentUser() != null){
            Log.d("msg / usuario logeado", mAuth.getCurrentUser().getEmail());
            finish();
            return;
        }
    }

    public void obtenerDireccion(View view){
        Intent intent = new Intent(this, RegistroObtenerDireccion.class);
        startActivityForResult(intent, REQUEST_CODE_GET_LATITUDELONGITUDE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode== REQUEST_CODE_GET_LATITUDELONGITUDE){
                // ubicacion actual del usuario
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
                Log.d("msg / de gps", String.valueOf(latitude));

                // start el otro intent para mostrar la unicacion en mapa
                Intent intent = new Intent(this, RegistroSeleccionarDireccion.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivityForResult(intent, REQUEST_CODE_GET_LATITUDELONGITUDE_MAPS);

            } else if (requestCode== REQUEST_CODE_GET_LATITUDELONGITUDE_MAPS){
                // ubicacion actual del usuario
                latitude = data.getStringExtra("latitude");
                longitude = data.getStringExtra("longitude");
                direccion = data.getStringExtra("direccion");

                if(direccion.isEmpty()){
                    textViewDireccion.setText(latitude + ", " + longitude);
                } else {
                    textViewDireccion.setText(direccion);
                }

                Log.d("msg / de maps", String.valueOf(latitude));

            }


        }
    }

    public void registroUsuario(View view){
        EditText editText_nombre = findViewById(R.id.register_nombre);
        EditText editText_apellido = findViewById(R.id.register_dni);
        EditText editText_email = findViewById(R.id.register_email);
        EditText editText_password = findViewById(R.id.register_password);
        EditText editText_celular = findViewById(R.id.register_celular);

        String nombre = editText_nombre.getText().toString();
        String dni = editText_apellido.getText().toString();
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();
        String celular = editText_celular.getText().toString();

        // 1. se verifica que no hayan valores vacios
        if (    nombre.isEmpty() ||
                dni.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                celular.isEmpty() ||
                latitude==null ||
                longitude==null)  {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        // 2. se crea el usuario https://firebase.google.com/docs/auth/android/password-auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // 3. si se crea satifactoriamente, se guarda la info en la real time database
                            if (task.isSuccessful()){

                                // se obtiene fecha actual
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                Date date = new Date();
                                String dateCurrent = String.valueOf(formatter.format(date));

                                // se guarda la info del usuario
                                Usuario user = new Usuario(nombre, dni, email, celular, "Cliente", latitude, longitude, direccion, "Pendiente", FirebaseAuth.getInstance().getCurrentUser().getUid(), dateCurrent);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                // 4. una vez se registra el usuario, se envia la confirmacion por correo
                                                mAuth.getCurrentUser().sendEmailVerification();

                                                // 5. se cierra esta actividad y se vuelve al inicia para que se loguee
                                                finish();
                                                return;
                                            }
                                        });

                            }

                        } else {
                            // si falla, se muestra un Toast
                            Toast.makeText(Registro.this, "Error en el registro", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }


}