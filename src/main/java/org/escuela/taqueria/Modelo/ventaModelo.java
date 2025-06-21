package org.escuela.taqueria.Modelo;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import org.escuela.taqueria.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ventaModelo {
    private int idVenta;
    private double precioTotal;
    private int idUsuario;
    private LocalDate fechaVenta;
    private int idCliente;


    static Alert alertaError = new Alert(Alert.AlertType.ERROR);

    FXMLLoader accediendoAVentanaVentas = new FXMLLoader(getClass().getResource("vendedor.fxml"));
    
    private loginModelo usuarioParaVentas;

    public loginModelo getUsuarioParaVentas() {
        return usuarioParaVentas;
    }

    public void setUsuarioParaVentas(loginModelo usuarioParaVentas) {
        this.usuarioParaVentas = usuarioParaVentas;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaVenta() {
        LocalDate fechaDeVenta = LocalDate.now();
        return fechaDeVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public ventaModelo() {
    }

    public ventaModelo(int idVenta, double precioTotal, int idUsuario, LocalDate fechaVenta, int idCliente) {
        this.idVenta = idVenta;
        this.precioTotal = precioTotal;
        this.idUsuario = idUsuario;
        this.fechaVenta = fechaVenta;
        this.idCliente = idCliente;
    }

    public ventaModelo(double precioTotal, int idUsuario, LocalDate fechaVenta, int idCliente) {
        this.precioTotal = precioTotal;
        this.idUsuario = idUsuario;
        this.fechaVenta = fechaVenta;
    }



    public static void generarVenta( double precioTotal, int idUsuario, LocalDate fechaVenta, int idCliente){
        if (idUsuario <= 0) {
            throw new RuntimeException("ID de usuario inválido");
        }
        if (precioTotal <= 0) {
            throw new RuntimeException("Precio total debe ser mayor a 0");
        }
        if (idCliente <= 0) {
            throw new RuntimeException("ID de cliente inválido");
        }
        if (fechaVenta == null) {
            throw new RuntimeException("Fecha de venta no puede ser nula");
        }

        String sql = "INSERT INTO VENTAS ( precio_total, id_usuario, fecha_venta, id_cliente) VALUES (?,?,?,?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setDouble(1, precioTotal);
            ps.setInt(2, idUsuario);
            ps.setDate(3, java.sql.Date.valueOf(fechaVenta));
            ps.setInt(4, idCliente);
            ps.execute();


        }catch (Exception e){
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("Error al crear la venta");
            alertaError.setContentText("Error al crear la venta llame a su técnico: " + e.getMessage());
            alertaError.showAndWait();

            throw new RuntimeException("Error al crear la venta: " + e.getMessage());

        }
    }



}
