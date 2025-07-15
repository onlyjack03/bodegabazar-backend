package com.backend.bodegabazar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class FormularioProductoScreen extends JFrame {

    private JTextField nombreField, cantidadField, cantidadMinimaField, horaAlertaField, diasAntesField;
    private JComboBox<String> estadoCombo;
    private JCheckBox alertaVencimiento, alertaStock, alertaSemanal;
    private JCheckBox[] diasSemanaChecks;
    private JTextField fechaIngresoField, fechaVencimientoField;
    private JComboBox<String> etiquetasCombo;
    private DefaultListModel<String> etiquetasSeleccionadas;
    private JList<String> listaEtiquetas;
    private JButton btnAgregar;

    private Producto productoActual;
    private boolean modoEditar;
    private int userId;

    
    public FormularioProductoScreen(String modo, Producto producto) {
        this.modoEditar = modo.equalsIgnoreCase("editar");
        this.productoActual = producto;

        setTitle(modoEditar ? "Editar Producto" : "Agregar Producto");
        setSize(400, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

 
        nombreField = crearCampo("Nombre del producto");
        cantidadField = crearCampo("Cantidad");
        cantidadMinimaField = crearCampo("Cantidad mínima");
        horaAlertaField = crearCampo("Hora de alerta (Ej: 09:00)");
        diasAntesField = crearCampo("Días antes de notificar");

        fechaIngresoField = crearCampo("Fecha de ingreso (yyyy-MM-dd)");
        fechaVencimientoField = crearCampo("Fecha de vencimiento (yyyy-MM-dd)");

        estadoCombo = new JComboBox<>(new String[]{"Habilitado", "Deshabilitado"});
        alertaVencimiento = new JCheckBox("Alerta de vencimiento");
        alertaStock = new JCheckBox("Alerta de bajo stock");
        alertaSemanal = new JCheckBox("Alerta semanal");

   
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        diasSemanaChecks = new JCheckBox[dias.length];
        JPanel diasPanel = new JPanel(new GridLayout(2, 4));
        for (int i = 0; i < dias.length; i++) {
            diasSemanaChecks[i] = new JCheckBox(dias[i]);
            diasPanel.add(diasSemanaChecks[i]);
        }

       
        etiquetasCombo = new JComboBox<>();
        etiquetasSeleccionadas = new DefaultListModel<>();
        listaEtiquetas = new JList<>(etiquetasSeleccionadas);

        JButton agregarEtiqueta = new JButton("+");
        agregarEtiqueta.addActionListener(e -> {
            String etiqueta = (String) etiquetasCombo.getSelectedItem();
            if (etiqueta != null && !etiquetasSeleccionadas.contains(etiqueta)) {
                etiquetasSeleccionadas.addElement(etiqueta);
            }
        });

        cargarEtiquetasDesdeBD();

        JPanel etiquetasPanel = new JPanel(new BorderLayout());
        etiquetasPanel.add(etiquetasCombo, BorderLayout.CENTER);
        etiquetasPanel.add(agregarEtiqueta, BorderLayout.EAST);
        etiquetasPanel.setBorder(BorderFactory.createTitledBorder("Etiquetas"));

        JScrollPane scrollEtiquetas = new JScrollPane(listaEtiquetas);

        
        btnAgregar = new JButton(modoEditar ? "Guardar Cambios" : "Agregar Producto");
        btnAgregar.setBackground(new Color(143, 102, 255));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> guardarProducto());

        
        panel.add(nombreField);
        panel.add(cantidadField);
        panel.add(cantidadMinimaField);
        panel.add(horaAlertaField);
        panel.add(diasAntesField);
        panel.add(fechaIngresoField);
        panel.add(fechaVencimientoField);
        panel.add(new JLabel("Estado de acción:"));
        panel.add(estadoCombo);
        panel.add(alertaVencimiento);
        panel.add(alertaStock);
        panel.add(alertaSemanal);
        panel.add(diasPanel);
        panel.add(etiquetasPanel);
        panel.add(scrollEtiquetas);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAgregar);

        add(new JScrollPane(panel));

        JButton btnRegresar = new JButton("Regresar");
btnRegresar.setBackground(new Color(200, 200, 200));
btnRegresar.setFocusPainted(false);
btnRegresar.addActionListener(e -> {
    String userId = productoActual != null ? productoActual.getUserId() : "test-user-id";
    DashboardScreen dashboard = new DashboardScreen(userId);
    dashboard.setVisible(true);  // Mostrar la ventana
    dispose(); // Cierra la pantalla actual
});


panel.add(Box.createVerticalStrut(10));
panel.add(btnRegresar);



        setVisible(true);

        if (modoEditar && producto != null) {
            cargarDatosProducto();
        }
    }

    private JTextField crearCampo(String placeholder) {
        JTextField campo = new JTextField();
        campo.setBorder(BorderFactory.createTitledBorder(placeholder));
        return campo;
    }

        private void guardarProducto() {
        try {
            
            String nombre = nombreField.getText().trim();
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            int minima = Integer.parseInt(cantidadMinimaField.getText().trim());
            String hora = horaAlertaField.getText().trim();
            int diasAntes = Integer.parseInt(diasAntesField.getText().trim());

            java.util.Date fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIngresoField.getText().trim());
            java.util.Date fechaVenc = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVencimientoField.getText().trim());

            String estado = (String) estadoCombo.getSelectedItem();

            Producto p = modoEditar ? productoActual : new Producto();
            if (!modoEditar) {
                p.setId(UUID.randomUUID().toString());
                p.setUserId("test-user-id"); 
            }

            p.setNombre(nombre);
            p.setCantidad(cantidad);
            p.setMinimaCantidad(minima);
            p.setHoraAlerta(hora);
            p.setDiasAntes(diasAntes);
            p.setFechaIngreso(fechaIngreso);
            p.setFechaVencimiento(fechaVenc);
            p.setEstado(estado);
            p.setAlertaVencimiento(alertaVencimiento.isSelected());
            p.setAlertaStock(alertaStock.isSelected());
            p.setAlertaSemanal(alertaSemanal.isSelected());
            p.setStatusId(1); 
            boolean exito;
            if (modoEditar) {
                exito = ProductoDAO.actualizarProducto(p);
            } else {
                exito = ProductoDAO.guardarProducto(p);
            }

            if (exito) {
                guardarEtiquetasProducto(p.getId());
                guardarDiasProducto(p.getId());
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar producto", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarEtiquetasProducto(String productoId) {
        ProductoDAO.eliminarEtiquetasProducto(productoId);
        for (int i = 0; i < etiquetasSeleccionadas.size(); i++) {
            String etiqueta = etiquetasSeleccionadas.getElementAt(i);
            ProductoDAO.insertarEtiquetaProducto(productoId, etiqueta);
        }
    }

    private void guardarDiasProducto(String productoId) {
        ProductoDAO.eliminarDiasProducto(productoId);
        for (int i = 0; i < diasSemanaChecks.length; i++) {
            if (diasSemanaChecks[i].isSelected()) {
                ProductoDAO.insertarDiaProducto(productoId, i + 1); 
            }
        }
    }

    private void cargarEtiquetasDesdeBD() {
        etiquetasCombo.removeAllItems();
        List<String> etiquetas = ProductoDAO.obtenerTodasEtiquetas();
        for (String etiqueta : etiquetas) {
            etiquetasCombo.addItem(etiqueta);
        }
    }

    private void cargarDatosProducto() {
        nombreField.setText(productoActual.getNombre());
        cantidadField.setText(String.valueOf(productoActual.getCantidad()));
        cantidadMinimaField.setText(String.valueOf(productoActual.getMinimaCantidad()));
        horaAlertaField.setText(productoActual.getHoraAlerta());
        diasAntesField.setText(String.valueOf(productoActual.getDiasAntes()));
        fechaIngresoField.setText(new SimpleDateFormat("yyyy-MM-dd").format(productoActual.getFechaIngreso()));
        fechaVencimientoField.setText(new SimpleDateFormat("yyyy-MM-dd").format(productoActual.getFechaVencimiento()));
        estadoCombo.setSelectedItem(productoActual.getEstado());
        alertaVencimiento.setSelected(productoActual.isAlertaVencimiento());
        alertaStock.setSelected(productoActual.isAlertaStock());
        alertaSemanal.setSelected(productoActual.isAlertaSemanal());

        
    }
}
