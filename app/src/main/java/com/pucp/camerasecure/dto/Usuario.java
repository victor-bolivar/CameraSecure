package com.pucp.camerasecure.dto;

public class Usuario {

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
    private String fechaIstalacion;
    private String horaInstalacion;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro, String fechaIstalacion, String horaInstalacion) {
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
        this.fechaIstalacion = fechaIstalacion;
        this.horaInstalacion = horaInstalacion;
    }

    // para cliente con solicitud rechazada
    private String motivoRechazo;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud, String id, String fechaHoraRegistro,String motivoRechazo) {
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

    public String getFechaIstalacion() {
        return fechaIstalacion;
    }

    public void setFechaIstalacion(String fechaIstalacion) {
        this.fechaIstalacion = fechaIstalacion;
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
}
