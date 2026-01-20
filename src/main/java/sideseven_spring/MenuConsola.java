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


    private void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignorar excepciones
        }
    }


    // Para truncar el texto y que no desborde en la consola
    private String truncar(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }

    public void mostrarMenuPrincipal() {
        boolean salir = false;

        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN - SIDESEVEN SPRING BOOT      ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");

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
                        System.out.println("\n¡Hasta luego! Saliendo del programa...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\nOpción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Debe ingresar un número.");
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
                        System.out.println("\nOpción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
    }

    private void listarProductos() {
        System.out.println("\n═══════════════════════════ LISTA DE PRODUCTOS ═════════════════════════");
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
            scanner.nextLine(); // Limpiar buffer
            Producto producto = productoService.obtenerPorId(id);
            System.out.println("\nProducto encontrado:");
            System.out.println("  ID: " + producto.getId());
            System.out.println("  Nombre: " + producto.getNombre());
            System.out.println("  Categoría: " + producto.getCategoria());
            System.out.println("  Precio: €" + producto.getPrecio());
            System.out.println("  Stock: " + producto.getStock());
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
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
                System.out.println("\nProducto agregado exitosamente.");

                System.out.print("\n¿Desea agregar otro producto? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (InputMismatchException e) {
                System.out.println("\nError: Datos inválidos.");
                scanner.nextLine();
                pausar();
                continuar = false;
            } catch (Exception e) {
                System.out.println("\nError al agregar producto: " + e.getMessage());
                pausar();
                continuar = false;
            }
        }
    }

    private void actualizarProducto() {
        System.out.print("\nIngrese el ID del producto a actualizar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer

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
            System.out.println("\nProducto actualizado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("\nError: Datos inválidos.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
            pausar();
        }
    }

    private void eliminarProducto() {
        System.out.print("\nIngrese el ID del producto a eliminar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer
            productoService.eliminarProducto(id);
            System.out.println("\nProducto eliminado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
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
                        System.out.println("\nOpción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
    }

    private void listarClientes() {
        System.out.println("\n═══════════════════════════ LISTA DE PRODUCTOS ═════════════════════════");
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
            scanner.nextLine(); // Limpiar buffer
            Cliente cliente = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            System.out.println("\nCliente encontrado:");
            System.out.println("  ID: " + cliente.getId());
            System.out.println("  Nombre: " + cliente.getNombre());
            System.out.println("  Dirección: " + cliente.getDireccion());
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
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
                System.out.println("\nCliente agregado exitosamente.");

                System.out.print("\n¿Desea agregar otro cliente? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (Exception e) {
                System.out.println("\nError al agregar cliente: " + e.getMessage());
                pausar();
                continuar = false;
            }
        }
    }

    private void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer

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
            System.out.println("\nCliente actualizado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
            pausar();
        }
    }

    private void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer
            clienteService.eliminarCliente(id);
            System.out.println("\nCliente eliminado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
            pausar();
        }
    }

    private void verHistorialCompras() {
        System.out.print("\nIngrese el ID del cliente: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer
            Cliente cliente = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            System.out.println("\n═══ HISTORIAL DE COMPRAS - " + cliente.getNombre() + " ═══");
            List<Venta> historial = cliente.getHistorialCompras();
            if (historial.isEmpty()) {
                System.out.println("Este cliente no tiene compras registradas.");
            } else {
                System.out.printf("%-5s %-25s %-18s %-10s%n",
                    "ID", "PRODUCTO", "FECHA", "TOTAL");
                System.out.println("───────────────────────────────────────────────────────────────");

                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

                for (Venta venta : historial) {
                    String productoNombre = (venta.getProducto() != null)
                        ? truncar(venta.getProducto().getNombre(), 25)
                        : "N/A";
                    String fechaStr = (venta.getFecha() != null)
                        ? sdf.format(venta.getFecha())
                        : "N/A";

                    System.out.printf("%-5d %-25s %-18s €%-9.2f%n",
                            venta.getId(), productoNombre, fechaStr, venta.getTotal());
                    System.out.println(); // Línea en blanco entre ventas
                }
                System.out.println("═══════════════════════════════════════════════════════════════");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar buffer
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
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
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input.trim());

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
                        System.out.println("\nOpción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: Debe ingresar un número válido.");
            }
        }
    }

    private void registrarVenta() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n─── REGISTRAR NUEVA VENTA ───");
            try {
                // Mostrar clientes disponibles (sin pausa)
                System.out.println("\n═══════════════════════════ CLIENTES DISPONIBLES ═════════════════════════");
                List<Cliente> clientes = clienteService.obtenerTodos();
                if (clientes.isEmpty()) {
                    System.out.println("No hay clientes registrados.");
                    break;
                } else {
                    System.out.printf("%-5s %-28s %-40s%n", "ID", "NOMBRE", "DIRECCIÓN");
                    System.out.println("─────────────────────────────────────────────────────────────────────────────");
                    for (Cliente c : clientes) {
                        String nombre = truncar(c.getNombre(), 28);
                        String direccion = truncar(c.getDireccion(), 40);
                        System.out.printf("%-5d %-28s %-40s%n", c.getId(), nombre, direccion);
                    }
                    System.out.println("═════════════════════════════════════════════════════════════════════════════");
                }

                System.out.print("\nIngrese el ID del cliente: ");
                String inputCliente = scanner.nextLine();
                Long clienteId = Long.parseLong(inputCliente.trim());

                // Mostrar productos disponibles (sin pausa)
                System.out.println("\n═══════════════════════════ PRODUCTOS DISPONIBLES ═════════════════════════");
                List<Producto> productos = productoService.obtenerTodos();
                if (productos.isEmpty()) {
                    System.out.println("No hay productos registrados.");
                    break;
                } else {
                    System.out.printf("%-5s %-25s %-18s %-12s %-10s%n", "ID", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK");
                    System.out.println("─────────────────────────────────────────────────────────────────────────");
                    for (Producto p : productos) {
                        String nombre = truncar(p.getNombre(), 25);
                        String categoria = truncar(p.getCategoria(), 18);
                        System.out.printf("%-5d %-25s %-18s €%-11.2f %-10d%n",
                                p.getId(), nombre, categoria, p.getPrecio(), p.getStock());
                    }
                    System.out.println("═════════════════════════════════════════════════════════════════════════");
                }

                System.out.print("\nIngrese el ID del producto: ");
                String inputProducto = scanner.nextLine();
                Long productoId = Long.parseLong(inputProducto.trim());

                Venta venta = ventaService.registrarVenta(clienteId, productoId);

                // Mostrar resumen completo
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                String clienteNombre = truncar(venta.getCliente().getNombre(), 32);
                String productoNombre = truncar(venta.getProducto().getNombre(), 31);
                String totalStr = String.format("%.2f", venta.getTotal());
                String fechaStr = sdf.format(venta.getFecha());

                System.out.println("\n╔════════════════════════════════════════════════╗");
                System.out.println("║     VENTA REGISTRADA EXITOSAMENTE              ║");
                System.out.println("╠════════════════════════════════════════════════╣");
                System.out.printf("║  Cliente:  %-37s║%n", clienteNombre);
                System.out.printf("║  Producto: %-37s║%n", productoNombre);
                System.out.printf("║  Total:    €%-36s║%n", totalStr);
                System.out.printf("║  Fecha:    %-37s║%n", fechaStr);
                System.out.println("╚════════════════════════════════════════════════╝");

                System.out.print("\n¿Desea registrar otra venta? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");

            } catch (NumberFormatException e) {
                System.out.println("\nError: Debe ingresar un número válido.");
                pausar();
                continuar = false;
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                pausar();
                continuar = false;
            }
        }
    }

    private void listarVentas() {
        System.out.println("\n══════════════════════════════ LISTA DE PRODUCTOS ═══════════════════════════════════════");
        List<Venta> ventas = ventaService.listarVentas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            System.out.printf("%-5s %-25s %-25s %-18s %-10s%n",
                "ID", "CLIENTE", "PRODUCTO", "FECHA", "TOTAL");
            System.out.println("─────────────────────────────────────────────────────────────────────────────────────────");

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (Venta venta : ventas) {
                String clienteNombre = (venta.getCliente() != null)
                    ? truncar(venta.getCliente().getNombre(), 25)
                    : "N/A";
                String productoNombre = (venta.getProducto() != null)
                    ? truncar(venta.getProducto().getNombre(), 25)
                    : "N/A";
                String fechaStr = (venta.getFecha() != null)
                    ? sdf.format(venta.getFecha())
                    : "N/A";

                System.out.printf("%-5d %-25s %-25s %-18s €%-9.2f%n",
                        venta.getId(), clienteNombre, productoNombre, fechaStr, venta.getTotal());
                System.out.println(); // Línea en blanco entre ventas
            }
        }
        System.out.println("═════════════════════════════════════════════════════════════════════════════════════════");
        pausar();
    }
}

