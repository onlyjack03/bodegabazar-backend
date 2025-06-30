package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;

public class RegistroScreen extends JFrame {
    private JTextField nombreField, correoField, usuarioField;
    private JPasswordField claveField;

    public RegistroScreen() {
        setTitle("Registro - Bodega Bazar Gabby");
        setSize(400, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 210, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Bodega Bazar Gabby");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Iniciar Sesión");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 20));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        nombreField = new JTextField();
        correoField = new JTextField();
        usuarioField = new JTextField();
        claveField = new JPasswordField();

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearCampo("Nombre", nombreField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampo("Correo Electrónico", correoField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampo("Usuario", usuarioField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearCampo("Contraseña", claveField));
        panel.add(Box.createVerticalStrut(20));

        JButton registrarBtn = new JButton("Crear cuenta");
        registrarBtn.setBackground(new Color(145, 100, 255));
        registrarBtn.setForeground(Color.WHITE);
        registrarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(registrarBtn);

        panel.add(Box.createVerticalStrut(15));

        JButton iniciarBtn = new JButton("¿Ya tienes una cuenta? Inicia Sesión");
        iniciarBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        iniciarBtn.setForeground(new Color(143, 102, 255));
        iniciarBtn.setFocusPainted(false);
        iniciarBtn.setBorderPainted(false);
        iniciarBtn.setContentAreaFilled(false);
        iniciarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        iniciarBtn.addActionListener(e -> {
        new LoginScreen();
            dispose();
        });

        panel.add(iniciarBtn);

      
        registrarBtn.addActionListener(e -> {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String usuario = usuarioField.getText();
            String clave = new String(claveField.getPassword());

            boolean registrado = RegistroDAO.registrarUsuario(nombre, correo, usuario, clave);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "¡Usuario registrado correctamente!");
                dispose(); 
                new LoginScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }

    private JPanel crearCampo(String labelText, JTextField field) {
        JPanel campoPanel = new JPanel();
        campoPanel.setLayout(new BorderLayout());
        campoPanel.setBackground(new Color(230, 210, 255));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        campoPanel.add(label, BorderLayout.NORTH);
        campoPanel.add(field, BorderLayout.CENTER);

        return campoPanel;
    }
}
