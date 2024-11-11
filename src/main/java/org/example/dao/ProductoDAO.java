package org.example.dao;

import org.example.models.Producto;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {

    public void agregarProducto(int id, Producto producto){
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, proveedor_id) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, String.valueOf(producto.getPrecio()));
            pstmt.setString(4, String.valueOf(producto.getStock()));
            pstmt.setString(5, String.valueOf(id));

            if (pstmt.executeUpdate() > 0){
                System.out.println("Producto creado con éxito");
            }else {
                System.out.println("No sé pudo crear el producto");
            }

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void buscarProducto(int id){
        String sql = "SELECT * FROM productos WHERE producto_id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int producto_id = rs.getInt("producto_id");
                String nombre = rs.getString("nombre");
                String descipcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                String stock = rs.getString("stock");
                int proveedor_id = rs.getInt("proveedor_id");
                System.out.println("Producto ID: " + producto_id + ", nombre: " + nombre + ", descipción: " + descipcion + ", precio: " + precio + ", stock: " + stock + ", proveedor_id: " + proveedor_id);
            } else{
                System.out.println("Cliente no encontrado");
            }

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void actualizarProducto(int id, Producto producto, int proveedorID){
        String sql = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, proveedor_id = ? WHERE producto_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, String.valueOf(producto.getPrecio()));
            pstmt.setInt(4, proveedorID);
            pstmt.setInt(5, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Producto actualizado con éxito.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void listarProductos(){
        String sql = "SELECT * FROM Productos";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int producto_id = rs.getInt("producto_id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");
                String proveedorID = rs.getString("proveedor_id");
                System.out.println("Producto ID: " + producto_id + ", nombre: " + nombre + ", descipción: " + descripcion + ", precio: " + precio + ", stock: " + stock + ", proveedor: " + proveedorID);
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void eliminarProducto(int id){
        String sql = "DELETE FROM productos WHERE producto_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0){
                System.out.println("Producto eliminado exitosamente.");
            }else {
                System.out.println("No se pudo eliminar el producto.");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }
}
