package com.pucp.camerasecure.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.R;
import com.pucp.camerasecure.dto.Usuario;

public class admin_registrarcamara extends AppCompatActivity {

    private EditText editText_url;
    private Button button_guardar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registrarcamara);

        // 2. se cambia el titulo a 'Solicitudes pendientes' ya que es el Fragment por defecto
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Visualizar cámara");
        }

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editText_url = findViewById(R.id.admin_registrocamara_url);
        button_guardar = findViewById(R.id.admin_registrocamara_guardar);
        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");

                String url = editText_url.getText().toString();
                usuario.setUrlCamara(url);
                usuario.setEstadoSolicitud("Instalado");

                // se guarda en solicitudes rechazadas (ya que usa el mismo UID, solo se chancaria en la DB con la info actual)
                mDatabase.child("users").child(usuario.getId()).setValue(usuario);

                finish();

            }
        });
    }
}