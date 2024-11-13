package org.example.dao;

import org.example.models.Proveedor;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase ProveedorDAO proporciona métodos para realizar operaciones CRUD en la base de datos
 * sobre objetos de tipo Cliente, como agregar, buscar, actualizar, listar y eliminar clientes.
 */
public class ProveedorDAO {

    //Metodo que me permite agregar un proveedor
    public void agregarProveedor(Proveedor proveedor){

        //Consulta SQL a ejecutar
        String sql = "INSERT INTO proveedores (nombre, cuit, direccion) VALUES (?, ?, ?)";
        try {

            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getCuit());
            pstmt.setString(3, proveedor.getDireccion());

            //Ejecuta la consulta y retorna un nuevo mayor a 0, en caso de que sea true la consulta fue un exito
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

    //Metodo que me permite buscar un proveedor
    public void buscarProveedor(int id){

        //Consulta SQL a ejecutar
        String sql = "SELECT * FROM proveedores WHERE proveedor_id = ?";
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
                int proveedor_id = rs.getInt("proveedor_id");
                String nombre = rs.getString("nombre");
                String cuit = rs.getString("cuit");
                String direccion = rs.getString("direccion");

                //Muestra por consola los datos
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

    //Metodo que me permite actualizar un proveedor
    public void actualizarProveedor(int id, Proveedor proveedor){

        //Consulta SQL a ejecutar
        String sql = "UPDATE Proveedores SET nombre = ?, cuit = ?, direccion = ? WHERE cliente_id = ?";
        try {

            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getCuit());
            pstmt.setString(3, proveedor.getDireccion());
            pstmt.setInt(4, id);

            //Ejecutamos la consulta SQL y obtenemos las columnas afectadas en la variable rowsUpdated
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

    //Metodo que me permite listar los proveedores
    public void listarProveedores() {

        //Consulta SQL a ejecutar
        String sql = "SELECT * FROM Proveedores";
        try {

            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Ejecutamos la consulta SQL y obtenemos el resultado en una instancia del objeto ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            while (rs.next()){
                int proveedor_id = rs.getInt("proveedor_id");
                String nombre = rs.getString("nombre");
                String cuit = rs.getString("cuit");
                String direccion = rs.getString("direccion");

                //Muestra por consola los datos
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

    //Metodo que me permite eliminar un proveedor
    public void eliminarProveedor(int id){

        //Consulta SQL a ejecutar
        String sql = "DELETE FROM Proveedores WHERE proveedor_id = ?";
        try{

            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos la consulta SQL por el ID pasado por parametro
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
