package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PerfilUsuarioScreen extends JFrame {

    private JLabel lblNombre, lblCorreo;
    private JButton btnCambiarContrasena;
    private String usuarioId;

    public PerfilUsuarioScreen(String usuarioId) {
        this.usuarioId = usuarioId;
        setTitle("Perfil de Usuario");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        lblNombre = new JLabel("Nombre: ");
        lblCorreo = new JLabel("Correo: ");
        btnCambiarContrasena = new JButton("Cambiar Contraseña");

        btnCambiarContrasena.addActionListener(e -> cambiarContrasena());

        JPanel panelInfo = new JPanel(new GridLayout(3, 1, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelInfo.add(lblNombre);
        panelInfo.add(lblCorreo);
        panelInfo.add(btnCambiarContrasena);

        add(panelInfo, BorderLayout.CENTER);
        cargarDatosUsuario();
        setVisible(true);
    }

    private void cargarDatosUsuario() {
        String sql = "SELECT full_name, email FROM users WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblNombre.setText("Nombre: " + rs.getString("full_name"));
                lblCorreo.setText("Correo: " + rs.getString("email"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuario");
        }
    }

    private void cambiarContrasena() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JPasswordField actual = new JPasswordField();
        JPasswordField nueva = new JPasswordField();

        panel.add(new JLabel("Contraseña actual:"));
        panel.add(actual);
        panel.add(new JLabel("Nueva contraseña:"));
        panel.add(nueva);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String actualText = new String(actual.getPassword());
            String nuevaText = new String(nueva.getPassword());

            if (UsuarioDAO.cambiarContrasena(usuarioId, actualText, nuevaText)) {
                JOptionPane.showMessageDialog(this, "Contraseña actualizada");
            } else {
                JOptionPane.showMessageDialog(this, "Error al cambiar contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
