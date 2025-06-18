package org.escuela.taqueria.Modelo;

import javafx.scene.web.WebHistory;
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


    public String getNombreUsuario() {
        return nombreUsuario;
    }


    public String getContrasenia() {
        return contrasenia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    //añl





    public loginModelo(String nombre, String apellido, String direccion, LocalDate fechaNacimiento, String email, String telefono, String contrasenia, String nombreUsuario, LocalDate horaEntrada, LocalDate horaSalida) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.nombreUsuario = nombreUsuario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
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
            JOptionPane.showMessageDialog(null, "Error al consultar los datos del usuario: " + e.getMessage());
        }
        return listaUsuarios;
    }


}
