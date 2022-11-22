package com.pucp.camerasecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RegistroSeleccionarDireccion extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    Long latitude;
    Long longitude;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_seleccionar_direccion);

        // se cambia el titulo a 'Solicitudes pendientes' ya que es el Fragment por defecto
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Aceptar solicitud");
        }

//        // se obtiene la ubicacion del usuario desde Registro.class
//        Intent intent = getIntent();
//        String stringlatitude = intent.getStringExtra("latitude");
//        String stringlongitude = intent.getStringExtra("longitude");
//
//        latitude = latitude.parseLong(stringlatitude);
//        longitude = longitude.parseLong(stringlongitude);
//
//        Log.d("msg latitud en maps", String.valueOf(latitude));

        latitude = (long) -12.0701382;
        longitude = (long) -77.0791665;

        // maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.registro_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // se coloca los listeners respecto al mapa

        mMap = googleMap;
        this.mMap.setOnMapClickListener(this); // cuando se hace click
        this.mMap.setOnMapLongClickListener(this); // cuando se mantiene presionado

        // posicion inicial del mapa
        LatLng ubicacionInicial = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(ubicacionInicial).title("Tu ubicación actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionInicial));

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latitude = (long) latLng.latitude;
        longitude = (long) latLng.longitude;

        // se actualiza el marcador del mapa
        mMap.clear();
        LatLng nuevaPosicion = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(nuevaPosicion).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nuevaPosicion));

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        latitude = (long) latLng.latitude;
        longitude = (long) latLng.longitude;

        // se actualiza el marcador del mapa
        mMap.clear();
        LatLng nuevaPosicion = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(nuevaPosicion).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nuevaPosicion));
    }

    public void confirmarDireccion(){
        // se crea un nuevo intent
        Intent intent = new Intent();

        // se regresa
        intent.putExtra("latitud", String.valueOf(latitude));
        intent.putExtra("longitud", String.valueOf(longitude));
        setResult(RESULT_OK, intent);
        finish();
    }
}