# Sistema de Gestión SideSeven - Spring Boot

## Descripción del Proyecto

Aplicación de gestión empresarial desarrollada con **Spring Boot** que permite administrar productos, clientes y ventas a través de un menú interactivo de consola. Este proyecto es una migración del sistema original que utilizaba JDBC y archivos CSV, ahora implementado completamente con Spring Boot y JPA/Hibernate.

## Características Principales

### ✅ Arquitectura Spring Boot
- **Spring Data JPA**: Persistencia automática con Hibernate
- **MySQL**: Base de datos relacional
- **REST Controllers**: Endpoints API REST para operaciones CRUD
- **Service Layer**: Lógica de negocio encapsulada
- **Repository Pattern**: Acceso a datos mediante JPA repositories

### ✅ Funcionalidades Implementadas

#### 1. Gestión de Productos
- ✓ Crear productos (nombre, categoría, precio, stock)
- ✓ Listar todos los productos
- ✓ Buscar producto por ID
- ✓ Actualizar información del producto
- ✓ Eliminar productos
- ✓ Control automático de inventario

#### 2. Gestión de Clientes
- ✓ Registrar clientes (nombre, dirección)
- ✓ Listar todos los clientes
- ✓ Buscar cliente por ID
- ✓ Actualizar datos del cliente
- ✓ Eliminar clientes
- ✓ Ver historial de compras por cliente

#### 3. Gestión de Ventas
- ✓ Registrar nuevas ventas
- ✓ Actualización automática de inventario
- ✓ Registro de fecha y hora de la venta
- ✓ Cálculo automático del total
- ✓ Listar todas las ventas
- ✓ Historial de compras por cliente

### ✅ Interfaz de Usuario
- **Menú de consola interactivo** con navegación intuitiva
- Validación de datos de entrada
- Mensajes de error informativos
- Formato visual mejorado con caracteres Unicode

## Estructura del Proyecto

```
src/main/java/sideseven_spring/
├── MainApplication.java          # Clase principal con CommandLineRunner
├── MenuConsola.java              # Sistema de menús interactivo
├── controller/
│   ├── ClienteController.java    # REST API para clientes
│   ├── ProductoController.java   # REST API para productos
│   └── VentaController.java      # REST API para ventas
├── model/
│   ├── Cliente.java              # Entidad JPA Cliente
│   ├── Producto.java             # Entidad JPA Producto
│   └── Venta.java                # Entidad JPA Venta
├── repository/
│   ├── ClienteRepository.java    # Repositorio JPA Cliente
│   ├── ProductoRepository.java   # Repositorio JPA Producto
│   └── VentaRepository.java      # Repositorio JPA Venta
└── service/
    ├── ClienteService.java       # Lógica de negocio clientes
    ├── ProductoService.java      # Lógica de negocio productos
    └── VentaService.java         # Lógica de negocio ventas
```

## Requisitos Técnicos

- **Java 17** o superior
- **Maven** (incluido Maven Wrapper)
- **MySQL 8.0** o superior
- **Spring Boot 3.2.1**

## Configuración de la Base de Datos

1. **Crear la base de datos MySQL** (opcional, se crea automáticamente):
   ```sql
   CREATE DATABASE sideseven_db;
   ```

2. **Configurar credenciales** en `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sideseven_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. **Hibernate creará las tablas automáticamente** al iniciar la aplicación:
   - `clientes` (id, nombre, direccion)
   - `productos` (id, nombre, categoria, precio, stock)
   - `ventas` (id, cliente_id, producto_id, fecha, total)

## Cómo Ejecutar el Proyecto

### Opción 1: Usando Maven Wrapper (Recomendado)
```bash
cd SideSeven_Spring
.\mvnw.cmd spring-boot:run
```

### Opción 2: Usando Maven instalado
```bash
cd SideSeven_Spring
mvn spring-boot:run
```

### Opción 3: Ejecutar el JAR compilado
```bash
.\mvnw.cmd clean package -DskipTests
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Uso del Sistema

### Menú Principal
Al iniciar la aplicación, verás el menú principal:

```
╔═══════════════════════════════════════════════════╗
║   SISTEMA DE GESTIÓN - SIDESEVEN SPRING BOOT      ║
╚═══════════════════════════════════════════════════╝

┌─────────────────────────────────────┐
│         MENÚ PRINCIPAL              │
├─────────────────────────────────────┤
│ 1. Gestión de Productos             │
│ 2. Gestión de Clientes              │
│ 3. Gestión de Ventas                │
│ 0. Salir                            │
└─────────────────────────────────────┘
```

### Ejemplos de Uso

