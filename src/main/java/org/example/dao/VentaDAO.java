package org.example.dao;

import org.example.models.Venta;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.example.exceptions.RegistroNoEncontradoException;

public class VentaDAO {

    private boolean clienteExiste(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Clientes WHERE cliente_id = ?";
        try (Connection conn = ConexionDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private boolean productoExiste(int idProducto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE producto_id = ?";
        try (Connection conn = ConexionDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void agregarVenta(int idCliente, int idProducto, Venta venta){
        try {
            if (!clienteExiste(idCliente)){
                throw new RegistroNoEncontradoException("No existe el cliente, agregue antes de realizar la venta.");
            }
            if (!productoExiste(idProducto)) {
                throw new RegistroNoEncontradoException("No existe el producto, agregue antes de realizar la venta.");
            }
            String sql = "INSERT INTO ventas (fecha, monto_total, cliente_id, producto_id) VALUES (?, ?, ?, ?)";
            try{
                Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                LocalDate ventaActual = LocalDate.now();

                pstmt.setString(1, String.valueOf(ventaActual));
                pstmt.setString(2, String.valueOf(venta.getMontoTotal()));
                pstmt.setString(3, String.valueOf(idCliente));
                pstmt.setString(4, String.valueOf(idProducto));

                if (pstmt.executeUpdate() > 0){
                    System.out.println("Venta creada con éxito");
                }else {
                    System.out.println("No sé pudo procesar la venta");
                }
                //Cerramos las instancias de la BD
                conn.close();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
            }
        } catch (SQLException | RegistroNoEncontradoException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void actualizarVenta(int idVenta, int idCliente, int idProducto, Venta venta){
        String sql = "UPDATE Ventas SET fecha = ?, monto_total = ?, producto_id = ?, cliente_id = ? WHERE venta_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(venta.getFechaDeVenta()));
            pstmt.setString(2, String.valueOf(venta.getMontoTotal()));
            pstmt.setString(3, String.valueOf(idProducto));
            pstmt.setString(4, String.valueOf(idCliente));
            pstmt.setString(5, String.valueOf(idVenta));

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Venta actualizada con éxito.");
            } else {
                System.out.println("No se encontró la venta con el ID especificado.");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void buscarVentaPorId(int id){
        String sql = "SELECT * FROM ventas WHERE venta_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                int venta_id = rs.getInt("venta_id");
                double monto_total = rs.getDouble("monto_total");
                String fecha = rs.getString("fecha");
                String producto_id = rs.getString("producto_id");
                String cliente_id = rs.getString("cliente_id");

                System.out.println("Venta ID: " + venta_id + ", monto total: " + monto_total + ", fecha: " + fecha + ", cliente ID: " + cliente_id + ", producto ID: " + producto_id);
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void listarVentas(){
        String sql = "SELECT * FROM ventas";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int venta_id = rs.getInt("venta_id");
                double monto_total = rs.getDouble("monto_total");
                String fecha = rs.getString("fecha");
                String cliente_id = rs.getString("cliente_id");
                String producto_id = rs.getString("producto_id");
                System.out.println("Venta ID: " + venta_id + ", monto total: " + monto_total + ", fecha: " + fecha + ", cliente ID: " + cliente_id + ", producto ID: " + producto_id);
            }
            conn.close();
            pstmt.close();
            rs.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void eliminarVenta(int id){
        String sql = "DELETE FROM ventas WHERE venta_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }
}
