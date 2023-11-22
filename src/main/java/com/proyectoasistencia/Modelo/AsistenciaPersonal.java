package com.proyectoasistencia.Modelo;

public class AsistenciaPersonal {
    private Asistencia asistencia;
    private Personal personal;

    public AsistenciaPersonal(Personal personal) {
        this.personal = personal;
        this.asistencia = new Asistencia(); // Inicializar la propiedad asistencia
    }


    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}