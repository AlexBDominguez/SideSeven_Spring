package sideseven_spring;

import sideseven_spring.model.Cliente;
import sideseven_spring.model.Producto;
import sideseven_spring.model.Venta;
import sideseven_spring.service.ClienteService;
import sideseven_spring.service.ProductoService;
import sideseven_spring.service.VentaService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final Scanner scanner;

    public MenuConsola(ClienteService clienteService, ProductoService productoService, VentaService ventaService) {
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     */
    private void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Limpia el buffer del scanner
     */
    private void limpiarBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    /**
     * Trunca un texto a la longitud máxima especificada, agregando "..." si es necesario
     */
    private String truncar(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }

    public void mostrarMenuPrincipal() {
        boolean salir = false;

        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN - SIDESEVEN SPRING BOOT      ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        while (!salir) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         MENÚ PRINCIPAL              │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│ 1. Gestión de Productos             │");
            System.out.println("│ 2. Gestión de Clientes              │");
            System.out.println("│ 3. Gestión de Ventas                │");
            System.out.println("│ 0. Salir                            │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        menuProductos();
                        break;
                    case 2:
                        menuClientes();
                        break;
                    case 3:
                        menuVentas();
                        break;
                    case 0:
                        salir = true;
                        System.out.println("\n¡Hasta luego! Cerrando el sistema...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\n⚠ Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠ Error: Debe ingresar un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    // ==================== MENÚ DE PRODUCTOS ====================

    private void menuProductos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│      GESTIÓN DE PRODUCTOS           │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│ 1. Listar todos los productos       │");
            System.out.println("│ 2. Buscar producto por ID           │");
            System.out.println("│ 3. Agregar nuevo producto           │");
            System.out.println("│ 4. Actualizar producto              │");
            System.out.println("│ 5. Eliminar producto                │");
            System.out.println("│ 0. Volver al menú principal         │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        buscarProducto();
                        break;
                    case 3:
                        agregarProducto();
                        break;
                    case 4:
                        actualizarProducto();
                        break;
                    case 5:
                        eliminarProducto();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("\n⚠ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠ Error: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
    }

    private void listarProductos() {
        System.out.println("\n═══════════════════ LISTA DE PRODUCTOS ═══════════════════");
        List<Producto> productos = productoService.obtenerTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.printf("%-5s %-25s %-18s %-12s %-10s%n", "ID", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK");
            System.out.println("─────────────────────────────────────────────────────────────────────────");
            for (Producto p : productos) {
                String nombre = truncar(p.getNombre(), 25);
                String categoria = truncar(p.getCategoria(), 18);
                System.out.printf("%-5d %-25s %-18s €%-11.2f %-10d%n",
                        p.getId(), nombre, categoria, p.getPrecio(), p.getStock());
                System.out.println(); // Línea en blanco entre productos
            }
        }
        System.out.println("═════════════════════════════════════════════════════════════════════════");
        pausar();
    }

    private void buscarProducto() {
        System.out.print("\nIngrese el ID del producto: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            Producto producto = productoService.obtenerPorId(id);
            System.out.println("\n✓ Producto encontrado:");
            System.out.println("  ID: " + producto.getId());
            System.out.println("  Nombre: " + producto.getNombre());
            System.out.println("  Categoría: " + producto.getCategoria());
            System.out.println("  Precio: €" + producto.getPrecio());
            System.out.println("  Stock: " + producto.getStock());
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    private void agregarProducto() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n─── AGREGAR NUEVO PRODUCTO ───");
            try {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Categoría: ");
                String categoria = scanner.nextLine();

                System.out.print("Precio: ");
                double precio = scanner.nextDouble();
                scanner.nextLine(); // Limpiar buffer

                System.out.print("Stock: ");
                int stock = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                Producto producto = new Producto(nombre, categoria, precio, stock);
                productoService.crearProducto(producto);
                System.out.println("\n✓ Producto agregado exitosamente.");

                System.out.print("\n¿Desea agregar otro producto? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (InputMismatchException e) {
                System.out.println("\n✗ Error: Datos inválidos.");
                scanner.nextLine();
                pausar();
                continuar = false;
            } catch (Exception e) {
                System.out.println("\n✗ Error al agregar producto: " + e.getMessage());
                pausar();
                continuar = false;
            }
        }
    }

    private void actualizarProducto() {
        System.out.print("\nIngrese el ID del producto a actualizar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            Producto productoExistente = productoService.obtenerPorId(id);
            System.out.println("\nProducto actual: " + productoExistente.getNombre());

            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) productoExistente.setNombre(nombre);

            System.out.print("Nueva categoría (Enter para mantener): ");
            String categoria = scanner.nextLine();
            if (!categoria.isEmpty()) productoExistente.setCategoria(categoria);

            System.out.print("Nuevo precio (0 para mantener): ");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer
            if (precio > 0) productoExistente.setPrecio(precio);

            System.out.print("Nuevo stock (-1 para mantener): ");
            int stock = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            if (stock >= 0) productoExistente.setStock(stock);

            productoService.actualizarProducto(id, productoExistente);
            System.out.println("\n✓ Producto actualizado exitosamente.");
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    private void eliminarProducto() {
        System.out.print("\nIngrese el ID del producto a eliminar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            productoService.eliminarProducto(id);
            System.out.println("\n✓ Producto eliminado exitosamente.");
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    // ==================== MENÚ DE CLIENTES ====================

    private void menuClientes() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│       GESTIÓN DE CLIENTES           │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│ 1. Listar todos los clientes        │");
            System.out.println("│ 2. Buscar cliente por ID            │");
            System.out.println("│ 3. Agregar nuevo cliente            │");
            System.out.println("│ 4. Actualizar cliente               │");
            System.out.println("│ 5. Eliminar cliente                 │");
            System.out.println("│ 6. Ver historial de compras         │");
            System.out.println("│ 0. Volver al menú principal         │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        listarClientes();
                        break;
                    case 2:
                        buscarCliente();
                        break;
                    case 3:
                        agregarCliente();
                        break;
                    case 4:
                        actualizarCliente();
                        break;
                    case 5:
                        eliminarCliente();
                        break;
                    case 6:
                        verHistorialCompras();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("\n⚠ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠ Error: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
    }

    private void listarClientes() {
        System.out.println("\n═══════════════════ LISTA DE CLIENTES ═══════════════════");
        List<Cliente> clientes = clienteService.obtenerTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.printf("%-5s %-28s %-40s%n", "ID", "NOMBRE", "DIRECCIÓN");
            System.out.println("─────────────────────────────────────────────────────────────────────────────");
            for (Cliente c : clientes) {
                String nombre = truncar(c.getNombre(), 28);
                String direccion = truncar(c.getDireccion(), 40);
                System.out.printf("%-5d %-28s %-40s%n",
                        c.getId(), nombre, direccion);
                System.out.println(); // Línea en blanco entre clientes
            }
        }
        System.out.println("═════════════════════════════════════════════════════════════════════════════");
        pausar();
    }

    private void buscarCliente() {
        System.out.print("\nIngrese el ID del cliente: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            Cliente cliente = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            System.out.println("\n✓ Cliente encontrado:");
            System.out.println("  ID: " + cliente.getId());
            System.out.println("  Nombre: " + cliente.getNombre());
            System.out.println("  Dirección: " + cliente.getDireccion());
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    private void agregarCliente() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n─── AGREGAR NUEVO CLIENTE ───");
            try {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Dirección: ");
                String direccion = scanner.nextLine();

                Cliente cliente = new Cliente(nombre, direccion);
                clienteService.crearCliente(cliente);
                System.out.println("\n✓ Cliente agregado exitosamente.");

                System.out.print("\n¿Desea agregar otro cliente? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (Exception e) {
                System.out.println("\n✗ Error al agregar cliente: " + e.getMessage());
                pausar();
                continuar = false;
            }
        }
    }

    private void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            Cliente clienteExistente = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            System.out.println("\nCliente actual: " + clienteExistente.getNombre());

            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) clienteExistente.setNombre(nombre);

            System.out.print("Nueva dirección (Enter para mantener): ");
            String direccion = scanner.nextLine();
            if (!direccion.isEmpty()) clienteExistente.setDireccion(direccion);

            clienteService.actualizarCliente(id, clienteExistente);
            System.out.println("\n✓ Cliente actualizado exitosamente.");
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    private void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            clienteService.eliminarCliente(id);
            System.out.println("\n✓ Cliente eliminado exitosamente.");
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    private void verHistorialCompras() {
        System.out.print("\nIngrese el ID del cliente: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            Cliente cliente = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            System.out.println("\n═══ HISTORIAL DE COMPRAS - " + cliente.getNombre() + " ═══");
            List<Venta> historial = cliente.getHistorialCompras();
            if (historial.isEmpty()) {
                System.out.println("Este cliente no tiene compras registradas.");
            } else {
                for (Venta venta : historial) {
                    System.out.println(venta.toString());
                    System.out.println(); // Línea en blanco entre ventas
                }
            }
            pausar();
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
            pausar();
        }
    }

    // ==================== MENÚ DE VENTAS ====================

    private void menuVentas() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│        GESTIÓN DE VENTAS            │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│ 1. Registrar nueva venta            │");
            System.out.println("│ 2. Listar todas las ventas          │");
            System.out.println("│ 0. Volver al menú principal         │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarVenta();
                        break;
                    case 2:
                        listarVentas();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("\n⚠ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠ Error: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
    }

    private void registrarVenta() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n─── REGISTRAR NUEVA VENTA ───");
            try {
                // Mostrar clientes disponibles
                listarClientes();
                System.out.print("Ingrese el ID del cliente: ");
                Long clienteId = scanner.nextLong();
                scanner.nextLine(); // Limpiar buffer

                // Mostrar productos disponibles
                listarProductos();
                System.out.print("Ingrese el ID del producto: ");
                Long productoId = scanner.nextLong();
                scanner.nextLine(); // Limpiar buffer

                Venta venta = ventaService.registrarVenta(clienteId, productoId);
                System.out.println("\n✓ Venta registrada exitosamente!");
                System.out.println("  Total: €" + venta.getTotal());
                System.out.println("  Fecha: " + venta.getFecha());

                System.out.print("\n¿Desea registrar otra venta? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (Exception e) {
                System.out.println("\n✗ Error: " + e.getMessage());
                scanner.nextLine();
                pausar();
                continuar = false;
            }
        }
    }

    private void listarVentas() {
        System.out.println("\n═══════════════════ LISTA DE VENTAS ═══════════════════");
        List<Venta> ventas = ventaService.listarVentas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            for (Venta venta : ventas) {
                System.out.println(venta.toString());
                System.out.println(); // Línea en blanco entre ventas
            }
        }
        System.out.println("════════════════════════════════════════════════════════");
        pausar();
    }
}

