package com.pucp.camerasecure.admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pucp.camerasecure.R;
import com.pucp.camerasecure.dto.Usuario;

public class ListaHistorialSolicitudesAdapter extends RecyclerView.Adapter<ListaHistorialSolicitudesAdapter.UsuarioViewHolder> {



    private Usuario[] listaUsuarios; // se pasaria la lista de solicitudes filtradas
    private Context context;

    class UsuarioViewHolder extends RecyclerView.ViewHolder{

        Usuario u;

        public UsuarioViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.admin_historialsolicitudes_item, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = listaUsuarios[position];
        holder.u = usuario;

        // 1. se colocan los datos
        TextView textView_id = holder.itemView.findViewById(R.id.admin_historial_id);
        TextView textView_nombre = holder.itemView.findViewById(R.id.admin_historial_nombre);
        TextView textView_email = holder.itemView.findViewById(R.id.admin_historial_email);
        TextView textView_dni = holder.itemView.findViewById(R.id.admin_historial_dni);
        TextView textView_celular = holder.itemView.findViewById(R.id.admin_historial_celular);
        TextView textView_direccion = holder.itemView.findViewById(R.id.admin_historial_direccion);
        TextView textView_estadosolicitud = holder.itemView.findViewById(R.id.admin_historial_estado);
        TextView textView_label_fechamotivo = holder.itemView.findViewById(R.id.admin_historial_enunciado_fechamotivo);
        TextView textView_valor_fechamotivo = holder.itemView.findViewById(R.id.admin_historial_fechamotivo);
        TextView textView_valor_fecharegistro = holder.itemView.findViewById(R.id.admin_historial_fecharegistro);

        textView_nombre.setText(usuario.getNombre());
        textView_email.setText(usuario.getEmail());
        textView_dni.setText(String.valueOf(usuario.getDni()));
        textView_celular.setText(usuario.getCelular());
        textView_direccion.setText(usuario.getDireccionNombre());
        textView_id.setText("ID: "+usuario.getId());
        textView_valor_fecharegistro.setText(usuario.getFechaHoraRegistro());

        // TODO Valores para estado "Instalado"
        String estadoSolicitud = usuario.getEstadoSolicitud();
        textView_estadosolicitud.setText(estadoSolicitud);

        if(estadoSolicitud.equals("Pendiente")){
            // se oculta los valores extra
            ConstraintLayout.LayoutParams paramsLabel = (ConstraintLayout.LayoutParams) textView_label_fechamotivo.getLayoutParams();
            paramsLabel.height = 0;
            textView_label_fechamotivo.setLayoutParams(paramsLabel);

            ConstraintLayout.LayoutParams paramsValue = (ConstraintLayout.LayoutParams) textView_valor_fechamotivo.getLayoutParams();
            paramsValue.height = 0;
            textView_valor_fechamotivo.setLayoutParams(paramsValue);
        } else if (estadoSolicitud.equals("Aprobado")){
            textView_label_fechamotivo.setText("Fecha de instalación");
            textView_valor_fechamotivo.setText(usuario.getFechaIstalacion()+" "+usuario.getHoraInstalacion());
        } else if (estadoSolicitud.equals("Rechazado")){
            textView_label_fechamotivo.setText("Motivo");
            textView_valor_fechamotivo.setText(usuario.getMotivoRechazo());
        }

//        // 3. onClick para ir a editar
//        ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.uti_dispositivo_fondo);
//        constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, uti_editardispositivo.class);
//                intent.putExtra("dispositivo", dispositivo);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.length;
    }

    // getters and setters

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public Usuario[] getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(Usuario[] listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
