package com.proyectoasistencia.Modelo;

import com.proyectoasistencia.ConexionSqlServer.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {

    public void crearPersonal(Personal personal) {
        Connection conexion = null; // Declara la variable Connection
        try {
            conexion = ConexionBD.obtenerConexion(); // Asigna una conexión a la variable
            String sql = "INSERT INTO Personal (nombres, apellidos, sexo, cargo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, personal.getNombres());
                pstmt.setString(2, personal.getApellidos());
                pstmt.setString(3, personal.getSexo());
                pstmt.setString(4, personal.getCargo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion); // Cierra la conexión en el bloque finally
        }
    }


    public List<Personal> obtenerTodoElPersonal() {
        List<Personal> listaPersonal = new ArrayList<>();
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM Personal";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Personal personal = mapearDesdeResultSet(rs);
                    listaPersonal.add(personal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return listaPersonal;
    }

    public Personal leerPersonal(int codigo) {
        Personal personal = null;
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "SELECT * FROM Personal WHERE codigo = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, codigo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        personal = mapearDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return personal;
    }

    public void actualizarPersonal(Personal personal) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "UPDATE Personal SET nombres=?, apellidos=?, sexo=?, cargo=? WHERE codigo=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, personal.getNombres());
                pstmt.setString(2, personal.getApellidos());
                pstmt.setString(3, personal.getSexo());
                pstmt.setString(4, personal.getCargo());
                pstmt.setInt(5, personal.getCodigo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public void eliminarPersonal(int codigo) {
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "DELETE FROM Personal WHERE codigo=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, codigo);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de manera apropiada
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }


    private Personal mapearDesdeResultSet(ResultSet rs) throws SQLException {
        int codigo = rs.getInt("Codigo");
        String nombres = rs.getString("Nombres");
        String apellidos = rs.getString("Apellidos");
        String sexo = rs.getString("Sexo");
        String cargo = rs.getString("Cargo");

        return new Personal(codigo, nombres, apellidos, sexo, cargo);
    }
}
