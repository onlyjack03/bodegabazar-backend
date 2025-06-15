package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setSize(400, 700);
        setResizable(false); // Para evitar que lo agranden como ventana de escritorio


        // Título de la ventana
        setTitle("Bodega Bazar Gabby");

        // Contenedor principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(230, 210, 255)); // color lila claro

        // Título
        JLabel title = new JLabel("Bodega Bazar Gabby", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel subtitle = new JLabel("Gestión de Inventarios", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Campos
        JLabel userLabel = new JLabel("Usuario");
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel passLabel = new JLabel("Contraseña");
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(e -> {
        String usuario = userField.getText();
        String clave = new String(passField.getPassword());

        if (UsuarioDAO.autenticar(usuario, clave)) {
        JOptionPane.showMessageDialog(this, "Bienvenido " + usuario);
        // Aquí podrías abrir el dashboard
        } else {
        JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
        });

        loginButton.setBackground(new Color(143, 102, 255)); // violeta
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton registrarBtn = new JButton("Regístrate");
        registrarBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        registrarBtn.setForeground(new Color(143, 102, 255));
        registrarBtn.setFocusPainted(false);
        registrarBtn.setBorderPainted(false);
        registrarBtn.setContentAreaFilled(false);
        registrarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Acción: abre la pantalla de registro
        registrarBtn.addActionListener(e -> {
        new RegistroScreen();
            dispose();
        });

        registerPanel.add(registrarBtn);


        // Añadir componentes al formPanel
        formPanel.add(userLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(userField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(loginButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(registerPanel);


        // Panel contenedor general
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        container.add(formPanel, BorderLayout.CENTER);

        // Agregar todos al panel principal
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(subtitle, BorderLayout.CENTER);
        mainPanel.add(container, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(400, 600);
        setLocationRelativeTo(null); // centrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
}

