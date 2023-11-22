package com.proyectoasistencia;

import com.proyectoasistencia.Modelo.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroAsistenciaController implements Initializable {

    @FXML
    private TableView<AsistenciaPersonal> tablaAsistencia;

    @FXML
    private TableColumn<AsistenciaPersonal, Integer> colCodigo;

    @FXML
    private TableColumn<AsistenciaPersonal, String> colApellidos;

    @FXML
    private TableColumn<AsistenciaPersonal, String> colNombres;

    @FXML
    private TableColumn<AsistenciaPersonal, String> colCargo;

    @FXML
    private TableColumn<AsistenciaPersonal, String> colSexo;

    @FXML
    private TableColumn<AsistenciaPersonal, Boolean> colAsistencia;

    @FXML
    private TableColumn<AsistenciaPersonal, String> colDetalle;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField tfFecha;

    private AsistenciaDAO asistenciaDAO;
    private PersonalDAO personalDAO;

    ObservableList<AsistenciaPersonal> listaAsistenciaPersonal = FXCollections.observableArrayList();

    @FXML
    void cancelarAsistencia(ActionEvent event) {
        // Desmarca todos los checkboxes en la columna colAsistencia y limpiar las celdas en colDetalle
        for (AsistenciaPersonal asistenciaPersonal : listaAsistenciaPersonal) {
            Asistencia asistencia = asistenciaPersonal.getAsistencia();

            // Desmarca checkbox en colAsistencia
            asistencia.setAsistio(false);

            // Limpia celda en colDetalle
            asistencia.setDetalle("");
        }

        // Actualiza la vista de la tabla
        tablaAsistencia.refresh();
    }

    @FXML
    void guardarAsistencia(ActionEvent event) {
        // Mostrar un cuadro de diálogo de confirmación
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Confirmar, para guardar la asistencia de hoy?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Date fechaAsistencia = new Date();  // Obtén la fecha actual

            for (AsistenciaPersonal asistenciaPersonal : listaAsistenciaPersonal) {
                Asistencia asistencia = asistenciaPersonal.getAsistencia();
                asistencia.setFecha(fechaAsistencia);  // Configura la fecha para todas las asistencias
            }

            // Llama al método que guarda en la base de datos
            asistenciaDAO.crearAsistencias(listaAsistenciaPersonal);

            // Luego, puedes imprimir todas las filas
            imprimirTodasLasFilas();

            // Muestra un cuadro de diálogo de éxito
            JOptionPane.showMessageDialog(null, "Asistencia guardada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void cargarDatosPersonal() {
        listaAsistenciaPersonal.clear();
        listaAsistenciaPersonal.addAll(personalDAO.obtenerTodoElPersonal().stream().map(AsistenciaPersonal::new).toList());
        tablaAsistencia.setItems(listaAsistenciaPersonal);
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPersonal().getCodigo()).asObject());
        colNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonal().getNombres()));
        colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonal().getApellidos()));
        colSexo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonal().getSexo()));
        colCargo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonal().getCargo()));

        // Configurar CheckBox en colAsistencia
        colAsistencia.setCellValueFactory(cellData -> {
            AsistenciaPersonal asistenciaPersonal = cellData.getValue();
            SimpleBooleanProperty asistioProperty = new SimpleBooleanProperty(asistenciaPersonal.getAsistencia().isAsistio());

            // Agregar un manejador de eventos al CheckBox para actualizar la propiedad asistio
            asistioProperty.addListener((obs, oldValue, newValue) -> {
                asistenciaPersonal.getAsistencia().setAsistio(newValue);
            });

            return asistioProperty;
        });

        colAsistencia.setCellFactory(CheckBoxTableCell.forTableColumn(colAsistencia));
        colAsistencia.setEditable(true);

        // Configurar edición de texto en colDetalle
        colDetalle.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAsistencia() != null ?
                        cellData.getValue().getAsistencia().getDetalle() : ""));
        colDetalle.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        // Manejar eventos de edición para colDetalle
        colDetalle.setOnEditCommit(event -> {
            AsistenciaPersonal asistenciaPersonal = event.getRowValue();
            Asistencia asistencia = asistenciaPersonal.getAsistencia();

            if (asistencia != null) {
                asistencia.setDetalle(event.getNewValue());
                System.out.println("Nuevo valor en la celda: " + event.getNewValue());
            } else {
                System.out.println("La instancia de Asistencia es nula en la fila con datos: " +
                        asistenciaPersonal.getPersonal().getCodigo());
            }
        });

        // Hacer que la columna de detalle sea editable al hacer clic
        colDetalle.setEditable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asistenciaDAO = new AsistenciaDAO();
        personalDAO = new PersonalDAO();
        configurarTabla();
        cargarDatosPersonal();
        Date date = new Date();
        tfFecha.setText(String.valueOf(date));
    }

    // Método para imprimir todas las filas de la tabla
    private void imprimirTodasLasFilas() {
        for (AsistenciaPersonal asistenciaPersonal : listaAsistenciaPersonal) {
            System.out.println("Código: " + asistenciaPersonal.getPersonal().getCodigo() +
                    ", Nombre: " + asistenciaPersonal.getPersonal().getNombres() +
                    ", Asistencia: " + asistenciaPersonal.getAsistencia().isAsistio() +
                    ", Detalle: " + asistenciaPersonal.getAsistencia().getDetalle());
        }
    }
}
