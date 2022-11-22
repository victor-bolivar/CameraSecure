package com.pucp.camerasecure;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class RegistroObtenerDireccion extends AppCompatActivity implements LocationListener{

    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_direccion);

        // to hide title bar (la vaina que esta arriba que dice el nombre de la app "PrestoPucp")
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        obtenerLocalizacion();
    }

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void obtenerLocalizacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // si no se han dado los permisos
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }
        requestPermissions(LOCATION_PERMS, 101);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                Log.d("msg", String.valueOf(PackageManager.PERMISSION_GRANTED==checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)));

                locationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

                //You can still do this if you like, you might get lucky:
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    retornarHaciaRegistro();

                }
                else{
                    Log.d("msg", "vacios");
                    //This is what you need:
                    locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
                }
        break;
    }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        locationManager.removeUpdates(this);

        ProgressDialog pd = new ProgressDialog(RegistroObtenerDireccion.this);
        pd.setMessage("Cargando tu ubicacion");
        pd.show();
        while (true){
            if(location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                retornarHaciaRegistro();
                pd.dismiss();
                break;
            }
        }

    }

    public void retornarHaciaRegistro(){
        // se crea un nuevo intent
        Intent intent = new Intent();

        //
        intent.putExtra("latitude", String.valueOf(latitude));
        intent.putExtra("longitude", String.valueOf(longitude));
        Log.d("msg", String.valueOf(latitude));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(locationManager != null){
            locationManager.removeUpdates(this);

        }

    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}