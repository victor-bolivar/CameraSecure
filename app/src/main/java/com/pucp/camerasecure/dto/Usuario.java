package com.pucp.camerasecure.dto;

import java.io.Serializable;

public class Usuario implements Serializable {

    // atributos tanto para usuario Admin como para Cliente
    private String nombre;
    private String dni;
    private String email;
    private String celular;
    private String rol;
    private String id;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String id) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.id = id;
    }

    // atributos para cliente
    private String direccionLatitud;
    private String direccionLongitud;
    private String direccionNombre;
    private String estadoSolicitud;
    private String fechaHoraRegistro;
    private String fechaHoraAprobacionRechazo;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.direccionLatitud = direccionLatitud;
        this.direccionLongitud = direccionLongitud;
        this.direccionNombre = direccionNombre;
        this.estadoSolicitud = estadoSolicitud;
        this.id = id;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    // para cliente con solicitud aprobada
    private String fechaInstalacion;
    private String horaInstalacion;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro, String fechaIstalacion, String horaInstalacion, String fechaHoraAprobacionRechazo) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.direccionLatitud = direccionLatitud;
        this.direccionLongitud = direccionLongitud;
        this.direccionNombre = direccionNombre;
        this.estadoSolicitud = estadoSolicitud;
        this.id = id;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaInstalacion = fechaIstalacion;
        this.horaInstalacion = horaInstalacion;
        this.fechaHoraAprobacionRechazo = fechaHoraAprobacionRechazo;
    }

    // para cliente con solicitud rechazada
    private String motivoRechazo;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro,String motivoRechazo, String fechaHoraAprobacionRechazo) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.direccionLatitud = direccionLatitud;
        this.direccionLongitud = direccionLongitud;
        this.direccionNombre = direccionNombre;
        this.estadoSolicitud = estadoSolicitud;
        this.id = id;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.motivoRechazo = motivoRechazo;
        this.fechaHoraAprobacionRechazo = fechaHoraAprobacionRechazo;
    }

    // para cliente con solicitud Instalada
    private String urlCamara;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro, String fechaIstalacion, String horaInstalacion, String fechaHoraAprobacionRechazo, String urlCamara) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.direccionLatitud = direccionLatitud;
        this.direccionLongitud = direccionLongitud;
        this.direccionNombre = direccionNombre;
        this.estadoSolicitud = estadoSolicitud;
        this.id = id;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaInstalacion = fechaIstalacion;
        this.horaInstalacion = horaInstalacion;
        this.fechaHoraAprobacionRechazo = fechaHoraAprobacionRechazo;
        this.urlCamara = urlCamara;
    }

    // getters and setter

    public String getHoraInstalacion() {
        return horaInstalacion;
    }

    public void setHoraInstalacion(String horaInstalacion) {
        this.horaInstalacion = horaInstalacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(String fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDireccionLatitud() {
        return direccionLatitud;
    }

    public void setDireccionLatitud(String direccionLatitud) {
        this.direccionLatitud = direccionLatitud;
    }

    public String getDireccionLongitud() {
        return direccionLongitud;
    }

    public void setDireccionLongitud(String direccionLongitud) {
        this.direccionLongitud = direccionLongitud;
    }

    public String getDireccionNombre() {
        return direccionNombre;
    }

    public void setDireccionNombre(String direccionNombre) {
        this.direccionNombre = direccionNombre;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(String fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }


    public String getFechaHoraAprobacionRechazo() {
        return fechaHoraAprobacionRechazo;
    }

    public void setFechaHoraAprobacionRechazo(String fechaHoraAprobacionRechazo) {
        this.fechaHoraAprobacionRechazo = fechaHoraAprobacionRechazo;
    }

    public String getUrlCamara() {
        return urlCamara;
    }

    public void setUrlCamara(String urlCamara) {
        this.urlCamara = urlCamara;
    }
}
