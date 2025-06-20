package org.escuela.taqueria.Controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.escuela.taqueria.Modelo.administradorModel;
import org.escuela.taqueria.Modelo.loginModelo;

import java.time.LocalDate;
import java.util.List;

public class administradorControlador {

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private TextField txtNombreUsuario, txtApellidoUsuario, txtCorreoUsuario, txtTelefonoUsuario, txtDireccionUsuario, txtUsuario, txtContraseniaUsuario, txtidUsuarioEliminar;

    @FXML
    private DatePicker FechaNacimientoUsuario;

    @FXML
    private TableView<administradorModel> tblAdministrador, tablaEliminacion;

    @FXML
    private TableColumn<administradorModel, Integer> ColumnaIdUsuario, idEliminarUsuario;

    @FXML
    private TableColumn<administradorModel, String> ColumnaNombreUsuario, ColumnaTipoUsuario, ColumnaTelefonoUsuario, nombreEliminarUsuario, apellidoEliminarUsuario, direccionEliminarUsuario, emailEliminarUsuario, telefonoEliminarUsuario, contraseniaEliminarUsuario, tipoUsuarioEliminar;

    @FXML
    private ComboBox<String> comboUsuarios;

    private loginModelo usuario;

    private static Alert alertas = new Alert(Alert.AlertType.INFORMATION);
    private static Alert alertasConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    public void initialize(){

        setearFechaActual();
        cargarDatosTabla();
        UsuariosAutorizados();

    }

    public void UsuariosAutorizados(){
        comboUsuarios.getItems().addAll( "administrador", "vendedor");
        comboUsuarios.getSelectionModel().select(-1);
        comboUsuarios.setPromptText("ELIJA USUARIO");
    }


    public void cargarDatosTabla(){
        ColumnaIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        ColumnaNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        ColumnaTelefonoUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        List<administradorModel> administrador = administradorModel.mostrarDatosDabla();
        ObservableList<administradorModel> datosTabla = FXCollections.observableArrayList(administrador);
        tblAdministrador.setItems(datosTabla);





        idEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        direccionEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        emailEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        contraseniaEliminarUsuario.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
        tipoUsuarioEliminar.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));


        List<administradorModel> listaUsuarios = administradorModel.listarUsuarios();
        ObservableList<administradorModel> datosTablaUsuarios = FXCollections.observableArrayList(listaUsuarios);
        tablaEliminacion.setItems(datosTablaUsuarios);
    }


    public void crearNuevoUsuario(){
        String nombre = txtNombreUsuario.getText();
        String apellido = txtApellidoUsuario.getText();
        String direccion = txtDireccionUsuario.getText();
        LocalDate fechaNacimiento = FechaNacimientoUsuario.getValue();
        String email = txtCorreoUsuario.getText();
        String telefono = txtTelefonoUsuario.getText();
        String contrasenia = txtContraseniaUsuario.getText();
        String nombreUsuario = txtUsuario.getText();
        String  tipoUsuario = comboUsuarios.getValue();

        try {

            if (tipoUsuario != null){
                if (tipoUsuario.equals("administrador") || tipoUsuario.equals("vendedor")){
                administradorModel.crearUsuario(nombre, apellido, direccion, fechaNacimiento, email, telefono, contrasenia, nombreUsuario, tipoUsuario);
                limpiarDatos();
                comboUsuarios.setPromptText("ELIJA USUARIO");
                cargarDatosTabla();
            }else {
                alertas.setTitle("ERROR");
                alertas.setHeaderText("debe cargar un usuario correcto: 'vendedor' o 'administrador'");
                alertas.setContentText("el error ocurre porque debe ingresar si el usuario es 'vendedor' o 'administrador \n" + "debe presionar el boton 'elija usuario' para crear un usuario");
            }
            }else{
                alertas.setHeaderText("ELIJA USUARIO");
                alertas.setHeaderText("debe cargar un usuario correcto");
            }



        }catch (Exception e){
           throw  new RuntimeException("Error de compuilacion llame al tecnico" +e);
        }


    }



    public void setearFechaActual(){
        LocalDate fechaActual = LocalDate.now();

        FechaNacimientoUsuario.setValue(fechaActual);
    }

    public void setUsuario(loginModelo usuario){
        this.usuario = usuario;


        if (lblNombreUsuario != null){
            lblNombreUsuario.setText("Bienbenido! " + usuario.getNombreUsuario());
        }else {
            System.out.println("lblNombreUsuario es null");
        }
    }



    public void eliminarUsuario(){
        try {
            int idUsuario = Integer.parseInt(txtidUsuarioEliminar.getText());


            alertasConfirmacion.setTitle("CONFIRMACION");
            alertasConfirmacion.setHeaderText("¿Seguro que deseas eliminar este usuario?");
            alertasConfirmacion.setContentText("ESTA ACCION NO SE PUEDE DESHACER.");

            ButtonType eliminar = alertasConfirmacion.showAndWait().orElse(ButtonType.CANCEL);

            if (eliminar == ButtonType.OK){
                administradorModel.eliminarUsuario(idUsuario);
                cargarDatosTabla();
            }else {
                alertas.setTitle("INFORMACION");
                alertas.setHeaderText("Eliminacion cancelada");
                alertas.setContentText("PARA ELIMINAR UN USUARIO DEBE PRESIONAR CONFIRMAR");
            }
        }catch (Exception e){
             throw   new RuntimeException("Error de compuilacion llame al tecnico" +e);
        }
    }


    public void limpiarDatos(){
        LocalDate fechaActual = LocalDate.now();


        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtDireccionUsuario.clear();
        FechaNacimientoUsuario.setValue(fechaActual);
        txtCorreoUsuario.clear();
        txtTelefonoUsuario.clear();
        txtContraseniaUsuario.clear();
        txtUsuario.clear();
    }






}
