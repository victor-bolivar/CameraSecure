package com.pucp.camerasecure.cliente;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pucp.camerasecure.R;

import java.util.HashMap;


public class cliente_micamara extends Fragment {

    private String websiteUrl;
    private ProgressDialog progressDialog;
    VideoView videoView;
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public cliente_micamara() {
        super(R.layout.fragment_cliente_micamara);
        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        playerView = view.findViewById(R.id.cliente_exoplayer_view);
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

                String estadoSolicitud = data.get("estadoSolicitud");
                if (!estadoSolicitud.equals("Instalado")){
                    // mensaje indicando al usuario que espere a que su solicitud sea aprobada
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Tu c??mara a??n no ha sido instalada. Por favor, espera a la confirmaci??n del administrador para poder acceder a esta funcionalidad.");
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

                } else {

                    websiteUrl = data.get("urlCamara");
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Buffering...");
                    progressDialog.setCancelable(true);
                    playVideo();
                }


            }
        });

    }

    private void playVideo(){
        try {

            simpleExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            playerView.setPlayer(simpleExoPlayer);
            MediaItem mediaItem = MediaItem.fromUri(websiteUrl);
            simpleExoPlayer.addMediaItem(mediaItem);
            simpleExoPlayer.prepare();
            simpleExoPlayer.play();

        } catch (Exception e){
            progressDialog.dismiss();
        }
    }



}