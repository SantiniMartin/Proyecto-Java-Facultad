package org.example.dao;

import org.example.models.Cliente;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public void agregarCliente(Cliente cliente){
        String sql = "INSERT INTO clientes (dni, nombre, apellido, direccion) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getDireccion());

            if (pstmt.executeUpdate() > 0){
                System.out.println("Usuario creado con éxito");
            }else {
                System.out.println("No sé pudo crear el usuario");
            }
            //Cerramos instancias de la BD
            conn.close();
            pstmt.close();
        }catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void buscarCliente(int id){
        String sql = "SELECT * FROM clientes WHERE cliente_id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int cliente_id = rs.getInt("cliente_id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");
                System.out.println("Cliente ID: " + cliente_id + ", DNI: " + dni + ", nombre: " + nombre + ", apellido: " + apellido + ", dirección: " + direccion);
            } else{
                System.out.println("Cliente no encontrado");
            }

            //Cerramos instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void actualizarCliente(int id, Cliente cliente){
        String sql = "UPDATE Clientes SET dni = ?, nombre = ?, apellido = ?, direccion = ? WHERE cliente_id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setInt(5, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cliente actualizado con éxito.");
            } else {
                System.out.println("No se encontró el cliente con el ID especificado.");
            }

            //Cerramos instancias de la BD
            conn.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void listarClientes(){
        String sql = "SELECT * FROM Clientes";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int cliente_id = rs.getInt("cliente_id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");
                System.out.println("Cliente ID: " + cliente_id + ", DNI: " + dni + ", nombre: " + nombre + ", apellido: " + apellido + ", dirección: " + direccion);
            }
            //Cerramos instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        }catch (SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    public void eliminarCliente(int id){
        String sql = "DELETE FROM clientes WHERE cliente_id = ?";
        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0){
                System.out.println("Cliente eliminado exitosamente.");
            }else {
                System.out.println("No se pudo eliminar el cliente.");
            }

            //Cerramos instancias de la BD
            conn.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }
}
