package org.escuela.taqueria.Modelo;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.escuela.taqueria.Controlador.administradorControlador;
import org.escuela.taqueria.InicioAplicacion;
import org.escuela.taqueria.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class loginModelo {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private String contrasenia;
    private String nombreUsuario;
    private LocalDate horaEntrada;
    private LocalDate horaSalida;
    private Boolean usuarioActivo;
    private LocalDate fechaCreacion;
    private String tipoUsuario;


    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public String getContrasenia() {
        return contrasenia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }




    public loginModelo(String nombreUsuario, String contrasenia, String tipoUsuario){
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }



    public static List<loginModelo> ListarUsuarios(){
        String sql = "SELECT nombre_usuario, contrasenia, tipo_usuario FROM USUARIO;";
        List<loginModelo> listaUsuarios = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    listaUsuarios.add(new loginModelo(
                            rs.getString("nombre_usuario"),
                            rs.getString("contrasenia"),
                            rs.getString("tipo_usuario")));
                }
        }
        catch (Exception e){
            throw new RuntimeException("Error al consultar los datos del usuario: " + e.getMessage());
        }
        return listaUsuarios;
    }

    public static void seleccionarVentana(loginModelo usuario){

        try {
            FXMLLoader fxmlLoader;
            String archivoFXML = "";
            switch (usuario.getTipoUsuario()){
                case "administrador":
                    archivoFXML = "administrador.fxml";
                    break;
                case "vendedor":
                    archivoFXML = "vendedor.fxml";
                    break;

                    default:
                    throw new IllegalArgumentException("tipo de usuario no valido" + usuario.getTipoUsuario());
            }
            fxmlLoader = new FXMLLoader(InicioAplicacion.class.getResource(archivoFXML));
            AnchorPane root = fxmlLoader.load();



            administradorControlador controlador = fxmlLoader.getController();

           controlador.setUsuario(usuario);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("ADMINISTRADOR");
            stage.setScene(scene);
            stage.show();



        }catch (Exception e){
            throw new RuntimeException("Error al cargar el archivo: " + e.getMessage());
        }
    }


}
