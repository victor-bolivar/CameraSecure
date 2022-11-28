package com.pucp.camerasecure.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucp.camerasecure.R;
import com.pucp.camerasecure.dto.Usuario;

import java.io.ByteArrayOutputStream;

public class admin_registrarcamara extends AppCompatActivity {

    private EditText editText_url;
    private Button button_guardar;
    private ImageView imageView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;

    byte imagenBb[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registrarcamara);

        // 2. se cambia el titulo a 'Solicitudes pendientes' ya que es el Fragment por defecto
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Visualizar cámara");
        }

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // firestorage
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = findViewById(R.id.admin_registrocamara_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        editText_url = findViewById(R.id.admin_registrocamara_url);
        button_guardar = findViewById(R.id.admin_registrocamara_guardar);
        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // se valida que haya una imagen imagen
                if(imagenBb == null){
                    // alert dialog para indicar al usuario que ingrese 3 imagenes como minimo
                    AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(admin_registrarcamara.this);
                    alertdialogBuilder.setMessage("Por favor, ingresar la imagen correspondiente a la instalación de la cámara.");
                    alertdialogBuilder.setCancelable(true);
                    alertdialogBuilder.setPositiveButton(
                            "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = alertdialogBuilder.create();
                    alert11.show();
                    return;
                }

                Intent intent = getIntent();
                Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");

                // se sube la imagen
                ProgressDialog progressDialog = new ProgressDialog(admin_registrarcamara.this);
                progressDialog.setMessage("Guardando...");
                progressDialog.show();

                // se guarda la imagen localmente
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBb, 0, imagenBb.length);
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "camaraInstalada_"+usuario.getId() , "Foto de la instalacion correcta de la camara de seguridad del usuario "+usuario.getNombre()+" con ID: " +usuario.getId());
                // se guarda la imagen en nube
                StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("camarasInstaladas");
                StorageReference imageName = imageFolder.child(usuario.getId()); // nombre a guardar en Fire Storage
                imageName.putBytes(imagenBb)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(admin_registrarcamara.this, "Imagen subida correctamente", Toast.LENGTH_SHORT).show();

                                // se obtiene la URL
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // url imagen
                                        String downloadUrl = uri.toString();
                                        usuario.setCamaraInstaladaImagen(downloadUrl);

                                        // url camara
                                        String url = editText_url.getText().toString();
                                        usuario.setUrlCamara(url);
                                        usuario.setEstadoSolicitud("Instalado");

                                        // se guarda en solicitudes rechazadas (ya que usa el mismo UID, solo se chancaria en la DB con la info actual)
                                        mDatabase.child("users").child(usuario.getId()).setValue(usuario);

                                        progressDialog.dismiss();
                                        finish();

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(admin_registrarcamara.this, "Error al subir imagen.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode ==101){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                imagenBb = bytes.toByteArray();
                imageView.setImageBitmap(thumbnail);

            }
        }
    }
}