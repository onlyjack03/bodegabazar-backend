package com.backend.bodegabazar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ProductoDAO {

    public static List<Producto> obtenerProductosPorFiltro(String filtro) {
        List<Producto> lista = new ArrayList<>();

        String sqlBase = "SELECT * FROM products WHERE is_enabled = 1";
        String sql = sqlBase;

        if (filtro.equals("vencer")) {
            sql += " AND DATEDIFF(expiration_date, CURDATE()) <= 7 AND DATEDIFF(expiration_date, CURDATE()) > 0";
        } else if (filtro.equals("vencidos")) {
            sql += " AND expiration_date < CURDATE()";
        } else if (filtro.equals("bajo")) {
            sql += " AND quantity <= low_stock_threshold";
        }

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getString("id"));
                p.setNombre(rs.getString("name"));
                p.setCantidad(rs.getInt("quantity"));
                p.setFechaIngreso(rs.getDate("entry_date"));
                p.setFechaVencimiento(rs.getDate("expiration_date"));
                p.setMinimaCantidad(rs.getInt("low_stock_threshold"));
                p.setEstado(rs.getInt("is_enabled") == 1 ? "Habilitado" : "Deshabilitado");
                p.setHoraAlerta(rs.getString("alert_time"));
                p.setDiasAntes(rs.getInt("notify_days_before"));
                p.setAlertaVencimiento(rs.getBoolean("use_expiration_alert"));
                p.setAlertaStock(rs.getBoolean("use_low_stock_alert"));
                p.setAlertaSemanal(rs.getBoolean("use_recurrent_alert"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static boolean guardarProducto(Producto producto) {
        String sql = "INSERT INTO products (id, user_id, name, entry_date, expiration_date, quantity, is_enabled, status_id, notify_days_before, low_stock_threshold, use_low_stock_alert, use_expiration_alert, use_recurrent_alert, alert_time, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getId());
            ps.setString(2, producto.getUserId());
            ps.setString(3, producto.getNombre());
            ps.setDate(4, new java.sql.Date(producto.getFechaIngreso().getTime()));
            ps.setDate(5, new java.sql.Date(producto.getFechaVencimiento().getTime()));
            ps.setInt(6, producto.getCantidad());
            ps.setInt(7, producto.getEstado().equalsIgnoreCase("Habilitado") ? 1 : 0);
            ps.setInt(8, producto.getStatusId());
            ps.setInt(9, producto.getDiasAntes());
            ps.setInt(10, producto.getMinimaCantidad());
            ps.setBoolean(11, producto.isAlertaStock());
            ps.setBoolean(12, producto.isAlertaVencimiento());
            ps.setBoolean(13, producto.isAlertaSemanal());
            ps.setString(14, producto.getHoraAlerta());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    


    public static boolean actualizarProducto(Producto p) {
    String sql = "UPDATE products SET name = ?, entry_date = ?, expiration_date = ?, quantity = ?, is_enabled = ?, status_id = ?, notify_days_before = ?, low_stock_threshold = ?, use_low_stock_alert = ?, use_expiration_alert = ?, use_recurrent_alert = ?, alert_time = ? WHERE id = ?";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, p.getNombre());
        ps.setDate(2, new java.sql.Date(p.getFechaIngreso().getTime()));
        ps.setDate(3, new java.sql.Date(p.getFechaVencimiento().getTime()));
        ps.setInt(4, p.getCantidad());
        ps.setInt(5, p.getEstado().equalsIgnoreCase("Habilitado") ? 1 : 0);
        ps.setInt(6, p.getStatusId());
        ps.setInt(7, p.getDiasAntes());
        ps.setInt(8, p.getMinimaCantidad());
        ps.setBoolean(9, p.isAlertaStock());
        ps.setBoolean(10, p.isAlertaVencimiento());
        ps.setBoolean(11, p.isAlertaSemanal());
        ps.setString(12, p.getHoraAlerta());
        ps.setString(13, p.getId());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public static List<String> obtenerTodasEtiquetas() {
    List<String> etiquetas = new ArrayList<>();
    String sql = "SELECT name FROM tags";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            etiquetas.add(rs.getString("name"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return etiquetas;
}

public static void insertarEtiquetaProducto(String productoId, String nombreEtiqueta) {
    String sql = "INSERT INTO product_tags (product_id, tag_id) VALUES (?, (SELECT id FROM tags WHERE name = ?))";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, productoId);
        ps.setString(2, nombreEtiqueta);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void eliminarEtiquetasProducto(String productoId) {
    String sql = "DELETE FROM product_tags WHERE product_id = ?";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, productoId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void insertarDiaProducto(String productoId, int idDia) {
    String sql = "INSERT INTO product_alert_days (product_id, day_id) VALUES (?, ?)";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, productoId);
        ps.setInt(2, idDia);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void eliminarDiasProducto(String productoId) {
    String sql = "DELETE FROM product_alert_days WHERE product_id = ?";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, productoId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static boolean insertarEtiqueta(String nombre) {
    String sql = "INSERT INTO tags (id, name) VALUES (?, ?)";
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, nombre);
        ps.executeUpdate();
        return true;

    } catch (Exception e) {
        return false;
    }
}

public static boolean actualizarEtiqueta(String anterior, String nuevo) {
    String sql = "UPDATE tags SET name = ? WHERE name = ?";
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nuevo);
        ps.setString(2, anterior);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        return false;
    }
}

public static boolean eliminarEtiqueta(String nombre) {
    String sql = "DELETE FROM tags WHERE name = ?";
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombre);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        return false;
    }
}


}
