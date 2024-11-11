package org.example.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CrearTablas {

    public static void crearTablasEnBD(){

        //Creacion de tablas
        String crearTablaCliente = "CREATE TABLE IF NOT EXISTS Clientes(cliente_id INT auto_increment PRIMARY KEY, dni VARCHAR(20) UNIQUE NOT NULL, nombre VARCHAR(50) NOT NULL, apellido VARCHAR(50) NOT NULL, direccion VARCHAR(100) );";
        String crearTablaProveedores = "CREATE TABLE IF NOT EXISTS Proveedores(proveedor_id  INT auto_increment  PRIMARY KEY, nombre VARCHAR(100) NOT NULL, cuit VARCHAR(20) UNIQUE NOT NULL, direccion VARCHAR(100));";
        String crearTablaProductos = "CREATE TABLE IF NOT EXISTS Productos(producto_id INT auto_increment PRIMARY KEY, nombre VARCHAR(100) NOT NULL, descripcion VARCHAR(100) NOT NULL, precio DECIMAL(10, 2) NOT NULL, stock INT NOT NULL, proveedor_id INT NOT NULL, FOREIGN KEY(proveedor_id) REFERENCES Proveedores(proveedor_id));";
        String crearTablaVentas = "CREATE TABLE IF NOT EXISTS Ventas(venta_id INT auto_increment PRIMARY KEY, cliente_id INT NOT NULL, producto_id INT NOT NULL, fecha DATE NOT NULL, monto_total DECIMAL(10, 2) NOT NULL, cantidad INT NOT NULL, FOREIGN KEY(cliente_id) REFERENCES Clientes(cliente_id), FOREIGN KEY(producto_id) REFERENCES Productos(producto_id));";

        try{
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmtCliente = conn.prepareStatement(crearTablaCliente);
            PreparedStatement pstmtProveedores = conn.prepareStatement(crearTablaProveedores);
            PreparedStatement pstmtProductos = conn.prepareStatement(crearTablaProductos);
            PreparedStatement pstmtVentas = conn.prepareStatement(crearTablaVentas);

            pstmtCliente.executeUpdate();
            pstmtProveedores.executeUpdate();
            pstmtProductos.executeUpdate();
            pstmtVentas.executeUpdate();

            pstmtCliente.close();
            pstmtProveedores.close();
            pstmtProductos.close();
            pstmtVentas.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}