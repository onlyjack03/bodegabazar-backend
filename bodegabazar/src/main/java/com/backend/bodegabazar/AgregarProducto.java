package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AgregarProducto extends JFrame {

    public AgregarProducto() {
        setTitle("Agregar Producto");
        setSize(500, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nombreField = new JTextField();
        JComboBox<String> estadoBox = new JComboBox<>(new String[]{"Habilitado", "Deshabilitado"});
        JTextField etiquetasField = new JTextField();

        JTextField fechaIngresoField = new JTextField("dd/mm/yyyy");
        JTextField fechaVencimientoField = new JTextField("dd/mm/yyyy");
        JTextField cantidadField = new JTextField();

        JCheckBox vencimientoCheck = new JCheckBox("Alerta de vencimiento");
        JCheckBox stockCheck = new JCheckBox("Alerta de bajo stock");
        JCheckBox semanalCheck = new JCheckBox("Alerta semanal");

        JTextField horaAlertaField = new JTextField("09:00 AM");
        JTextField diasAntesField = new JTextField("5 o 10 días antes");
        JTextField cantidadMinField = new JTextField();
        JTextField diasAlertaField = new JTextField("Ej: Lunes, Martes");

        JButton agregarButton = new JButton("Agregar");
        JButton cancelarButton = new JButton("Cancelar");

        // Agrega los componentes
        mainPanel.add(new JLabel("Nombre*"));
        mainPanel.add(nombreField);
        mainPanel.add(new JLabel("Estado de acción*"));
        mainPanel.add(estadoBox);
        mainPanel.add(new JLabel("Etiquetas"));
        mainPanel.add(etiquetasField);
        mainPanel.add(new JLabel("Fecha de ingreso*"));
        mainPanel.add(fechaIngresoField);
        mainPanel.add(new JLabel("Fecha de vencimiento*"));
        mainPanel.add(fechaVencimientoField);
        mainPanel.add(new JLabel("Cantidad"));
        mainPanel.add(cantidadField);
        mainPanel.add(new JLabel("Tipo de alertas"));
        mainPanel.add(vencimientoCheck);
        mainPanel.add(stockCheck);
        mainPanel.add(semanalCheck);
        mainPanel.add(new JLabel("Hora de alerta"));
        mainPanel.add(horaAlertaField);
        mainPanel.add(new JLabel("Días antes de notificar"));
        mainPanel.add(diasAntesField);
        mainPanel.add(new JLabel("Cantidad mínima"));
        mainPanel.add(cantidadMinField);
        mainPanel.add(new JLabel("Alertas (días)"));
        mainPanel.add(diasAlertaField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelarButton);
        buttonPanel.add(agregarButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);

        agregarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            String estado = (String) estadoBox.getSelectedItem();
            String etiquetas = etiquetasField.getText();
            String fechaIngreso = fechaIngresoField.getText();
            String fechaVencimiento = fechaVencimientoField.getText();
            String cantidad = cantidadField.getText();
            boolean alertaVencimiento = vencimientoCheck.isSelected();
            boolean alertaStock = stockCheck.isSelected();
            boolean alertaSemanal = semanalCheck.isSelected();
            String hora = horaAlertaField.getText();
            String diasNotificar = diasAntesField.getText();
            String cantidadMin = cantidadMinField.getText();
            String dias = diasAlertaField.getText();

            // Aquí deberías llamar a un método DAO para guardar los datos en la base de datos.
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente (simulado)");
        });

        cancelarButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        new AgregarProducto();
    }
}
