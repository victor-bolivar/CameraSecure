package com.pucp.camerasecure.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.pucp.camerasecure.R;
import com.pucp.camerasecure.dto.Usuario;

public class admin_mostrarcamara extends AppCompatActivity {

    private String websiteUrl = "rtsp://192.168.0.6:8080/h264_pcm.sdp";
    private ProgressDialog progressDialog;
    VideoView videoView;
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mostrarcamara);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Visualizar cámara");
        }

        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        websiteUrl = usuario.getUrlCamara();

        playerView = findViewById(R.id.admin_exoplayer_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buffering...");
        progressDialog.setCancelable(true);
        playVideo();
    }

    private void playVideo(){
            try {

                simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
                playerView.setPlayer(simpleExoPlayer);
                MediaItem mediaItem = MediaItem.fromUri(websiteUrl);
                simpleExoPlayer.addMediaItem(mediaItem);
                simpleExoPlayer.prepare();
                simpleExoPlayer.play();

            } catch (Exception e){
                progressDialog.dismiss();
            }
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            simpleExoPlayer.pause();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            simpleExoPlayer.pause();
        }


}