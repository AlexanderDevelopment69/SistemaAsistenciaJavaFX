package com.proyectoasistencia.Modelo;

import java.util.Date;

public class Asistencia {
    private int codigo;
    private Personal personal;
    private boolean asistio;
    private Date fecha;
    private String detalle;

    public Asistencia(int codigo, Personal personal, boolean asistio, Date fecha, String detalle) {
        this.codigo = codigo;
        this.personal = personal;
        this.asistio = asistio;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public Asistencia() {

    }


    // Métodos getters y setters para los atributos


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "codigo=" + codigo +
                ", personal=" + personal +
                ", asistio=" + asistio +
                ", fecha=" + fecha +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
