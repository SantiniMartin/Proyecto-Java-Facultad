package org.example.dao;

import org.example.models.Producto;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase ProductoDAO proporciona métodos para realizar operaciones CRUD en la base de datos
 * sobre objetos de tipo Cliente, como agregar, buscar, actualizar, listar y eliminar clientes.
 */
public class ProductoDAO {


    //Metodo que me permite agregar un Producto a la base de datos
    public void agregarProducto(int id, Producto producto){
        //Consulta SQL para agregar un producto a la tabla 'productos'
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, proveedor_id) VALUES (?, ?, ?, ?, ?)";
        try{

            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL con una instancia de la clase PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, String.valueOf(producto.getPrecio()));
            pstmt.setString(4, String.valueOf(producto.getStock()));
            pstmt.setString(5, String.valueOf(id));

            //Ejecuta la consulta y retorna un nuevo mayor a 0, en caso de que sea true la consulta fue un exito
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

    //Metodo que me permite buscar un Producto a la base de datos
    public void buscarProducto(int id){
        //Consulta SQL para traer un producto de la tabla 'productos'
        String sql = "SELECT * FROM productos WHERE producto_id = ?";
        try {
            //Establecemos una conexión con la base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL con una instancia de la clase PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos el ID a buscar de la consulta SQL
            pstmt.setInt(1, id);

            //Ejecutamos la consulta con el metodo executeQuery y guardamos el resultado en una Clase ResulSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            if (rs.next()){
                int producto_id = rs.getInt("producto_id");
                String nombre = rs.getString("nombre");
                String descipcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                String stock = rs.getString("stock");
                int proveedor_id = rs.getInt("proveedor_id");

                //Imprime por consola los datos obtenidos de la BD
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

    //Metodo que me permite actualizar un Producto a la base de datos
    public void actualizarProducto(int id, Producto producto, int proveedorID){

        //Consulta SQL para traer un producto de la tabla 'productos'
        String sql = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, proveedor_id = ? WHERE producto_id = ?";
        try{
            //Establecemos una conexión con la base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL con una instancia de la clase PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos los parametros que vamos a utilizar para la consulta SQL
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, String.valueOf(producto.getPrecio()));
            pstmt.setString(4, String.valueOf(producto.getStock()));
            pstmt.setInt(5, proveedorID);
            pstmt.setInt(6, id);

            //Ejecutamos la consulta SQL y obtenemos las columnas afectadas en la variable rowsUpdated
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

    //Metodo que me permite listar los Productos de la base de datos
    public void listarProductos(){

        //Consulta SQL para traer un producto de la tabla 'productos'
        String sql = "SELECT * FROM Productos";
        try{
            //Establecemos una conexión con la base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL con una instancia de la clase PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Ejecutamos la consulta SQL y obtenemos el resultado en una instancia del objeto ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            while (rs.next()){
                int producto_id = rs.getInt("producto_id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");
                String proveedorID = rs.getString("proveedor_id");

                //Muestra por consola los datos
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

    //Metodo que me permite eliminar un Producto a la base de datos
    public void eliminarProducto(int id){

        //Consulta SQL para traer un producto de la tabla 'productos'
        String sql = "DELETE FROM productos WHERE producto_id = ?";
        try{
            //Establecemos una conexión con la base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL con una instancia de la clase PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos la consulta SQL con el ID pasado por parametro
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
