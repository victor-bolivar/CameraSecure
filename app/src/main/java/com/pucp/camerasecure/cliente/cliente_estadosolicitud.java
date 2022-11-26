package com.pucp.camerasecure.cliente;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.R;

import java.util.HashMap;


public class cliente_estadosolicitud extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ImageView imageView_progressBar;
    private TextView textView_fechaRegistro;
    private TextView textView_fechaMotivoInstalacion;
    private TextView textView_textoMotivoInstalacion;
    private TextView textView_fechaInstalacion;
    private TextView textView_textoInstalacion;

    public cliente_estadosolicitud() {
        super(R.layout.fragment_cliente_estadosolicitud);
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
        imageView_progressBar = view.findViewById(R.id.cliente_estadosolicitud_progressbar);
        textView_fechaRegistro = view.findViewById(R.id.cliente_estadosolicitud_fecharegistro);
        textView_fechaMotivoInstalacion = view.findViewById(R.id.cliente_estadosolicitud_fechamotivoinstalacion);
        textView_textoMotivoInstalacion = view.findViewById(R.id.cliente_estadosolicitud_motivoinstalacion);
        textView_fechaInstalacion = view.findViewById(R.id.cliente_estadosolicitud_fechainstalacion);
        textView_textoInstalacion = view.findViewById(R.id.cliente_estadosolicitud_instalacion);
        cargarDatos();

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


                textView_fechaRegistro.setText(data.get("fechaHoraRegistro"));

                String estadoSolicitud = data.get("estadoSolicitud");
                switch (estadoSolicitud){
                    case "Pendiente":
                        // se cambia la imagen
                        imageView_progressBar.setImageResource(R.drawable.solicitud_registrada);
                        break;
                    case "Aprobado":
                        // se cambia la imagen
                        imageView_progressBar.setImageResource(R.drawable.solicitud_aprobada);
                        // se coloca el texto
                        textView_fechaMotivoInstalacion.setText(data.get("fechaHoraAprobacionRechazo"));
                        textView_textoMotivoInstalacion.setText("Se ha aprobado tu solicitud. El equipo se movilizara a la direccion indicada la siguiente fecha: "+data.get("fechaInstalacion")+" "+data.get("horaInstalacion"));

                        //se cambia el height del textview
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView_fechaMotivoInstalacion.getLayoutParams();
                        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        textView_fechaMotivoInstalacion.setLayoutParams(params);

                        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) textView_textoMotivoInstalacion.getLayoutParams();
                        params2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        textView_textoMotivoInstalacion.setLayoutParams(params2);

                        break;

                    case "Rechazado":
                        // se cambia la imagen
                        imageView_progressBar.setImageResource(R.drawable.solicitud_rechazada);
                        // se coloca el texto
                        textView_fechaMotivoInstalacion.setText(data.get("fechaHoraAprobacionRechazo"));
                        textView_textoMotivoInstalacion.setText("Se ha rechazado tu solicitud. El administrador ingresó el siguiente motivo: "+data.get("motivoRechazo"));

                        //se cambia el height del textview
                        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) textView_fechaMotivoInstalacion.getLayoutParams();
                        params3.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        textView_fechaMotivoInstalacion.setLayoutParams(params3);

                        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) textView_textoMotivoInstalacion.getLayoutParams();
                        params4.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        textView_textoMotivoInstalacion.setLayoutParams(params4);

                        break;
                    case "Instalado":
                        // TODO
                        break;
                }


            }
        });

    }

}