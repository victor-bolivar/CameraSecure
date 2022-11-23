package com.pucp.camerasecure.admin;

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

public class Admin extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 1. creacion del drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.admin_drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 2. se cambia el titulo a 'Solicitudes pendientes' ya que es el Fragment por defecto
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Solicitudes pendientes");
        }

        // 3. listener para cada uno de los items en en menu de navegation
        NavigationView nav  = (NavigationView)findViewById(R.id.admin_nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment = null;
                String title = getString(R.string.app_name);

                switch (item.getItemId()){
                    case R.id.admin_nav_solicitudespendientes:
                        fragment = new admin_solicitudespendientes();
                        title  = "Solicitudes pendientes";
                        break;
                    case R.id.admin_nav_visualizarcamaras:
                        fragment = new admin_visualizarcamaras();
                        title  = "Visualizar cámaras";
                        break;
                    case R.id.admin_nav_estadisticas:
                        fragment = new admin_estadisticas();
                        title  = "Estadísticas";
                        break;
                    case R.id.admin_nav_historialsolicitudes:
                        fragment = new admin_historialsolicitudes();
                        title  = "Historial de solicitudes";
                        break;
                    case R.id.admin_nav_registronuevodispositivo:
                        fragment = new admin_registrardispositivo();
                        title  = "Registrar un nuevo dispositivo";
                        break;
                    case R.id.admin_nav_micuenta:
                        fragment = new admin_micuenta();
                        title  = "Mi cuenta";
                        break;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.admin_fragmentContainerView, fragment);
                ft.commit();

                // set the toolbar title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawerlayout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        // 4. se cambia el nombre del usuario para que salga en el NavDrawer
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("msg / firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();

                String nombreCompleto = data.get("nombres");
                TextView textView_navdrawer_nombre = findViewById(R.id.admin_navdrawer_nombreusuario);
                textView_navdrawer_nombre.setText(nombreCompleto);
                Log.d("msg", nombreCompleto);
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
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}