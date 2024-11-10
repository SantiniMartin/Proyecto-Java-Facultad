package org.example.menus;
import org.example.dao.*;
import org.example.models.Cliente;
import org.example.models.Producto;
import org.example.models.Proveedor;
import org.example.models.Venta;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrincipal {

    public static void mostrarMenuPrincipal(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Clientes");
            System.out.println("2. Proveedores");
            System.out.println("3. Productos");
            System.out.println("4. Ventas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    clienteMenu();
                    break;
                case 2:
                    proveedorMenu();
                    break;
                case 3:
                    productoMenu();
                    break;
                case 4:
                    ventaMenu();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 5);
    }

    // Menú para Clientes
    private static void clienteMenu(){
        Scanner scanner = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        int clienteID;
        int option;
        do {
            System.out.println("\n--- Menú Clientes ---");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Ver Clientes");
            System.out.println("3. Buscar Cliente Por ID");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una acción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    Cliente cliente = new Cliente();
                    System.out.println("Ingrese el DNI del cliente: ");
                    cliente.setDni(scanner.nextLine());
                    System.out.println("Ingrese el nombre del cliente: ");
                    cliente.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el apellido del cliente: ");
                    cliente.setApellido(scanner.nextLine());
                    System.out.println("Ingrese la direccion del cliente: ");
                    cliente.setDireccion(scanner.nextLine());

                    clienteDAO.agregarCliente(cliente);
                    break;
                case 2:
                    clienteDAO.listarClientes();
                    break;
                case 3:
                    System.out.println("Ingrese un ID a buscar: ");
                    clienteID = scanner.nextInt();
                    clienteDAO.buscarCliente(clienteID);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del cliente a actualizar: ");
                    int actualizarID = scanner.nextInt();
                    scanner.nextLine();
                    Cliente actualizarCliente = new Cliente();
                    System.out.println("Ingrese el DNI del cliente: ");
                    actualizarCliente.setDni(scanner.nextLine());
                    System.out.println("Ingrese el nombre del cliente: ");
                    actualizarCliente.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el apellido del cliente: ");
                    actualizarCliente.setApellido(scanner.nextLine());
                    System.out.println("Ingrese la direccion del cliente: ");
                    actualizarCliente.setDireccion(scanner.nextLine());
                    clienteDAO.actualizarCliente(actualizarID, actualizarCliente);
                    break;
                case 5:
                    System.out.println("Ingrese el ID del cliente a eliminar:");
                    clienteID = scanner.nextInt();
                    clienteDAO.eliminarCliente(clienteID);
                    break;
                case 6: System.out.println("Volviendo al Menú Principal..."); break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (option != 6);
    }

    // Menú para Proveedores
    private static void proveedorMenu() {
        Scanner scanner = new Scanner(System.in);
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        int proveedorID;
        int option;
        do {
            System.out.println("\n--- Menú Proveedores ---");
            System.out.println("1. Agregar Proveedor");
            System.out.println("2. Ver Proveedores");
            System.out.println("3. Buscar Proveedor Por ID");
            System.out.println("4. Actualizar Proveedor");
            System.out.println("5. Eliminar Proveedor");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una acción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    Proveedor proveedor = new Proveedor();
                    System.out.println("Ingrese el nombre del proveedor: ");
                    proveedor.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el CUIT del proveedor: ");
                    proveedor.setCuit(scanner.nextLine());
                    System.out.println("Ingrese la dirección del proveedor: ");
                    proveedor.setDireccion(scanner.nextLine());
                    proveedorDAO.agregarProveedor(proveedor);
                    break;
                case 2:
                    proveedorDAO.listarProveedores();
                    break;
                case 3:
                    System.out.println("Ingrese un ID a buscar: ");
                    proveedorID = scanner.nextInt();
                    proveedorDAO.buscarProveedor(proveedorID);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del proveedor a actualizar: ");
                    int actualizarID = scanner.nextInt();
                    scanner.nextLine();
                    Proveedor actualizarProveedor = new Proveedor();
                    System.out.println("Ingrese el nombre del proveedor: ");
                    actualizarProveedor.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el CUIT del proveedor: ");
                    actualizarProveedor.setCuit(scanner.nextLine());
                    System.out.println("Ingrese la direccion del cliente: ");
                    actualizarProveedor.setDireccion(scanner.nextLine());
                    proveedorDAO.actualizarProveedor(actualizarID, actualizarProveedor);
                    break;
                case 5:
                    System.out.println("Ingrese un ID a buscar: ");
                    proveedorID = scanner.nextInt();
                    proveedorDAO.eliminarProveedor(proveedorID);
                    break;
                case 6:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (option != 6);
    }

    // Menú para Productos
    private static void productoMenu(){
        Scanner scanner = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();
        int productoID;

        int option;
        do {
            System.out.println("\n--- Menú Productos ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Ver Productos");
            System.out.println("3. Buscar Producto Por ID");
            System.out.println("4. Actualizar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una acción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    Producto producto = new Producto();
                    System.out.println("Ingrese el nombre del producto: ");
                    producto.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el precio del producto: ");
                    producto.setPrecio(scanner.nextDouble());
                    scanner.nextLine();
                    System.out.println("Ingrese el ID del proveedor: ");
                    int proveedor_id = scanner.nextInt();
                    scanner.nextLine();
                    productoDAO.agregarProducto(proveedor_id, producto);
                    break;
                case 2:
                    productoDAO.listarProductos();
                    break;
                case 3:
                    System.out.println("Ingrese el ID del producto a buscar: ");
                    productoID = scanner.nextInt();
                    productoDAO.buscarProducto(productoID);
                    break;
                case 4:
                    Producto actualizarProducto = new Producto();
                    System.out.println("Ingrese el ID del producto a actualizar: ");
                    productoID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el nombre del producto: ");
                    actualizarProducto.setNombre(scanner.nextLine());
                    System.out.println("Ingrese el precio del producto: ");
                    actualizarProducto.setPrecio(scanner.nextDouble());
                    System.out.println("Ingrese el ID del proveedor: ");
                    int proveedorID = scanner.nextInt();
                    productoDAO.actualizarProducto(productoID, actualizarProducto, proveedorID);
                    break;
                case 5:
                    System.out.println("Ingrese el ID del producto a eliminar: ");
                    productoID = scanner.nextInt();
                    productoDAO.eliminarProducto(productoID);
                    break;
                case 6: System.out.println("Volviendo al Menú Principal..."); break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (option != 6);
    }

    // Menú para Ventas
    private static void ventaMenu(){
        VentaDAO ventaDAO = new VentaDAO();
        Scanner scanner = new Scanner(System.in);
        int ventaID;
        int option;
        do {
            System.out.println("\n--- Menú Ventas ---");
            System.out.println("1. Agregar Venta");
            System.out.println("2. Ver Ventas");
            System.out.println("3. Buscar Venta Por ID");
            System.out.println("4. Actualizar Venta");
            System.out.println("5. Eliminar Venta");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una acción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    Venta nuevaVenta = new Venta();
                    System.out.println("Ingrese el ID del cliente: ");
                    int clienteID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el ID del producto: ");
                    int productoID = scanner.nextInt();
                    scanner.nextLine();
//                    System.out.println("Ingrese la fecha de la venta: ");
//                    System.out.println("Ingrese el año: ");
//                    int anio = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("Ingrese el mes: ");
//                    int mes = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("Ingrese el día: ");
//                    int dia = scanner.nextInt();
//                    nuevaVenta.setFechaDeVenta(LocalDate.of(anio, mes, dia));
                    System.out.println("Ingrese el monto: ");
                    nuevaVenta.setMontoTotal(scanner.nextDouble());
                    scanner.nextLine();
                    ventaDAO.agregarVenta(clienteID, productoID, nuevaVenta);
                    break;
                case 2:
                    ventaDAO.listarVentas();
                    break;
                case 3:
                    System.out.println("Ingrese el ID de la venta a buscar: ");
                    ventaID = scanner.nextInt();
                    ventaDAO.buscarVentaPorId(ventaID);
                    break;
                case 4:
                    Venta modificarVenta = new Venta();
                    System.out.println("Ingrese el ID de la venta a modificar: ");
                    ventaID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el ID del cliente: ");
                    int idCliente = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el ID del producto");
                    int idProducto = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el monto total de la venta: ");
                    modificarVenta.setMontoTotal(scanner.nextDouble());
                    scanner.nextLine();
                    System.out.println("Ingrese la fecha de la venta");
                    System.out.println("Ingrese el año: ");
                    int anioMod = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el mes: ");
                    int mesMod = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el día: ");
                    int diaMod = scanner.nextInt();
                    modificarVenta.setFechaDeVenta(LocalDate.of(anioMod, mesMod, diaMod));
                    ventaDAO.actualizarVenta(ventaID, idCliente, idProducto, modificarVenta);
                    break;
                case 5:
                    System.out.println("Ingrese el ID de la venta a eliminar: ");
                    ventaID = scanner.nextInt();
                    ventaDAO.eliminarVenta(ventaID);
                    break;
                case 6:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (option != 6);
        scanner.close();
    }
}