package com.pucp.camerasecure.dto;

public class Usuario {

    // atributos tanto para usuario Admin como para Cliente
    private String nombre;
    private String dni;
    private String email;
    private String celular;
    private String rol;

    public Usuario(String nombre, String dni, String email, String celular, String rol) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
    }

    // atributos para cliente
    private String direccionLatitud;
    private String direccionLongitud;
    private String direccionNombre;
    private String estadoSolicitud;

    public Usuario(String nombre, String dni, String email, String celular, String rol, String direccionLatitud, String direccionLongitud, String direccionNombre, String estadoSolicitud) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.celular = celular;
        this.rol = rol;
        this.direccionLatitud = direccionLatitud;
        this.direccionLongitud = direccionLongitud;
        this.direccionNombre = direccionNombre;
        this.estadoSolicitud = estadoSolicitud;
    }

    // getters and setter

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
}
