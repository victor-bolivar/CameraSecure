package com.pucp.camerasecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegistroSeleccionarDireccion extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    Double latitude;
    Double longitude;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_seleccionar_direccion);

        // to hide title bar (la vaina que esta arriba que dice el nombre de la app "PrestoPucp")
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // se obtiene la ubicacion del usuario desde Registro.class
        Intent intent = getIntent();
        String stringlatitude = intent.getStringExtra("latitude");
        String stringlongitude = intent.getStringExtra("longitude");

        latitude = new Double(stringlatitude);
        longitude = new Double(stringlongitude);

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
        latitude =  latLng.latitude;
        longitude = latLng.longitude;

        // se actualiza el marcador del mapa
        mMap.clear();
        LatLng nuevaPosicion = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(nuevaPosicion).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nuevaPosicion));

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        latitude = latLng.latitude;
        longitude =  latLng.longitude;

        // se actualiza el marcador del mapa
        mMap.clear();
        LatLng nuevaPosicion = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(nuevaPosicion).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nuevaPosicion));
    }

    public void confirmarDireccion(View view){
        // se crea un nuevo intent
        Intent intent = new Intent();

        // para obtener el nombre
        String direccion = "";
        Geocoder geocoder = new Geocoder(RegistroSeleccionarDireccion.this, Locale.getDefault());
        try {
            List<Address> listAdress = geocoder.getFromLocation(latitude, longitude, 1);
            if(listAdress.size()>0){
                direccion = listAdress.get(0).getAddressLine(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // se regresa
        intent.putExtra("direccion", direccion);
        intent.putExtra("latitude", String.valueOf(latitude));
        intent.putExtra("longitude", String.valueOf(longitude));
        setResult(RESULT_OK, intent);
        finish();
    }
}