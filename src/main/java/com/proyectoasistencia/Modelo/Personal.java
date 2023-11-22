package com.proyectoasistencia.Modelo;

public class Personal {
    private int codigo;
    private String nombres;
    private String apellidos;
    private String sexo;
    private String cargo;

    public Personal(int codigo, String nombres, String apellidos, String sexo, String cargo) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.cargo = cargo;
    }

    public Personal(String nombres, String apellidos, String sexo, String cargo) {

        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.cargo = cargo;
    }

    // Métodos getters y setters para los atributos

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "codigo=" + codigo +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", sexo='" + sexo + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
