package com.backend.bodegabazar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class LoginDAO {

    public static boolean autenticar(String usuario, String clave) {
        String sql = "SELECT password_hash FROM users WHERE username = ? AND deleted_at IS NULL";

        try (Connection con = ConexionBD.conectar()) {
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
}
