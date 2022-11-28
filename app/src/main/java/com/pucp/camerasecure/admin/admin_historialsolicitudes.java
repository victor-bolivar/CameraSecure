package com.pucp.camerasecure.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class admin_historialsolicitudes extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    RecyclerView recyclerView;
    SearchView searchView;

    Button btn_todos;
    Button btn_pendiente;
    Button btn_aprobados;
    Button btn_rechazados;
    Button btn_instalados;
    String filtroActualSearchview = "";
    String filtroActualEstadosolicitud = "";

    public admin_historialsolicitudes() {
        super(R.layout.fragment_admin_historialsolicitudes);
    }

    public void filtroEstadoSolicitud(String estadoSolicitud) {
        filtroActualEstadosolicitud = estadoSolicitud;
        cargarInformacion(filtroActualSearchview, filtroActualEstadosolicitud);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // recycler view binding
        recyclerView = view.findViewById(R.id.admin_historial_recyclerview);
        searchView = view.findViewById(R.id.admin_historial_searchview);

        // search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filtroActualSearchview = s;
                cargarInformacion(s, filtroActualEstadosolicitud); // se colocan string vacios para que al inicio no hayan filtros
                return false;
            }
        });

        // button listeners
        btn_todos = view.findViewById(R.id.admin_historial_estadoTodos);
        btn_aprobados = view.findViewById(R.id.admin_historial_estadoAprobado);
        btn_pendiente = view.findViewById(R.id.admin_historial_estadoPendiente);
        btn_rechazados = view.findViewById(R.id.admin_historial_estadoRechazado);
        btn_instalados = view.findViewById(R.id.admin_historial_estadoInstalado);

        btn_todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroEstadoSolicitud("");
            }
        });
        btn_aprobados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroEstadoSolicitud("Aprobado");
            }
        });
        btn_pendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroEstadoSolicitud("Pendiente");
            }
        });
        btn_rechazados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroEstadoSolicitud("Rechazado");
            }
        });
        btn_instalados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroEstadoSolicitud("Instalado");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // se obtienen los datos
        cargarInformacion(filtroActualSearchview, filtroActualEstadosolicitud); // se colocan string vacios para que al inicio no hayan filtros
    }

    public void cargarInformacion(String busqueda, String estadoSolicitudBuscada){

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    // se define la lista de usuarios a llenar
                    ArrayList<Usuario> arraylistUsuarios = new ArrayList<Usuario>();

                    // 1. se obtiene la data y se filtra por que alguno de los atributos contenga lo ingresado (dni, celular, nombre, etc)
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        if (ds.exists()){ // para evitar encontrar un null
                            // info de cada usuario

                            HashMap<String, String> data = (HashMap<String, String>) ds.getValue();

                            // PARA EVITAR LISTAR ADMINISTRADORES
                            if (data.get("rol").equals("Cliente")){

                                // SE REALIZA EL FILTRADO
                                if( data.get("nombre").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("dni").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("email").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("celular").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("rol").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("direccionLatitud").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("direccionNombre").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        data.get("estadoSolicitud").toLowerCase().contains(busqueda.toLowerCase()) ||
                                        ds.getKey().toLowerCase().contains(busqueda.toLowerCase()) ){

                                    // se guarda el usuario de acuerdo a su rol (cada rol tiene diferentes atributos)
                                    if (data.get("estadoSolicitud").equals("Pendiente") && data.get("estadoSolicitud").contains(filtroActualEstadosolicitud)){
                                        // 1. se evalua si es una SOLICITUD PENDIENTE
                                        arraylistUsuarios.add(new Usuario(
                                                data.get("nombre"),
                                                data.get("dni"),
                                                data.get("email"),
                                                data.get("celular"),
                                                data.get("rol"),
                                                data.get("direccionLatitud"),
                                                data.get("direccionLongitud"),
                                                data.get("direccionNombre"),
                                                data.get("estadoSolicitud"),
                                                ds.getKey(),
                                                data.get("fechaHoraRegistro")
                                        ));
                                    } else if (data.get("estadoSolicitud").equals("Aprobado") && data.get("estadoSolicitud").contains(filtroActualEstadosolicitud)){
                                        // 2. se evalua si es una SOLICITUD Aprobado
                                        arraylistUsuarios.add(new Usuario(
                                                data.get("nombre"),
                                                data.get("dni"),
                                                data.get("email"),
                                                data.get("celular"),
                                                data.get("rol"),
                                                data.get("direccionLatitud"),
                                                data.get("direccionLongitud"),
                                                data.get("direccionNombre"),
                                                data.get("estadoSolicitud"),
                                                ds.getKey(),
                                                data.get("fechaHoraRegistro"),
                                                data.get("fechaInstalacion"),
                                                data.get("horaInstalacion"),
                                                data.get("fechaHoraAprobacionRechazo")
                                        ));
                                    } else if (data.get("estadoSolicitud").equals("Rechazado") && data.get("estadoSolicitud").contains(filtroActualEstadosolicitud)){
                                        // 3. se evalua si es una SOLICITUD Rechazado
                                        arraylistUsuarios.add(new Usuario(
                                                data.get("nombre"),
                                                data.get("dni"),
                                                data.get("email"),
                                                data.get("celular"),
                                                data.get("rol"),
                                                data.get("direccionLatitud"),
                                                data.get("direccionLongitud"),
                                                data.get("direccionNombre"),
                                                data.get("estadoSolicitud"),
                                                ds.getKey(),
                                                data.get("fechaHoraRegistro"),
                                                data.get("motivoRechazo"),
                                                data.get("fechaHoraAprobacionRechazo")
                                        ));
                                    } else if (data.get("estadoSolicitud").equals("Instalado") && data.get("estadoSolicitud").contains(filtroActualEstadosolicitud)){
                                        //  se evalua si es una SOLICITUD Instalado
                                        arraylistUsuarios.add(new Usuario(
                                                data.get("nombre"),
                                                data.get("dni"),
                                                data.get("email"),
                                                data.get("celular"),
                                                data.get("rol"),
                                                data.get("direccionLatitud"),
                                                data.get("direccionLongitud"),
                                                data.get("direccionNombre"),
                                                data.get("estadoSolicitud"),
                                                ds.getKey(),
                                                data.get("fechaHoraRegistro"),
                                                data.get("fechaInstalacion"),
                                                data.get("horaInstalacion"),
                                                data.get("fechaHoraAprobacionRechazo"),
                                                data.get("urlCamara")
                                        ));
                                    }
                                }

                            }

                        }
                    }

                    // se convierte a java array pq asi lo requiere el adapter
                    Usuario[] listaUsuarios = new Usuario[ arraylistUsuarios.size() ];
                    arraylistUsuarios.toArray( listaUsuarios );

                    // se crea el adapter
                    ListaHistorialSolicitudesAdapter adapter = new ListaHistorialSolicitudesAdapter();
                    adapter.setListaUsuarios(listaUsuarios);
                    adapter.setContext(getActivity());

                    // se asigna el adapter al recyclerview
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}