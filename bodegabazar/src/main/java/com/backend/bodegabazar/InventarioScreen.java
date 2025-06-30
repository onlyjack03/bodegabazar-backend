package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventarioScreen extends JFrame {

    public InventarioScreen(String filtro) {
        setTitle("Inventario");
        setSize(400, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Inventario", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] filtros = {"Todos", "Activo", "Próximo a vencer", "Vencidos"};
        for (String f : filtros) {
            JButton btnFiltro = new JButton(f);
            btnFiltro.addActionListener(e -> {
                dispose();
                new InventarioScreen(f.toLowerCase());
            });
            filterPanel.add(btnFiltro);
        }

       
        JPanel listaProductosPanel = new JPanel();
        listaProductosPanel.setLayout(new BoxLayout(listaProductosPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listaProductosPanel);

        List<Producto> productos = ProductoDAO.obtenerProductosPorFiltro(filtro);
        for (Producto p : productos) {
            JPanel tarjeta = crearTarjetaProducto(p);
            listaProductosPanel.add(tarjeta);
            listaProductosPanel.add(Box.createVerticalStrut(10));
        }

        
        JButton botonAgregar = new JButton("+");
        botonAgregar.setFont(new Font("Arial", Font.BOLD, 24));
        botonAgregar.setForeground(Color.WHITE);
        botonAgregar.setBackground(new Color(143, 102, 255));
        botonAgregar.setBounds(310, 580, 60, 60);
        botonAgregar.setFocusPainted(false);
        botonAgregar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        botonAgregar.addActionListener(e -> new FormularioProductoScreen("agregar", null));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 700));
        scrollPane.setBounds(0, 80, 400, 500);
        botonAgregar.setBounds(320, 600, 50, 50);
        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(botonAgregar, JLayeredPane.PALETTE_LAYER);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(layeredPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel crearTarjetaProducto(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(360, 80));

        JLabel nombre = new JLabel(producto.getNombre());
        nombre.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel cantidad = new JLabel("Cantidad: " + producto.getCantidad());
        JLabel vencimiento = new JLabel("Vence: " + producto.getFechaVencimiento());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nombre);
        infoPanel.add(cantidad);
        infoPanel.add(vencimiento);

        JButton opciones = new JButton("...");
        opciones.addActionListener(e -> {
            Object[] options = {"Editar", "Eliminar", "Cancelar"};
            int r = JOptionPane.showOptionDialog(this,
                    "¿Qué desea hacer con el producto?",
                    producto.getNombre(),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (r == 0) {
                new FormularioProductoScreen("editar", producto);
                dispose();
            }
            
        });

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(opciones, BorderLayout.EAST);

        return panel;
    }
}
