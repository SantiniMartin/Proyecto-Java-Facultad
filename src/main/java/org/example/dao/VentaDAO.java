package org.example.dao;

import org.example.models.Venta;
import org.example.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.example.exceptions.RegistroNoEncontradoException;

/**
 * La clase VentaDAO proporciona métodos para realizar operaciones CRUD en la base de datos
 * sobre objetos de tipo Cliente, como agregar, buscar, actualizar, listar y eliminar clientes.
 */
public class VentaDAO {

    //Metodo que valida en la base de datos si existe o no un cliente
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

    //Metodo que valida en la base de datos si existe o no un producto
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

    //Metodo que valida el stock del producto en la base de datos
    private int obtenerStock(int idProducto){

        //Consulta SQL a ejecutar
        String sql = "SELECT stock FROM productos WHERE producto_id = ?";
        try{
            //Establece la conexión a la Base de datos
            Connection conn = ConexionDB.conectar();

            //Prepara la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Setea el ID pasado por parametro a la consulta SQL
            pstmt.setInt(1, idProducto);

            //Ejecuta la consulta con el metodo executeQuery y guarda los datos en una clase de tipo ResulSet
            ResultSet rs = pstmt.executeQuery();

            //Si en la instancia de ResulSet rs encuentra datos con el metodo next
            if (rs.next()) {
                return rs.getInt("stock");
            } else {
                throw new RegistroNoEncontradoException("Producto no encontrado");
            }
        }catch (SQLException | RegistroNoEncontradoException e){
            System.out.println("Se producjo en error en la base de datos" + e.getMessage());
        }
        return 0;
    }

