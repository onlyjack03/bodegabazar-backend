package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GestionEtiquetasScreen extends JFrame {

    private DefaultListModel<String> modeloLista;
    private JList<String> listaEtiquetas;
    private JTextField campoEtiqueta;
    private JButton botonAgregar, botonEditar, botonEliminar;

    public GestionEtiquetasScreen() {
        setTitle("Gestión de Etiquetas");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloLista = new DefaultListModel<>();
        listaEtiquetas = new JList<>(modeloLista);
        cargarEtiquetas();

        campoEtiqueta = new JTextField();
        campoEtiqueta.setBorder(BorderFactory.createTitledBorder("Etiqueta"));

        botonAgregar = new JButton("Agregar");
        botonEditar = new JButton("Editar");
        botonEliminar = new JButton("Eliminar");

        botonAgregar.addActionListener(e -> agregarEtiqueta());
        botonEditar.addActionListener(e -> editarEtiqueta());
        botonEliminar.addActionListener(e -> eliminarEtiqueta());

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        panelBotones.add(botonAgregar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(campoEtiqueta, BorderLayout.NORTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(new JScrollPane(listaEtiquetas), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarEtiquetas() {
        modeloLista.clear();
        List<String> etiquetas = ProductoDAO.obtenerTodasEtiquetas();
        for (String etiqueta : etiquetas) {
            modeloLista.addElement(etiqueta);
        }
    }

    private void agregarEtiqueta() {
        String nombre = campoEtiqueta.getText().trim();
        if (!nombre.isEmpty()) {
            if (ProductoDAO.insertarEtiqueta(nombre)) {
                campoEtiqueta.setText("");
                cargarEtiquetas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar. Verifica si ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarEtiqueta() {
        String seleccionada = listaEtiquetas.getSelectedValue();
        String nueva = campoEtiqueta.getText().trim();
        if (seleccionada != null && !nueva.isEmpty()) {
            if (ProductoDAO.actualizarEtiqueta(seleccionada, nueva)) {
                campoEtiqueta.setText("");
                cargarEtiquetas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar etiqueta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEtiqueta() {
        String seleccionada = listaEtiquetas.getSelectedValue();
        if (seleccionada != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar etiqueta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (ProductoDAO.eliminarEtiqueta(seleccionada)) {
                    cargarEtiquetas();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
