package org.escuela.taqueria.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.escuela.taqueria.Modelo.clienteModelo;
import org.escuela.taqueria.Modelo.loginModelo;
import org.escuela.taqueria.Modelo.ventaModelo;
import org.escuela.taqueria.usuarioSesion;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class ventaControlador {


    private loginModelo vendedor;

    @FXML
    private Label lblNombreUsuarioVendedor, lblTotal;

    @FXML
    private TextField txtNIT, txtNombreVenta, txtEmailVenta, txtTelefonoVenta, txtTaco3Unidades, txtTaco4Unidades, txtMixto3Unidades, txtMixto4Unidades, txtPagoTarjeta, txtPagoEfectivo;
    
    static Alert alerta = new Alert(Alert.AlertType.INFORMATION);

    double precioTacos3Unidades = 25;
    double precioTacos4Unidades = 30;
    double precioMixto3Unidades = 35;
    double precioMixto4Unidades = 40;





    public void setUsuario(loginModelo vendedor){
        this.vendedor = vendedor;
        actualizarNombreUsuarioVendedor();
    }

    public void getUsuario(loginModelo vendedor){
        this.vendedor = vendedor;
    }

    public loginModelo getVendedor() {
        return vendedor;
    }



    public void initialize(){
        iniciarUnidadesDeTacos();
        lblTotal.setText("0.00");
    }

    public void actualizarNombreUsuarioVendedor(){
        if (lblNombreUsuarioVendedor != null){
            lblNombreUsuarioVendedor.setText("Bienvenido! " + vendedor.getNombreUsuario());
        }else {
            System.out.println("lblNombreUsuario es null");
        }
    }
    
    
    //VERIFICANDO LA VENTA
    public void verificarUsuarioVenta(){
        String nit = txtNIT.getText();
        String nombreVenta = txtNombreVenta.getText();
        String emailVenta = txtEmailVenta.getText();
        String telefonoVenta = txtTelefonoVenta.getText();
        double total = Double.parseDouble(lblTotal.getText());


       List<clienteModelo> cliente =  clienteModelo.buscarClienteporNit(nit);
       ventaModelo venta = new ventaModelo();

        if (cliente.isEmpty()){
            if (nit.equals("") || nombreVenta.equals("") || total == 0.0f){
                alerta.setTitle("INFORMACION");
                alerta.setHeaderText("Debe ingresar NIT Y NOMBRE");
                alerta.setContentText("DEBE INGRESAR EL NIT, NOMBRE Y NO PUEDE VENDER CON VALOR A 0");
                alerta.showAndWait();
            }else {
                clienteModelo.crearCliente(nit, nombreVenta, emailVenta, telefonoVenta);

                Double pagoTarjeta = 0.0;
                Double pagoEfectivo = 0.0;


                try {
                    if (!txtPagoTarjeta.getText().isEmpty()){
                        pagoTarjeta = Double.parseDouble(txtPagoTarjeta.getText());
                    }
                    if (!txtPagoEfectivo.getText().isEmpty()){
                        pagoEfectivo = Double.parseDouble(txtPagoEfectivo.getText());
                    }
                }catch (NumberFormatException e){
                    throw  new RuntimeException("Error de compuilacion llame al tecnico" +e);
                }

                Double totalAPagar = Double.parseDouble(lblTotal.getText());
                Double totalPago = totalAPagar - (pagoTarjeta + pagoEfectivo);



                if (totalPago.equals(0.0)){
                    double precio_total = total;
                    int idVendedor = usuarioSesion.getUsuario().getIdUsuario();
                    LocalDate fechaVenta = venta.getFechaVenta();
                    int idCliente = cliente.get(0).getIdCliente();

                    JOptionPane.showMessageDialog(null, idVendedor);

                    ventaModelo.generarVenta(precio_total, idVendedor, fechaVenta, idCliente);

                    limpiarDatos();
                }else {
                    alerta.setTitle("COMPLETAR PAGO");
                    alerta.setHeaderText("Debe completar el pago");
                    alerta.setContentText("debe completar el pago con tarjeta o efectivo");
                    alerta.showAndWait();
                    txtPagoTarjeta.setText("");
                    txtPagoEfectivo.setText("");
                    return;
                }


            }

        }else {
            if (nit.equals("") || nombreVenta.equals("") || total == 0.0f){
                alerta.setTitle("INFORMACION");
                alerta.setHeaderText("Debe ingresar todos los datos");
                alerta.setContentText("debe ingresar todos los datos");
                alerta.showAndWait();
                limpiarDatos();
                return;
            }


            Double pagoTarjeta = 0.0;
            Double pagoEfectivo = 0.0;

           try {
               if (!txtPagoTarjeta.getText().isEmpty()){
                   pagoTarjeta = Double.parseDouble(txtPagoTarjeta.getText());
               }
               if (!txtPagoEfectivo.getText().isEmpty()){
                   pagoEfectivo = Double.parseDouble(txtPagoEfectivo.getText());
               }
           }catch (NumberFormatException e){
               throw  new RuntimeException("Error de compuilacion llame al tecnico" +e);
           }

            Double totalAPagar = Double.parseDouble(lblTotal.getText());
            Double totalPago = totalAPagar - (pagoTarjeta + pagoEfectivo);



            if (totalPago.equals(0.0)){
                double precio_total = total;
                int idVendedor = usuarioSesion.getUsuario().getIdUsuario();
                LocalDate fechaVenta = venta.getFechaVenta();
                int idCliente = cliente.get(0).getIdCliente();

                JOptionPane.showMessageDialog(null, idVendedor);

                ventaModelo.generarVenta(precio_total, idVendedor, fechaVenta, idCliente);

                limpiarDatos();
            }else {
                alerta.setTitle("COMPLETAR PAGO");
                alerta.setHeaderText("Debe completar el pago");
                alerta.setContentText("debe completar el pago con tarjeta o efectivo");
                alerta.showAndWait();
                txtPagoTarjeta.setText("");
                txtPagoEfectivo.setText("");
            }
        }

    }
    

    public void insertarDatosDeCliente(){
        String nit = txtNIT.getText();
        
        List<clienteModelo> cliente =  clienteModelo.buscarClienteporNit(nit);
        
        if (cliente.isEmpty()){
            alerta.setTitle("INFORMACION");
            alerta.setHeaderText("Cliente no encontrado");
            alerta.setContentText("debe ingresar un numero de NIT valido");
            alerta.showAndWait();
        }else {
            txtNIT.setText(cliente.get(0).getNIT());
            txtNombreVenta.setText(cliente.get(0).getNombre());
            txtEmailVenta.setText(cliente.get(0).getEmail());
            txtTelefonoVenta.setText(cliente.get(0).getTelefono());
        }
        
        
    }


    public void taco3UnidadesVentaProducto(){
        double cantidadDePorcionesTaco3Unidades = Double.parseDouble(txtTaco3Unidades.getText());
        double total3Unidade = cantidadDePorcionesTaco3Unidades * precioTacos3Unidades;
        double totalParcial = Double.parseDouble(lblTotal.getText());

        double total = total3Unidade + totalParcial;

        lblTotal.setText(String.format("%.2f",total));
    }

    public void taco4UnidadesVentaProducto(){
        double cantidadDePorcionesTaco4Unidades = Double.parseDouble(txtTaco4Unidades.getText());
        double total4Unidade = cantidadDePorcionesTaco4Unidades * precioTacos4Unidades;
        double totalParcial = Double.parseDouble(lblTotal.getText());

        double total = total4Unidade + totalParcial;

        lblTotal.setText(String.format("%.2f",total));
    }

    public void mixto3UnidadesVentaProducto(){
        double cantidadDePorcionesMixto3Unidades = Double.parseDouble(txtMixto3Unidades.getText());
        double total3Unidade = cantidadDePorcionesMixto3Unidades * precioMixto3Unidades;
        double totalParcial = Double.parseDouble(lblTotal.getText());

        double total = total3Unidade + totalParcial;

        lblTotal.setText(String.format("%.2f",total));
    }

    public void mixto4UnidadesVentaProducto(){
        double cantidadDePorcionesMixto4Unidades = Double.parseDouble(txtMixto4Unidades.getText());
        double total4Unidade = cantidadDePorcionesMixto4Unidades * precioMixto4Unidades;
        double totalParcial = Double.parseDouble(lblTotal.getText());

        double total = total4Unidade + totalParcial;

        lblTotal.setText(String.format("%.2f",total));
    }
    
    public void limpiarDatos(){
        txtNIT.setText("C/F");
        txtNombreVenta.clear();
        txtEmailVenta.clear();
        txtTelefonoVenta.clear();
        txtPagoTarjeta.setText("0");
        txtPagoEfectivo.setText("0");
        txtTaco3Unidades.setText("1");
        txtTaco4Unidades.setText("1");
        txtMixto3Unidades.setText("1");
        txtMixto4Unidades.setText("1");



        lblTotal.setText("0.00");
    }

    public void iniciarUnidadesDeTacos(){
        txtNIT.setText("C/F");
        txtTaco3Unidades.setText("1");
        txtTaco4Unidades.setText("1");
        txtMixto3Unidades.setText("1");
        txtMixto4Unidades.setText("1");
        txtPagoTarjeta.setText("0");
        txtPagoEfectivo.setText("0");
        lblTotal.setText("0.00");
    }

}
