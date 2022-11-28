package com.pucp.camerasecure.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pucp.camerasecure.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class admin_aceptarsolicitud extends AppCompatActivity {

    private TextView textView_fecha;
    private TextView textView_hora;
    private Button  button_fecha;
    private Button  button_hora;

    private String fecha = null;
    private String hora = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_aceptarsolicitud);

        textView_fecha = findViewById(R.id.admin_aceptarsolicitud_fecha);
        textView_hora = findViewById(R.id.admin_aceptarsolicitud_hora);
        button_fecha = findViewById(R.id.admin_aceptarsolicitud_seleccionarfecha);
        button_hora = findViewById(R.id.admin_aceptarsolicitud_seleccionarhora);

    }

    public void mostrarFecha(View view){
        DatePickerDialog d = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                fecha = String.valueOf(year) +"/"+String.valueOf(month+1) +"/"+ String.valueOf(dayOfMonth); // se suma 1 al mes pq diciembre sale como 11 y enero como 0
                textView_fecha.setText(fecha);
            }
        }, 2022, 12, 1);
        d.show();
    }

    public void mostrarHorario(View view){
        TimePickerDialog d = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora = String.valueOf(hourOfDay) +":"+String.valueOf(minute) ;
                textView_hora.setText(hora);
            }
        }, 0, 0, true);
        d.show();
    }

    public void guardarCambios(View view){

        if ( fechaValida(fecha+" "+hora+":00")){
            Intent intent = new Intent();

            intent.putExtra("fecha", fecha);
            intent.putExtra("hora", hora);

            setResult(RESULT_OK, intent);
            finish();
        } else {
            // alert dialog
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Seleccione una fecha y hora válida.");
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

    public static boolean fechaValida(String d1)
    {
        // retorna verdadero si la fecha ingresada es posterior a la actual
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateCurrent = String.valueOf(formatter.format(date));

        try{
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date1 = sdf.parse(d1); // fecha ingresada por el usuario
            Date date2 = sdf.parse(dateCurrent); // fecha actual

            System.out.println("Date1"+sdf.format(date1));
            System.out.println("Date2"+sdf.format(date2));System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if(date1.after(date2)){
                System.out.println("Date1 is after Date2");
                return true;
            }else {
                return false;
            }
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        return false;
    }
}