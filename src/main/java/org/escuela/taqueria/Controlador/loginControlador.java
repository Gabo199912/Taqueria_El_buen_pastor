package org.escuela.taqueria.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.escuela.taqueria.InicioAplicacion;
import org.escuela.taqueria.Modelo.loginModelo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class loginControlador {


    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseniaLogin;


    public void IngresarUsuario(){
        String nombreUsuario = txtUsuario.getText();
        String contrasenia = txtContraseniaLogin.getText();
        Boolean validar = true;

        List<loginModelo> listaUsuario = loginModelo.ListarUsuarios();


        for(loginModelo usuario : listaUsuario){
            if (usuario.getNombreUsuario().equals(nombreUsuario)){
                if (usuario.getContrasenia().equals(contrasenia)){
                    switch (usuario.getTipoUsuario()){
                        case "administrador":
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(InicioAplicacion.class.getResource("administrador.fxml"));
                                AnchorPane root = fxmlLoader.load();

                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setTitle("ADMINISTRADOR");
                                stage.setScene(scene);
                                stage.show();

                                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombreUsuario());
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Error al cargar el archivo: " + e.getMessage());
                            }
                            break;

                        case "vendedor":
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(InicioAplicacion.class.getResource("vendedor.fxml"));
                                AnchorPane root = fxmlLoader.load();

                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setTitle("VENDEDOR");
                                stage.setScene(scene);
                                stage.show();

                                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombreUsuario());
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Error al cargar el archivo: " + e.getMessage());
                            }
                            break;



                    }

                }else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    validar = false;
                }
            }
        }

        if (validar == false){
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
    }


}
