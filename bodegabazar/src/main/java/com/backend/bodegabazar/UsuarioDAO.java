package com.backend.bodegabazar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public static boolean autenticar(String usuario, String clave) {
        Connection con = ConexionBD.conectar();
        if (con == null) return false;

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();

            return rs.next(); // Si hay resultado, está autenticado

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

