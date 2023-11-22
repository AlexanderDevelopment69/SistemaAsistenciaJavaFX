package com.proyectoasistencia.Modelo;

import com.proyectoasistencia.ConexionSqlServer.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    public void crearAsistencia(Asistencia asistencia) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO Asistencia (personalCodigo, asistio, fecha, detalle) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, asistencia.getPersonal().getCodigo());
                pstmt.setBoolean(2, asistencia.isAsistio());
                pstmt.setDate(3, new java.sql.Date(asistencia.getFecha().getTime()));
                pstmt.setString(4, asistencia.getDetalle());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }


    public void crearAsistencias(List<AsistenciaPersonal> asistenciasPersonales) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO Asistencia (personalCodigo, asistio, fecha, detalle) VALUES (?, ?, ?, ?)";

            for (AsistenciaPersonal asistenciaPersonal : asistenciasPersonales) {
                Asistencia asistencia = asistenciaPersonal.getAsistencia();
                Personal personal = asistenciaPersonal.getPersonal();

                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                    pstmt.setInt(1, personal.getCodigo());
                    pstmt.setBoolean(2, asistencia.isAsistio());
                    pstmt.setTimestamp(3, new Timestamp(asistencia.getFecha().getTime()));
                    pstmt.setString(4, asistencia.getDetalle());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }


    public Asistencia leerAsistencia(int id) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM Asistencia WHERE codigo = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return null;
    }

    public List<Asistencia> listarAsistencias() {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM Asistencia";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                List<Asistencia> asistencias = new ArrayList<>();
                while (rs.next()) {
                    Asistencia asistencia = mapearDesdeResultSet(rs);
                    asistencias.add(asistencia);
                }
                return asistencias;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return new ArrayList<>(); // Puedes retornar una lista vacía o manejarlo según tus necesidades
    }

    public void actualizarAsistencia(Asistencia asistencia) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "UPDATE Asistencia SET personalCodigo=?, asistio=?, fecha=?, detalle=? WHERE codigo=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, asistencia.getPersonal().getCodigo());
                pstmt.setBoolean(2, asistencia.isAsistio());
                pstmt.setDate(3, new java.sql.Date(asistencia.getFecha().getTime()));
                pstmt.setString(4, asistencia.getDetalle());
                pstmt.setInt(5, asistencia.getCodigo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public void eliminarAsistencia(int id) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM Asistencia WHERE codigo=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }

    private Asistencia mapearDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("Id");
        int personalCodigo = rs.getInt("PersonalCodigo");
        boolean asistio = rs.getBoolean("Asistio");
        java.util.Date fecha = rs.getDate("Fecha");
        String detalle = rs.getString("Detalle");

        // Utilizar el método leerPersonal de PersonalDAO
        Personal personal = new PersonalDAO().leerPersonal(personalCodigo);

        return new Asistencia(id, personal, asistio, fecha, detalle);
    }
}
