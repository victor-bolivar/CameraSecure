package com.pucp.camerasecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.admin.Admin;
import com.pucp.camerasecure.cliente.Cliente;

import java.util.HashMap;

public class Inicio extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // to hide title bar (la vaina que esta arriba que dice el nombre de la app "PrestoPucp")
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        vaidarLogin(); // se coloca aca para que no se demore hasta llegar a onResume() para validar el login
    }

    @Override
    protected void onResume() {
        super.onResume();

        vaidarLogin(); // se colca en onResume porsi el usuario, una vez logueado, presiona el boton de retroceder
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity( intent );
    }

    public void irLogin(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity( intent );
    }

    public void vaidarLogin(){
        // SE VERIFICA QUE SI ESTA LOGUEADO -> actividad solicitudes pendientes
        if (mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            Log.d("msg / i usuario logeado", mAuth.getCurrentUser().getUid());

            Log.d("msg / uid", mAuth.getCurrentUser().getUid());

            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("msg / firebase", "Error getting data", task.getException());
                        Toast.makeText(Inicio.this, "Error en obtener información del usuario", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                        HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();
                        String rol = data.get("rol");
                        Intent intent;

                        switch(rol){
                            case "Admin":
                                intent = new Intent(Inicio.this, Admin.class);
                                startActivity( intent );
                                break;

                            case "Cliente":
                                intent = new Intent(Inicio.this, Cliente.class);
                                startActivity( intent );
                                break;
                        }


                    }
                }
            });

        }
    }

}