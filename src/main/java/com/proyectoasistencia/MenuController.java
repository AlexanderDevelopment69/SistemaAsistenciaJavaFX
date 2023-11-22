package com.proyectoasistencia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    void irRegistrarAsistencia(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista secundaria
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyectoasistencia/RegistroAsistencia.fxml"));
            Parent root = loader.load();


            // Configurar la nueva ventana
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Ventana modal (bloquea la ventana principal)
            stage.setTitle("Registrar Asistencia");
            stage.setScene(new Scene(root));

            // Configurar la ventana para que no sea redimensionable
            stage.setResizable(false);

            // Mostrar la nueva ventana
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irRegistroPersonal(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la vista secundaria
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/proyectoasistencia/RegistroPersonal.fxml"));
            Parent root = loader.load();


            // Configurar la nueva ventana
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Ventana modal (bloquea la ventana principal)
            stage.setTitle("Registrar Personal");
            stage.setScene(new Scene(root));

            // Pasar cualquier dato necesario al controlador de la vista secundaria
            // controladorSecundario.setDatos(...);

            // Configurar la ventana para que no sea redimensionable
            stage.setResizable(false);

            // Mostrar la nueva ventana
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
