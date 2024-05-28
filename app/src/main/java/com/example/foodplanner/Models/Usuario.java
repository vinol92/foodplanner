package com.example.foodplanner.Models;

import java.util.List;

// Clase Usuario
public class Usuario {
    public String nombre;
    public String apellido;
    public String colegiado;
    public String dni;
    public String usuario;
    public String telefono;
    public String email;
    public String contra;
    public String tipousuario;
    public List<String> favoritos;
    public List<String> alergias;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String nombre, String apellido, String colegiado, String dni, String usuario, String telefono, String email, String contra, String tipousuario, List<String> favoritos, List<String> alergias) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.colegiado = colegiado;
        this.dni = dni;
        this.usuario = usuario;
        this.telefono = telefono;
        this.email = email;
        this.contra = contra;
        this.tipousuario = tipousuario;
        this.favoritos = favoritos;
        this.alergias = alergias;
    }
}