    //Metodo que me permite agregar una venta
    public void agregarVenta(int idCliente, int idProducto, Venta venta){

        //Obtenemos el stock de la base de datos
        int stockDisponible = obtenerStock(idProducto);

        //Validamos si el stock es mayor a la cantidad de productos que se quiere vender
        if (stockDisponible < venta.getCantidadProducto()) {
            System.out.println("No hay suficiente stock del producto.");
            return;
        }

        try {
            if (!clienteExiste(idCliente)){
                throw new RegistroNoEncontradoException("No existe el cliente, agregue antes de realizar la venta.");
            }
            if (!productoExiste(idProducto)) {
                throw new RegistroNoEncontradoException("No existe el producto, agregue antes de realizar la venta.");
            }

            //Consulta SQL para actualizar el stock de los productos
            String sqlActualizarStock = "UPDATE productos SET stock = stock - ? WHERE producto_id = ?";

            //Consulta SQL para ingresar una venta en la base de datos
            String sql = "INSERT INTO ventas (fecha, monto_total, cantidad, cliente_id, producto_id) VALUES (?, ?, ?, ?, ?)";

            try{
                //Se establece la conexión a la base de datos
                Connection conn = ConexionDB.conectar();

                //Se prepara la consulta SQL
                PreparedStatement pstmt = conn.prepareStatement(sql);

                //Guardamos la fecha actual para cargar en la venta
                LocalDate ventaActual = LocalDate.now();

                //Seteamos los parametros que vamos a utilizar para la consulta SQL
                pstmt.setString(1, String.valueOf(ventaActual));
                pstmt.setString(2, String.valueOf(venta.getMontoTotal()));
                pstmt.setString(3, String.valueOf(venta.getCantidadProducto()));
                pstmt.setString(4, String.valueOf(idCliente));
                pstmt.setString(5, String.valueOf(idProducto));

                //Si otenemos un número distinto a 0 quiere decir que se ejecuto la consulta SQL correctamente
                if (pstmt.executeUpdate() > 0){
                    System.out.println("Venta creada con éxito");

                    //Validamos si se ejecuta la consulta SQL
                    try (PreparedStatement pstmtUpdateStock = conn.prepareStatement(sqlActualizarStock)) {

                        //Seteamos la cantidad de producto a comprar
                        pstmtUpdateStock.setInt(1, venta.getCantidadProducto());

                        //Seteamos el ID del producto a comprar
                        pstmtUpdateStock.setInt(2, idProducto);

                        //Si otenemos un número distinto a 0 quiere decir que se ejecuto la consulta SQL correctamente y se actualiza el stock
                        if (pstmtUpdateStock.executeUpdate() > 0) {
                            System.out.println("Stock actualizado correctamente");
                        } else {
                            conn.rollback(); // Deshace la venta si falla la actualización del stock
                            System.out.println("No se pudo actualizar el stock del producto");
                        }
                    }
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

    //Metodo para actualizar una venta realizada
    public void actualizarVenta(int idVenta, int idCliente, int idProducto, Venta venta){

        //Obtenemos el stock del producto en la base de datos
        int stockDisponible = obtenerStock(idProducto);

        //Validamos si tenemos el stock disponible
        if (stockDisponible < venta.getCantidadProducto()) {
            System.out.println("No hay suficiente stock del producto.");
            return;
        }

        //Consulta SQL para setear el stock actual
        String sqlActualizarStock = "UPDATE productos SET stock = stock - ? WHERE producto_id = ?";

        //Consulta SQL para actualizar una venta
        String sql = "UPDATE Ventas SET fecha = ?, monto_total = ?, cantidad = ?, producto_id = ?, cliente_id = ? WHERE venta_id = ?";
        try{
            //Establecemos conexión a la base de datos
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos la consulta SQL con los parametros obtenidos en el objeto pasado por parametro
            pstmt.setString(1, String.valueOf(venta.getFechaDeVenta()));
            pstmt.setString(2, String.valueOf(venta.getMontoTotal()));
            pstmt.setString(3, String.valueOf(venta.getCantidadProducto()));
            pstmt.setString(4, String.valueOf(idProducto));
            pstmt.setString(5, String.valueOf(idCliente));
            pstmt.setString(6, String.valueOf(idVenta));

            //Ejecutamos la consulta SQL y obtenemos el total de las columnas modificadas
            int rowsUpdated = pstmt.executeUpdate();

            //Validamos si hay columnas modificadas lo que indica que la venta se realizó con exito
            if (rowsUpdated > 0) {
                try (PreparedStatement pstmtUpdateStock = conn.prepareStatement(sqlActualizarStock)) {
                    pstmtUpdateStock.setInt(1, venta.getCantidadProducto());
                    pstmtUpdateStock.setInt(2, idProducto);

                    if (pstmtUpdateStock.executeUpdate() > 0) {
                        System.out.println("Stock actualizado correctamente");
                    } else {
                        conn.rollback(); // Deshace la venta si falla la actualización del stock
                        System.out.println("No se pudo actualizar el stock del producto");
                    }
                }
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

    //Metodo utilizado para buscar una venta por ID
    public void buscarVentaPorId(int id){

        //Consulta SQL para obtener una venta por ID
        String sql = "SELECT * FROM ventas WHERE venta_id = ?";
        try{

            //Establecemos una conexión a la BD
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos el ID de la consulta SQL
            pstmt.setInt(1, id);

            //Ejecutamos la consulta SQL y obtenemos el resultado en una instancia del objeto ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Se valida si se obtuvieron datos y se recorre la instancia rs
            if (rs.next()){
                int venta_id = rs.getInt("venta_id");
                double monto_total = rs.getDouble("monto_total");
                String cantidad = rs.getString("cantidad");
                String fecha = rs.getString("fecha");
                String producto_id = rs.getString("producto_id");
                String cliente_id = rs.getString("cliente_id");

                //Se muestra por consola el resultado
                System.out.println("Venta ID: " + venta_id + ", monto total: " + monto_total + ", cantidad: " + cantidad + ", fecha: " + fecha + ", cliente ID: " + cliente_id + ", producto ID: " + producto_id);
            }else {
                System.out.println("No se realizaron ventas");
            }
            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
            rs.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }

    //Metodo para listar ventas
    public void listarVentas(){

        //Consulta SQL para obtener las ventas de la tabla 'ventas'
        String sql = "SELECT * FROM ventas";
        try{

            //Establecemos la conexión a la BD
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Ejecutamos la consulta SQL y guardamos en una instancia de la clase ResultSet
            ResultSet rs = pstmt.executeQuery();

            //Recorre los datos obtenidos en rs
            while(rs.next()){
                int venta_id = rs.getInt("venta_id");
                double monto_total = rs.getDouble("monto_total");
                String cantidad = rs.getString("cantidad");
                String fecha = rs.getString("fecha");
                String cliente_id = rs.getString("cliente_id");
                String producto_id = rs.getString("producto_id");

                //Muestra por consola los datos
                System.out.println("Venta ID: " + venta_id + ", monto total: " + monto_total + ", cantidad: " + cantidad + ", fecha: " + fecha + ", cliente ID: " + cliente_id + ", producto ID: " + producto_id);
            }

            //Cerramos las instancias de la BD, Consulta SQL y resultados SQL
            conn.close();
            pstmt.close();
            rs.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }


    //Metodo para eliminar una venta en la BD
    public void eliminarVenta(int id){
        //Consulta SQL para eliminar una venta de la BD
        String sql = "DELETE FROM ventas WHERE venta_id = ?";
        try{

            //Establecemos conexión a la BD
            Connection conn = ConexionDB.conectar();

            //Preparamos la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Seteamos el ID a la consulta SQL
            pstmt.setInt(1, id);

            //Ejecutamos la consulta SQL
            pstmt.executeUpdate();

            //Cerramos las instancias de la BD
            conn.close();
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Ocurrió un error en la base de datos " + e.getMessage());
        }
    }
}
