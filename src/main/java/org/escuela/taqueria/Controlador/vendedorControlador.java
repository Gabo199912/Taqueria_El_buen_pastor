package org.escuela.taqueria.Controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.escuela.taqueria.Modelo.loginModelo;

public class vendedorControlador {
    private loginModelo vendedor;

    @FXML
    private Label lblNombreUsuarioVendedor;



    public void setUsuario(loginModelo vendedor){
        this.vendedor = vendedor;


        if (lblNombreUsuarioVendedor != null){
            lblNombreUsuarioVendedor.setText("Bienbenido! " + vendedor.getNombreUsuario());
        }else {
            System.out.println("lblNombreUsuario es null");
        }
    }
}
