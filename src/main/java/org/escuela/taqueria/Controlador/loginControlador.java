package org.escuela.taqueria.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.escuela.taqueria.InicioAplicacion;
import org.escuela.taqueria.Modelo.loginModelo;
import org.escuela.taqueria.usuarioSesion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class loginControlador {
    private String nombreUsuario;
    private String contrasenia;

    private loginModelo usuario;

    public loginModelo getUsuario() {
        return usuario;
    }

    public void setUsuario(loginModelo usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String getContrasenia) {
        this.contrasenia = getContrasenia;
    }

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseniaLogin;





    public void IngresarUsuario(){

        setNombreUsuario(txtUsuario.getText());
        setContrasenia(txtContraseniaLogin.getText());

        List<loginModelo> listaUsuario = loginModelo.ListarUsuarios();



        for(loginModelo usuario : listaUsuario){
            if (usuario.getNombreUsuario().equals(nombreUsuario)){
                if (usuario.getContrasenia().equals(contrasenia)){
                    loginModelo.seleccionarVentana(usuario);


                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombreUsuario());


                    Stage stage = (Stage) txtUsuario.getScene().getWindow();
                    stage.close();
                    break;

                }else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    break;
                }
            }
        }


    }


}
