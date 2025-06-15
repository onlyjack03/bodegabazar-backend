package com.backend.bodegabazar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

public class RegistroDAO {

    public static boolean registrarUsuario(String nombre, String correo, String usuario, String clave) {
        String sql = "INSERT INTO users (id, full_name, email, username, password_hash, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(sql);

            String id = UUID.randomUUID().toString(); // Generar ID único
            String passwordHash = BCrypt.hashpw(clave, BCrypt.gensalt()); // Cifrar la clave

            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, correo);
            ps.setString(4, usuario);
            ps.setString(5, passwordHash);
            ps.setObject(6, LocalDateTime.now());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
