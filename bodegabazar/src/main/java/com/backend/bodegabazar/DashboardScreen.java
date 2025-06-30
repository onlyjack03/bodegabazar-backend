package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;

public class DashboardScreen extends JFrame {

    private String userId;

    public DashboardScreen(String userId) {
        this.userId = userId;
        setTitle("Dashboard");
        setSize(400, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(5, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        
        JButton totalBtn = crearBoton("Total Productos", "📦", new Color(230, 230, 255));
        totalBtn.addActionListener(e -> new InventarioScreen("todos"));

        
        JButton stockBtn = crearBoton("Bajo Stock", "⚠️", new Color(255, 245, 200));
        stockBtn.addActionListener(e -> new InventarioScreen("bajo"));

        
        JButton vencerBtn = crearBoton("Próximos a Vencer", "⏰", new Color(210, 255, 240));
        vencerBtn.addActionListener(e -> new InventarioScreen("vencer"));

        
        JButton vencidosBtn = crearBoton("Vencidos", "🗑️", new Color(255, 220, 220));
        vencidosBtn.addActionListener(e -> new InventarioScreen("vencidos"));

        
        JButton mermaBtn = crearBoton("% Merma", "📉", new Color(230, 230, 255));
        mermaBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de merma próximamente");
        });

        statsPanel.add(totalBtn);
        statsPanel.add(stockBtn);
        statsPanel.add(vencerBtn);
        statsPanel.add(vencidosBtn);
        statsPanel.add(mermaBtn);

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 5));
        navPanel.setBackground(Color.DARK_GRAY);

        JButton btnDashboard = crearBotonNav("🏠", "Dashboard", () -> {});
        JButton btnInventario = crearBotonNav("📋", "Inventario", () -> new InventarioScreen("todos"));
        JButton btnProductos = crearBotonNav("➕", "Productos", () -> new FormularioProductoScreen("agregar", null));
        JButton btnEtiquetas = crearBotonNav("🏷️", "Etiquetas", () -> new GestionEtiquetasScreen());
        JButton btnPerfil = crearBotonNav("👤", "Perfil", () -> new PerfilUsuarioScreen(userId));

        navPanel.add(btnDashboard);
        navPanel.add(btnInventario);
        navPanel.add(btnProductos);
        navPanel.add(btnEtiquetas);
        navPanel.add(btnPerfil);

        mainPanel.add(navPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton crearBoton(String texto, String emoji, Color color) {
        JButton boton = new JButton(emoji + "  " + texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    private JButton crearBotonNav(String icono, String nombre, Runnable action) {
        JButton boton = new JButton(icono + "\n" + nombre);
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setFocusPainted(false);
        boton.setBackground(Color.LIGHT_GRAY);
        boton.addActionListener(e -> {
            dispose(); 
            action.run(); 
        });
        return boton;
    }
}
