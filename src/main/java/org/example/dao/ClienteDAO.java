package org.example.dao;

import org.example.models.Cliente;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase ClienteDAO proporciona métodos para realizar operaciones CRUD en la base de datos
 * sobre objetos de tipo Cliente, como agregar, buscar, actualizar, listar y eliminar clientes.
 */
public class ClienteDAO {

    //Metodo que me permite agregar un cliente
    public void agregarCliente(Cliente cliente){
        //Consulta SQL a ejecutar
        String sql = "INSERT INTO clientes (dni, nombre, apellido, direccion) VALUES (?, ?, ?, ?)";
        try {
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getDireccion());

            //Ejecuta la consulta y retorna un nuevo mayor a 0, en caso de que sea true la consulta fue un exito
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

    //Metodo que me permite buscar un cliente por ID
    public void buscarCliente(int id){
        //Consulta SQL a ejecutar
        String sql = "SELECT * FROM clientes WHERE cliente_id = ?";
        try {
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos la consulta SQL con el ID pasado por parametro
            pstmt.setInt(1, id);

            //Ejecutamos la consulta SQL y obtenemos el resultado en una instancia del objeto ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            if (rs.next()){
                int cliente_id = rs.getInt("cliente_id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");

                //Muestra por consola los datos
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

    //Metodo que me permite actualizar un cliente en base al ID
    public void actualizarCliente(int id, Cliente cliente){
        //Consulta SQL a ejecutar
        String sql = "UPDATE Clientes SET dni = ?, nombre = ?, apellido = ?, direccion = ? WHERE cliente_id = ?";
        try {
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setInt(5, id);

            //Ejecutamos la consulta SQL y obtenemos las columnas afectadas en la variable rowsUpdated
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

    //Metodo que me permite listar a los clientes
    public void listarClientes(){
        //Consulta SQL a ejecutar
        String sql = "SELECT * FROM Clientes";
        try {
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Ejecutamos la consulta SQL y obtenemos el resultado en una instancia del objeto ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            while (rs.next()){
                int cliente_id = rs.getInt("cliente_id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");

                //Muestra por consola los datos
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

    //Metodo que me permite eliminar un cliente
    public void eliminarCliente(int id){
        //Consulta SQL a ejecutar
        String sql = "DELETE FROM clientes WHERE cliente_id = ?";
        try{
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos la consulta SQL por el ID pasado por parametro
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
