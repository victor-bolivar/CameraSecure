package com.pucp.camerasecure.admin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pucp.camerasecure.R;
import com.pucp.camerasecure.dto.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class admin_solicitudespendientes extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView textView_solicitudID;
    private TextView textView_nombre;
    private TextView textView_email;
    private TextView textView_celular;
    private TextView textView_dni;
    private TextView textView_direccion;

    private FloatingActionButton floatingActionButton_aceptar;
    private FloatingActionButton floatingActionButton_rechazar;

    private int REQUEST_CODE_RECHAZAR_SOLICITUD = 1;
    private int REQUEST_CODE_ACEPTAR_SOLICITUD = 2;

    private List<Usuario> usuariosPendientes;
    private Usuario usuariopendienteActual = null;

    Object mapReference;

    public admin_solicitudespendientes(){
        super(R.layout.fragment_admin_solicitudespendientes);

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        mapReference = this;

        // binding de elementos UI
        textView_solicitudID = view.findViewById(R.id.admin_solicitudespendientes_solicitudid);
        textView_nombre = view.findViewById(R.id.admin_solicitudespendientes_nombre);
        textView_email = view.findViewById(R.id.admin_solicitudespendientes_email);
        textView_celular = view.findViewById(R.id.admin_solicitudespendientes_celular);
        textView_dni = view.findViewById(R.id.admin_solicitudespendientes_dni);
        textView_direccion = view.findViewById(R.id.admin_solicitudespendientes_direccion);
        floatingActionButton_aceptar = view.findViewById(R.id.admin_aceptarsolicitud);
        floatingActionButton_rechazar = view.findViewById(R.id.admin_cancelarsolicitud);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // se obtienen los datos
        DatabaseReference usersRef = mDatabase.child("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usuariosPendientes = new ArrayList<Usuario>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, String> data = (HashMap<String, String>) ds.getValue();
                    if (data.get("rol").equals("Cliente") && data.get("estadoSolicitud").equals("Pendiente")){
                        // se evalua si es una SOLICITUD PENDIENTE
                        usuariosPendientes.add(new Usuario(
                                data.get("nombre"),
                                data.get("dni"),
                                data.get("email"),
                                data.get("celular"),
                                data.get("rol"),
                                data.get("direccionLatitud"),
                                data.get("direccionLongitud"),
                                data.get("direccionNombre"),
                                data.get("estadoSolicitud"),
                                ds.getKey()
                        ));
                    }

                }

                if(usuariosPendientes.size() == 0){
                    // si no hay solicitudes restantes
                    dialogNoHayMasSolicitudesPendientes();

                    // se limpianl los datos
                    usuariopendienteActual = null;
                    textView_solicitudID.setText("No hay mas solicitudes");
                    textView_nombre.setText("");
                    textView_email.setText("");
                    textView_celular.setText("");
                    textView_dni.setText("");
                    textView_direccion.setText("");
                } else {

                    // se pinta el primer elemento
                    usuariopendienteActual = usuariosPendientes.get(0);

                    textView_solicitudID.setText("Solicitud ID:\n"+ usuariopendienteActual.getId());
                    textView_nombre.setText(usuariopendienteActual.getNombre());
                    textView_email.setText(usuariopendienteActual.getEmail());
                    textView_celular.setText(usuariopendienteActual.getCelular());
                    textView_dni.setText(usuariopendienteActual.getDni());
                    textView_direccion.setText(usuariopendienteActual.getDireccionNombre());

                    // maps
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.admin_solicitudespendientes_map);
                    mapFragment.getMapAsync((OnMapReadyCallback) mapReference);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("msg / solicitudesPendie", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);



        //  aceptar solicitud
        floatingActionButton_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuariopendienteActual == null){
                    dialogNoHayMasSolicitudesPendientes();
                } else {
                    Intent intent = new Intent(getContext(), admin_aceptarsolicitud.class);
                    startActivityForResult(intent, REQUEST_CODE_ACEPTAR_SOLICITUD);
                }

            }
        });


        //  rechazar solicitud
        floatingActionButton_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuariopendienteActual == null){
                    dialogNoHayMasSolicitudesPendientes();
                } else {
                    Intent intent = new Intent(getContext(), admin_rechazarsolicitud.class);
                    startActivityForResult(intent, REQUEST_CODE_RECHAZAR_SOLICITUD);
                }


            }
        });

    }

    public void dialogNoHayMasSolicitudesPendientes(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Buen trabajo! No hay mas solicitudes pendientes por revisar");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CODE_RECHAZAR_SOLICITUD){

                String motivo = data.getStringExtra("motivo");

                usuariopendienteActual.setEstadoSolicitud("Rechazado");
                usuariopendienteActual.setMotivoRechazo(motivo);

                // se guarda en solicitudes rechazadas (ya que usa el mismo UID, solo se chancaria en la DB con la info actual)
                mDatabase.child("users").child(usuariopendienteActual.getId()).setValue(usuariopendienteActual);

                // se manda la notificacion (correo + notificacion app) al cliente, indicando que se acepto su solicitud
                notificarCliente("Rechazado");

            } else if (requestCode == REQUEST_CODE_ACEPTAR_SOLICITUD){
                String fecha = data.getStringExtra("fecha");
                String hora = data.getStringExtra("hora");
                Log.d("msg", fecha + " "+ hora);

                usuariopendienteActual.setFechaIstalacion(fecha);
                usuariopendienteActual.setHoraInstalacion(hora);
                usuariopendienteActual.setEstadoSolicitud("Aprobado");

                // se guarda en solicitudes aprobadas (ya que usa el mismo ID, solo se chancaria en la DB con la info actual)
                mDatabase.child("users").child(usuariopendienteActual.getId()).setValue(usuariopendienteActual);

                // se manda la notificacion (correo + notificacion app) al cliente, indicando que se acepto su solicitud
                notificarCliente("Aprobado");


            }

        }

    }

    public void notificarCliente(String resultado){
        // TODO se enviara la notificacion a la app del cliente
        // TODO se enviara el correo al usuario usando el backend microservicios de la app

        if (resultado.equals("aceptado")){

        } else if (resultado.equals("rechazo")){

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // posicion inicial del mapa
        LatLng ubicacionUsuario = new LatLng(Double.parseDouble(usuariopendienteActual.getDireccionLatitud()),Double.parseDouble(usuariopendienteActual.getDireccionLongitud()));
        mMap.addMarker(new MarkerOptions().position(ubicacionUsuario).title("Cliente"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionUsuario));

    }


}