package com.pucp.camerasecure.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pucp.camerasecure.R;

public class admin_rechazarsolicitud extends AppCompatActivity {

    private EditText editText_motivo;
    private Button button_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rechazarsolicitud);

        editText_motivo = findViewById(R.id.admin_rechazarsolicitud_edittext);
        button_guardar = findViewById(R.id.admin_rechazarsolicitud_guardar);
    }

    public void guardarCambios(View view){

        String motivoRechazo = editText_motivo.getText().toString();

        if ( !motivoRechazo.equals("") ){
            Intent intent = new Intent();

            intent.putExtra("motivo", motivoRechazo);

            setResult(RESULT_OK, intent);
            finish();
        } else {
            // alert dialog
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Ingrese un motivo.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}