package com.proyectoasistencia;

import com.proyectoasistencia.Modelo.Personal;
import com.proyectoasistencia.Modelo.PersonalDAO;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroPersonalController implements Initializable {

    @FXML
    private TableView<Personal> tablaPersonal;
    @FXML
    private TableColumn<Personal, String> colApellidos;

    @FXML
    private TableColumn<Personal, String> colCargo;

    @FXML
    private TableColumn<Personal, Integer> colCodigo;

    @FXML
    private TableColumn<Personal, String> colNombres;

    @FXML
    private TableColumn<Personal, String> colSexo;



    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfCodigo;

    @FXML
    private TextField tfNombres;

    @FXML
    private ComboBox<String> cmCargo;

    @FXML
    private ComboBox<String> cmSexo;

    private PersonalDAO personalDAO;

    @FXML
    void actualizarPersonal(ActionEvent event) {
        // Obtiene el elemento seleccionado en la tabla
        Personal personalSeleccionado = tablaPersonal.getSelectionModel().getSelectedItem();
        if (personalSeleccionado != null) {
            try {
                // Obtener los datos de los campos de entrada
                int codigo = Integer.parseInt(tfCodigo.getText());
                String nombres = tfNombres.getText();
                String apellidos = tfApellidos.getText();
                String sexo = cmSexo.getValue();
                String cargo = cmCargo.getValue();

                // Crea un objeto Personal con los datos actualizados
                Personal personalActualizado = new Personal(codigo, nombres, apellidos, sexo, cargo);

                // Actualiza el personal en la base de datos
                personalDAO.actualizarPersonal(personalActualizado);
                // Limpia los campos después de actualizar
                limpiarCampos();

                // Actualizar la tabla
                actualizarTabla();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Manejar el error de manera adecuada
            }
        } else {
            // Limpia los campos si no hay ningún elemento seleccionado
            limpiarCampos();
        }
    }

    @FXML
    void personalSeleccionado() {
        // Obtiene el elemento seleccionado en la tabla
        Personal personalSeleccionado = tablaPersonal.getSelectionModel().getSelectedItem();

        if (personalSeleccionado != null) {
            // Muentra los datos del personal seleccionado en los campos de entrada
            tfCodigo.setText(String.valueOf(personalSeleccionado.getCodigo()));
            tfNombres.setText(personalSeleccionado.getNombres());
            tfApellidos.setText(personalSeleccionado.getApellidos());
            cmSexo.setValue(personalSeleccionado.getSexo());
            cmCargo.setValue(personalSeleccionado.getCargo());
        }
    }

    @FXML
    void cancelarRegistro(ActionEvent event) {
        // Limpia los campos o realizar cualquier acción necesaria al cancelar
        limpiarCampos();
    }

    @FXML
    void eliminarPersonal(ActionEvent event) {
        // Obtiene el elemento seleccionado en la tabla
        Personal personalSeleccionado = tablaPersonal.getSelectionModel().getSelectedItem();

        if (personalSeleccionado != null) {
            // Obtiene el código del personal seleccionado
            int codigo = personalSeleccionado.getCodigo();

            // Elimina el personal de la base de datos
            personalDAO.eliminarPersonal(codigo);

            // Actualiza la tabla
            actualizarTabla();
        } else {
            // Muestra un mensaje o realiza alguna acción si no hay ningún elemento seleccionado
            JOptionPane.showMessageDialog(null,"Por favor, selecciona un personal para eliminar.");
        }
    }


    @FXML
    void registrarPersonal(ActionEvent event) {
        String nombres = tfNombres.getText();
        String apellidos = tfApellidos.getText();
        String sexo = cmSexo.getValue();
        String cargo = cmCargo.getValue();

        Personal personal = new Personal(nombres, apellidos, sexo, cargo);
        personalDAO.crearPersonal(personal);
        // Actualizar la tabla si es necesario
         actualizarTabla();
    }

    // Método para actualizar la tabla con los datos más recientes
    private void actualizarTabla() {
        List<Personal> listaPersonal = personalDAO.obtenerTodoElPersonal();
        tablaPersonal.getItems().setAll(listaPersonal);
    }


    // Método para cargar datos en ComboBox (puedes cargar desde la base de datos)
    private void cargarDatosComboBox() {
        // Cargar datos de sexo y cargo en los ComboBox
        cmSexo.getItems().addAll("Masculino", "Femenino");
        cmCargo.getItems().addAll("Cargo1", "Cargo2", "Cargo3");
    }

    // Método para limpiar los campos después de una acción
    private void limpiarCampos() {
        tfCodigo.clear();
        tfNombres.clear();
        tfApellidos.clear();
        cmSexo.getSelectionModel().clearSelection();
        cmCargo.getSelectionModel().clearSelection();
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar ComboBox, cargar datos, etc.
        personalDAO = new PersonalDAO();
        configurarTabla();
        actualizarTabla();
        cargarDatosComboBox();
    }



}