#### Agregar un Producto
1. Menú Principal → 1 (Gestión de Productos)
2. Menú Productos → 3 (Agregar nuevo producto)
3. Ingresa: Nombre, Categoría, Precio, Stock
4. Confirmación: ✓ Producto agregado exitosamente

#### Registrar una Venta
1. Menú Principal → 3 (Gestión de Ventas)
2. Menú Ventas → 1 (Registrar nueva venta)
3. Selecciona ID de cliente de la lista
4. Selecciona ID de producto de la lista
5. El sistema:
   - Reduce automáticamente el stock
   - Calcula el total
   - Registra fecha y hora
   - Actualiza historial del cliente

## API REST Disponible

Además del menú de consola, la aplicación expone endpoints REST:

### Productos
- `GET /productos` - Listar todos
- `GET /productos/{id}` - Buscar por ID
- `POST /productos` - Crear nuevo
- `PUT /productos/{id}` - Actualizar
- `DELETE /productos/{id}` - Eliminar

### Clientes
- `GET /clientes` - Listar todos
- `GET /clientes/{id}` - Buscar por ID
- `POST /clientes` - Crear nuevo
- `PUT /clientes/{id}` - Actualizar
- `DELETE /clientes/{id}` - Eliminar

### Ventas
- `GET /ventas` - Listar todas
- `POST /ventas` - Registrar nueva venta

**Puerto del servidor**: `8082`

Ejemplo usando curl:
```bash
# Listar productos
curl http://localhost:8082/productos

# Crear un producto
curl -X POST http://localhost:8082/productos -H "Content-Type: application/json" -d '{"nombre":"Laptop","categoria":"Electrónica","precio":999.99,"stock":10}'
```

## Comparación con el Enunciado Original

### ✅ Fase 1: Almacenamiento (Original: CSV)
**Implementado en Spring Boot**: 
- Persistencia automática con JPA/Hibernate
- Base de datos MySQL (mucho más robusto que archivos CSV)
- Transacciones ACID garantizadas

### ✅ Fase 2: Conexión a Base de Datos (Original: JDBC)
**Implementado en Spring Boot**:
- Spring Data JPA (abstracción sobre JDBC)
- Repositories automáticos
- No se requiere código SQL manual
- ORM Hibernate para mapeo objeto-relacional

### ✅ Requisitos Adicionales Cumplidos

#### Manejo de Errores ✓
- Bloques try-catch en toda la aplicación
- Validación de datos de entrada
- Mensajes de error informativos
- Manejo de excepciones de base de datos

#### Estructura de Clases ✓
- Clases de entidad: `Cliente`, `Producto`, `Venta`
- Capa de servicio (Service Layer): `ClienteService`, `ProductoService`, `VentaService`
- Capa de repositorio (DAO Pattern): Repositories de Spring Data JPA
- Separación clara de responsabilidades (MVC)

#### Gestión de Transacciones ✓
- Transacciones automáticas con `@Transactional` (implícito en Spring Data JPA)
- Integridad referencial con foreign keys
- Rollback automático en caso de error

## Ventajas de Spring Boot sobre el Enfoque Original

1. **Menos código boilerplate**: No se necesita código JDBC manual
2. **Configuración automática**: Spring Boot configura todo automáticamente
3. **Seguridad**: Prevención de SQL injection con JPA
4. **Productividad**: Desarrollo más rápido
5. **Mantenibilidad**: Código más limpio y organizado
6. **Escalabilidad**: Fácil migración a arquitecturas distribuidas
7. **REST API**: Endpoints web listos para usar
8. **Testing**: Mejor soporte para pruebas unitarias

## Tecnologías Utilizadas

- **Spring Boot 3.2.1**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Hibernate**: ORM (Object-Relational Mapping)
- **MySQL Connector**: Driver JDBC para MySQL
- **Maven**: Gestión de dependencias
- **Java 17**: Lenguaje de programación

## Autor

Alexandre Barbe
Proyecto: SideSeven Spring Boot
Fecha: Enero 2026

---

## Notas para el Profesor

Este proyecto cumple con **todos los requisitos del enunciado original** pero implementado con **Spring Boot**:

✅ Gestión completa de Productos, Clientes y Ventas
✅ Persistencia en base de datos (MySQL en lugar de JDBC manual)
✅ Menú de consola interactivo
✅ Manejo robusto de errores
✅ Arquitectura orientada a objetos con Service Layer y Repository Pattern
✅ Transacciones automáticas
✅ Bonus: API REST completa

La principal diferencia es que en lugar de usar JDBC manual y archivos CSV, se utiliza **Spring Boot con JPA/Hibernate**, que es la forma moderna y profesional de desarrollar aplicaciones empresariales en Java.

