package org.escuela.taqueria.Modelo;

import javafx.scene.control.Alert;
import org.escuela.taqueria.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class clienteModelo {
    private int idCliente;
    private String NIT;
    private String nombre;
    private String email;
    private String telefono;

    static Alert alertas = new Alert(Alert.AlertType.INFORMATION);
    static Alert alertasError = new Alert(Alert.AlertType.ERROR);

    public void setIDCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public clienteModelo(int idCliente, String NIT, String nombre, String email, String telefono) {
        this.idCliente = idCliente;
        this.NIT = NIT;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }


    public static void crearCliente(String NIT, String nombre, String email, String telefono){
        String sql = "INSERT INTO CLIENTES (NIT, nombre, email, telefono) VALUES (?,?,?,?)";


        try(Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, NIT);
            ps.setString(2, nombre);
            ps.setString(3, email);
            ps.setString(4, telefono);
            ps.execute();

            alertas.setTitle("INFORMACION");
            alertas.setHeaderText("Se ha creado un nuevo cliente");
            alertas.setContentText("Se ha creado un nuevo cliente con el nombre: " + nombre);
            alertas.showAndWait();

        }catch (Exception e){
            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
        }
    }


    public static List<clienteModelo> buscarClienteporNit(String NIT){
        String sql = "SELECT id_cliente, NIT, nombre, email, telefono FROM CLIENTES WHERE NIT = ?";
        List<clienteModelo> clienteEncontrado = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, NIT);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String NITCliente = rs.getString("NIT");
                String nombreCliente = rs.getString("nombre");
                String emailCliente = rs.getString("email");
                String telefonoCliente = rs.getString("telefono");

                clienteEncontrado.add(new clienteModelo(idCliente, NITCliente, nombreCliente, emailCliente, telefonoCliente));
            }
        }catch (Exception e){
            alertasError.setTitle("ERROR");
            alertasError.setHeaderText("Error al consultar los datos del cliente");
            alertasError.setContentText("Llame a su técnico, error: " + e.getMessage());

            throw new RuntimeException("Error al consultar los datos del cliente: " + e.getMessage());
        }
        return clienteEncontrado;
    }


}
