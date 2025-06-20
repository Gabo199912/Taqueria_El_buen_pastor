package org.escuela.taqueria.Modelo;

import javafx.scene.control.Alert;
import org.escuela.taqueria.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class administradorModel {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String nombreUsuario;
    private Boolean usuarioActivo;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String tipoUsuario;
    private String contrasenia;

    public String getNombreUsuario() {
        return nombreUsuario;
    }



    public administradorModel(int idUsuario, String nombre, String tipoUsuario, String telefono) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.telefono = telefono;
    }



    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Boolean getUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(Boolean usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    static Alert alertas = new Alert(Alert.AlertType.INFORMATION);


    public static void crearUsuario(String nombre, String apellido, String direccion, LocalDate fechaNacimiento, String email, String telefono, String contrasenia, String nombreUsuario, String tipoUsuario){
        String sql = "INSERT INTO USUARIO (nombre, apellido, direccion, fecha_nacimiento, email, telefono, contrasenia, nombre_usuario, tipo_usuario) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, direccion);
            ps.setDate(4, java.sql.Date.valueOf(fechaNacimiento));
            ps.setString(5, email);
            ps.setString(6, telefono);
            ps.setString(7, contrasenia);
            ps.setString(8, nombreUsuario);
            ps.setString(9, tipoUsuario);

            ps.execute();

            alertas.setTitle("INFORMACION");
            alertas.setHeaderText("Se ha creado un nuevo usuario");
            alertas.showAndWait();

        }catch (Exception e){
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }

    }


    public static List<administradorModel> mostrarDatosDabla(){
        String sql = "SELECT id_usuario, nombre, tipo_usuario, telefono FROM USUARIO";

        List<administradorModel> listadoAdministradores = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()){
                listadoAdministradores.add(new administradorModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("tipo_usuario"),
                        rs.getString("telefono")
                ));
            }
        }catch (Exception e){
            throw new RuntimeException("Error al consultar los datos del usuario: " + e.getMessage());
        }
        return listadoAdministradores;
    }


    public administradorModel(int idUsuario, String nombre, String apellido, String email, String telefono, String nombreUsuario, String direccion, String contrasenia, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }

    public static List<administradorModel> listarUsuarios(){
        String sql = "SELECT id_usuario, nombre, apellido, email, telefono, nombre_usuario, direccion, contrasenia, tipo_usuario FROM USUARIO";
        List<administradorModel> listaUsuarios = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                listaUsuarios.add(new administradorModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("nombre_usuario"),
                        rs.getString("direccion"),
                        rs.getString("contrasenia"),
                        rs.getString("tipo_usuario")
                ));
            }


        }catch (Exception e ){
            throw new RuntimeException("Error al consultar los datos del usuario: " + e.getMessage());
        }

        return listaUsuarios;
    }



    public static void eliminarUsuario(int  idUsuario){
        String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";

        try (Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, idUsuario);
            ps.executeUpdate();


            alertas.setTitle("INFORMACION");
            alertas.setHeaderText("Se ha eliminado el usuario de manera correcta");

            alertas.showAndWait();

        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
