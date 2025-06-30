package com.backend.bodegabazar;

import java.util.Date;

public class Producto {
    private String id;
    private String userId;
    private String nombre; 
    private int cantidad;
    private int minimaCantidad;
    private String estado;
    private Date fechaIngreso;
    private Date fechaVencimiento;
    private int diasAntes;
    private String horaAlerta;
    private boolean alertaVencimiento;
    private boolean alertaStock;
    private boolean alertaSemanal;
    private int statusId;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMinimaCantidad() {
        return minimaCantidad;
    }

    public void setMinimaCantidad(int minimaCantidad) {
        this.minimaCantidad = minimaCantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getDiasAntes() {
        return diasAntes;
    }

    public void setDiasAntes(int diasAntes) {
        this.diasAntes = diasAntes;
    }

    public String getHoraAlerta() {
        return horaAlerta;
    }

    public void setHoraAlerta(String horaAlerta) {
        this.horaAlerta = horaAlerta;
    }

    public boolean isAlertaVencimiento() {
        return alertaVencimiento;
    }

    public void setAlertaVencimiento(boolean alertaVencimiento) {
        this.alertaVencimiento = alertaVencimiento;
    }

    public boolean isAlertaStock() {
        return alertaStock;
    }

    public void setAlertaStock(boolean alertaStock) {
        this.alertaStock = alertaStock;
    }

    public boolean isAlertaSemanal() {
        return alertaSemanal;
    }

    public void setAlertaSemanal(boolean alertaSemanal) {
        this.alertaSemanal = alertaSemanal;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
