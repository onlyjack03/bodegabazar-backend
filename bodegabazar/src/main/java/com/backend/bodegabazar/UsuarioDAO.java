package com.backend.bodegabazar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public static boolean autenticar(String usuario, String clave) {
        Connection con = ConexionBD.conectar();
        if (con == null) return false;

        String sql = "SELECT password_hash FROM users WHERE username = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("password_hash");
                return BCrypt.checkpw(clave, hash);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public static boolean cambiarContrasena(String id, String actual, String nueva) {
    String sql = "SELECT password_hash FROM users WHERE id = ?";
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String hashActual = rs.getString("password_hash");
            if (BCrypt.checkpw(actual, hashActual)) {
                String nuevoHash = BCrypt.hashpw(nueva, BCrypt.gensalt());
                PreparedStatement update = con.prepareStatement("UPDATE users SET password_hash = ? WHERE id = ?");
                update.setString(1, nuevoHash);
                update.setString(2, id);
                return update.executeUpdate() > 0;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public static String obtenerIdPorCredenciales(String usuario, String clave) {
    Connection con = ConexionBD.conectar();
    if (con == null) return null;

    String sql = "SELECT id, password_hash FROM users WHERE username = ?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String hashAlmacenado = rs.getString("password_hash");
            if (BCrypt.checkpw(clave, hashAlmacenado)) {
                return rs.getString("id");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}



}
