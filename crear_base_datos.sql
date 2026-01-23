-- ============================================================================
-- Script de Creación de Base de Datos - SideSeven
-- ============================================================================
-- Este script crea la base de datos y todas las tablas necesarias desde cero
-- Ejecutar como usuario root o con permisos suficientes
-- ============================================================================

-- 1. Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS sideseven_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Usar la base de datos
USE sideseven_db;

-- 3. Eliminar tablas existentes (en orden correcto por dependencias)
DROP TABLE IF EXISTS venta_detalle;
DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS clientes;

-- ============================================================================
-- TABLA: clientes
-- ============================================================================
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    INDEX idx_activo (activo),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLA: productos
-- ============================================================================
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(255),
    precio DOUBLE NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    INDEX idx_activo (activo),
    INDEX idx_categoria (categoria),
    INDEX idx_nombre (nombre),
    CHECK (precio >= 0),
    CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLA: ventas
-- ============================================================================
CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL DEFAULT 0,
    nombre_cliente VARCHAR(255),
    id_producto BIGINT NULL,  -- Campo legacy, nullable para compatibilidad
    INDEX idx_cliente (id_cliente),
    INDEX idx_fecha (fecha),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    CHECK (total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- TABLA: venta_detalle
-- ============================================================================
CREATE TABLE venta_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    nombre_producto VARCHAR(255),
    INDEX idx_venta (id_venta),
    INDEX idx_producto (id_producto),
    FOREIGN KEY (id_venta) REFERENCES ventas(id) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id) ON DELETE RESTRICT,
    CHECK (cantidad > 0),
    CHECK (precio_unitario >= 0),
    CHECK (subtotal >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- DATOS DE PRUEBA (Opcional)
-- ============================================================================

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, direccion, activo) VALUES
('Juan Pérez', 'Calle Mayor 123, Madrid', TRUE),
('María García', 'Avenida Principal 45, Barcelona', TRUE),
('Carlos López', 'Plaza España 7, Valencia', TRUE),
('Ana Martínez', 'Calle Luna 89, Sevilla', TRUE);

-- Insertar productos de prueba
INSERT INTO productos (nombre, categoria, precio, stock, activo) VALUES
('One Piece Vol. 1', 'Manga', 8.99, 50, TRUE),
('Batman: The Dark Knight', 'Comic', 15.99, 30, TRUE),
('Dungeons & Dragons - Manual del Jugador', 'Rol', 49.99, 20, TRUE),
('Figura Naruto Uzumaki', 'Figura', 29.99, 15, TRUE),
('Attack on Titan Vol. 1', 'Manga', 9.99, 40, TRUE),
('Figura Cloud Strife FFVII', 'Figura', 50.99, 5, TRUE),
('Naruto Vol. 1', 'Manga', 8.50, 35, TRUE),
('Dragon Ball Vol. 1', 'Manga', 7.99, 60, TRUE);

-- Insertar ventas de prueba con múltiples productos
-- Venta 1: Juan Pérez compra 2 mangas
INSERT INTO ventas (id_cliente, fecha, total, nombre_cliente) VALUES
(1, NOW(), 18.98, 'Juan Pérez');

SET @venta1_id = LAST_INSERT_ID();

INSERT INTO venta_detalle (id_venta, id_producto, cantidad, precio_unitario, subtotal, nombre_producto) VALUES
(@venta1_id, 1, 2, 8.99, 17.98, 'One Piece Vol. 1'),
(@venta1_id, 5, 1, 9.99, 9.99, 'Attack on Titan Vol. 1');

UPDATE ventas SET total = 27.97 WHERE id = @venta1_id;

-- Venta 2: María García compra productos variados
INSERT INTO ventas (id_cliente, fecha, total, nombre_cliente) VALUES
(2, NOW(), 0, 'María García');

SET @venta2_id = LAST_INSERT_ID();

INSERT INTO venta_detalle (id_venta, id_producto, cantidad, precio_unitario, subtotal, nombre_producto) VALUES
(@venta2_id, 2, 1, 15.99, 15.99, 'Batman: The Dark Knight'),
(@venta2_id, 7, 3, 8.50, 25.50, 'Naruto Vol. 1');

UPDATE ventas SET total = 41.49 WHERE id = @venta2_id;

-- Venta 3: Carlos López compra una figura
INSERT INTO ventas (id_cliente, fecha, total, nombre_cliente) VALUES
(3, NOW(), 29.99, 'Carlos López');

SET @venta3_id = LAST_INSERT_ID();

INSERT INTO venta_detalle (id_venta, id_producto, cantidad, precio_unitario, subtotal, nombre_producto) VALUES
(@venta3_id, 4, 1, 29.99, 29.99, 'Figura Naruto Uzumaki');

-- Actualizar stock de productos después de las ventas de prueba
UPDATE productos SET stock = stock - 2 WHERE id = 1;  -- One Piece
UPDATE productos SET stock = stock - 1 WHERE id = 2;  -- Batman
UPDATE productos SET stock = stock - 1 WHERE id = 4;  -- Figura Naruto
UPDATE productos SET stock = stock - 1 WHERE id = 5;  -- Attack on Titan
UPDATE productos SET stock = stock - 3 WHERE id = 7;  -- Naruto

-- ============================================================================
-- VERIFICACIONES
-- ============================================================================

-- Verificar que las tablas se crearon correctamente
SELECT 'Tablas creadas correctamente:' as '';
SHOW TABLES;

-- Verificar datos insertados
SELECT 'Total de clientes:' as '', COUNT(*) as total FROM clientes;
SELECT 'Total de productos:' as '', COUNT(*) as total FROM productos;
SELECT 'Total de ventas:' as '', COUNT(*) as total FROM ventas;
SELECT 'Total de detalles de venta:' as '', COUNT(*) as total FROM venta_detalle;

-- Verificar integridad de ventas
SELECT
    'Verificación de totales:' as '',
    v.id,
    v.total as total_venta,
    SUM(vd.subtotal) as suma_detalles,
    CASE
        WHEN ABS(v.total - SUM(vd.subtotal)) < 0.01 THEN '✓ OK'
        ELSE '✗ ERROR'
    END as estado
FROM ventas v
LEFT JOIN venta_detalle vd ON v.id = vd.id_venta
GROUP BY v.id, v.total;

-- ============================================================================
-- INFORMACIÓN FINAL
-- ============================================================================
SELECT '============================================================================' as '';
SELECT 'Base de datos sideseven_db creada exitosamente' as '';
SELECT '============================================================================' as '';
SELECT 'Tablas creadas:' as '';
SELECT '  - clientes (con índices en activo y nombre)' as '';
SELECT '  - productos (con índices en activo, categoría y nombre)' as '';
SELECT '  - ventas (con relación a clientes)' as '';
SELECT '  - venta_detalle (con relaciones a ventas y productos)' as '';
SELECT '' as '';
SELECT 'Datos de prueba insertados:' as '';
SELECT '  - 4 clientes' as '';
SELECT '  - 8 productos' as '';
SELECT '  - 3 ventas con múltiples productos' as '';
SELECT '' as '';
SELECT 'Características:' as '';
SELECT '  - Soporte para múltiples productos por venta' as '';
SELECT '  - Borrado lógico con campo "activo"' as '';
SELECT '  - Preservación de historial (nombres guardados)' as '';
SELECT '  - Integridad referencial con foreign keys' as '';
SELECT '  - Índices para mejor rendimiento' as '';
SELECT '  - Constraints para validación de datos' as '';
SELECT '============================================================================' as '';

