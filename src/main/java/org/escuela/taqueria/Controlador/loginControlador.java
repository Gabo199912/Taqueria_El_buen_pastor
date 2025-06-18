package org.escuela.taqueria.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombreUsuario());
                }else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                }
            }else {
                validar = false;
            }
        }

        if (validar == false){
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
    }


}
