package com.proyectoasistencia.ConexionSqlServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Configuración de la conexión
    private static final String URL =
            "jdbc:sqlserver://192.168.145.128:1433;"
                    + "database=ControlAsistencia;"
                    + "user=sa;"
                    + "password=alexander#123A;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";


    public static Connection obtenerConexion() {
        Connection conexion = null;
        try  {
            conexion  = DriverManager.getConnection(URL);
            System.out.println("Conexión establecida correctamente");
        }catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Connection conexion = ConexionBD.obtenerConexion();

        if (conexion != null) {
            System.out.println("Conexión exitosa");
            // Realiza operaciones en la base de datos...

            ConexionBD.cerrarConexion(conexion);
        } else {
            System.out.println("No se pudo establecer la conexión");
        }
    }
}
