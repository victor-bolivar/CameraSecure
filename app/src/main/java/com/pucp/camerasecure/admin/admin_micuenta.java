package com.pucp.camerasecure.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.R;

import java.util.HashMap;


public class admin_micuenta extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // info del usuario
    private String nombres;
    private String email;
    private String celular;
    private String dni;

    // elementos de UI
    TextView textView_nombre;
    TextView textView_email;
    TextView textView_celular;
    TextView textView_dni;

    public admin_micuenta() {
        super(R.layout.fragment_admin_micuenta);

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // 1. Para mostrar los datos
        textView_nombre = view.findViewById(R.id.admin_micuenta_nombre);
        textView_email = view.findViewById(R.id.admin_micuenta_email);
        textView_celular = view.findViewById(R.id.admin_micuenta_celular);
        textView_dni = view.findViewById(R.id.admin_micuenta_dni);
        cargarDatos();

        // 3. para LOGOUT
        Button btnLogout = view.findViewById(R.id.admin_micuenta_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });

        return view;
    }

    public void cargarDatos(){

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("msg / firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();

                celular = data.get("celular");
                dni = data.get("dni");
                email = data.get("email");
                nombres = data.get("nombres");

                textView_nombre.setText(nombres);
                textView_email.setText(email);
                textView_dni.setText(dni);
                textView_celular.setText(celular);


            }
        });

    }
}