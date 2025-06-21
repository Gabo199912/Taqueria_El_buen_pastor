package org.escuela.taqueria;

import org.escuela.taqueria.Modelo.loginModelo;

public class usuarioSesion {
    private static loginModelo usuario;

    // Evitar que se cree más de una instancia
    private usuarioSesion() {}

    public static loginModelo getUsuario() {
        return usuario;
    }

    public static void setUsuario(loginModelo usuario) {
      usuarioSesion.usuario = usuario;
    }
}
