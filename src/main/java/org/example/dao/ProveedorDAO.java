package org.example.dao;

import org.example.models.Proveedor;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorDAO {

    public void agregarProveedor(Proveedor proveedor){
        String sql = "INSERT INTO proveedores (nombre, cuit, direccion) VALUES (?, ?, ?)";
        try {

            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getCuit());
            pstmt.setString(3, proveedor.getDireccion());

            if (pstmt.executeUpdate() > 0){
                System.out.println("Proveedor creado con éxito");
            }else {
                System.out.println("No sé pudo crear el proveedor");
            }

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void buscarProveedor(int id){
        String sql = "SELECT * FROM proveedores WHERE proveedor_id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int proveedor_id = rs.getInt("proveedor_id");
                String nombre = rs.getString("nombre");
                String cuit = rs.getString("cuit");
                String direccion = rs.getString("direccion");
                System.out.println("Proveedor ID: " + proveedor_id + ", nombre: " + nombre + ", cuit: " + cuit + ", dirección: " + direccion);
            } else{
                System.out.println("Proveedor no encontrado");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void actualizarProveedor(int id, Proveedor proveedor){
        String sql = "UPDATE Proveedores SET nombre = ?, cuit = ?, direccion = ? WHERE cliente_id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getCuit());
            pstmt.setString(3, proveedor.getDireccion());
            pstmt.setInt(4, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Proveedor actualizado con éxito.");
            } else {
                System.out.println("No se encontró el proveedor con el ID especificado.");
            }

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void listarProveedores() {
        String sql = "SELECT * FROM Proveedores";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int proveedor_id = rs.getInt("proveedor_id");
                String nombre = rs.getString("nombre");
                String cuit = rs.getString("cuit");
                String direccion = rs.getString("direccion");
                System.out.println("Proveedor ID: " + proveedor_id + ", nombre: " + nombre + ", cuit: " + cuit + ", dirección: " + direccion);
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        }catch (SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void eliminarProveedor(int id){
        String sql = "DELETE FROM Proveedores WHERE proveedor_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0){
                System.out.println("Proveedor eliminado exitosamente.");
            }else {
                System.out.println("No se pudo eliminar el proveedor.");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

}
