package com.pucp.camerasecure.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.Inicio;
import com.pucp.camerasecure.R;

import java.util.HashMap;

public class Cliente extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 1. creacion del drawer layout

        drawerLayout = (DrawerLayout) findViewById(R.id.cliente_drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 2. se cambia el titulo a 'Solicitudes pendientes' ya que es el Fragment por defecto
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Estado de solicitud");
        }

        // 3. listener para cada uno de los items en en menu de navegation
        NavigationView nav  = (NavigationView)findViewById(R.id.cliente_nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment = null;
                String title = getString(R.string.app_name);

                switch (item.getItemId()){
                    case R.id.cliente_nav_micuenta:
                        fragment = new cliente_micuenta();
                        title  = "Mi cuenta";
                        break;
                    case R.id.cliente_nav_micamara:
                        fragment = new cliente_micamara();
                        title  = "Mi cámara";
                        break;
                    case R.id.cliente_nav_capturas:
                        fragment = new cliente_capturasvideo();
                        title  = "Capturas de video";
                        break;
                    case R.id.cliente_nav_estadosolicitud:
                        fragment = new cliente_estadosolicitud();
                        title  = "Estado de solicitud";
                        break;

                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.cliente_fragmentContainerView, fragment);
                ft.commit();

                // set the toolbar title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.cliente_drawerlayout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1. se verifica que el usuario este logeado, sino se regresa al activity Inicio
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(this, Inicio.class);
            startActivity(intent);
            finish();
        }

        // 4. se cambia el nombre del usuario para que salga en el NavDrawer
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("msg / firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();

                String nombreCompleto = data.get("nombre");
                Log.d("msg / null", String.valueOf(nombreCompleto));
                if (nombreCompleto!=null){
                    TextView textView_navdrawer_nombre = findViewById(R.id.cliente_navdrawer_nombreusuario);
                    textView_navdrawer_nombre.setText(nombreCompleto);
                    Log.d("msg", nombreCompleto);
                }

            }
        });
    }

    // NavigationDrawer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}