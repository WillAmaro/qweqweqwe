-- Crear la base de datos y seleccionarla
CREATE DATABASE IF NOT EXISTS minimarket;

USE minimarket;

-- Crear Tablas con IDs más descriptivos

-- Usuarios
CREATE TABLE usuario (
                         usuarioId INT AUTO_INCREMENT NOT NULL,
                         username VARCHAR(100) NOT NULL,
                         password VARCHAR(100) NULL,
                         email VARCHAR(100) NOT NULL,
                         role VARCHAR(100) NOT NULL,
                         CONSTRAINT usuario_pk PRIMARY KEY (usuarioId),
                         CONSTRAINT usuario_unique UNIQUE KEY (email),
                         CONSTRAINT usuario_unique_1 UNIQUE KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Distritos
CREATE TABLE distrito (
                          distritoId INT AUTO_INCREMENT NOT NULL,
                          nombre VARCHAR(100) NOT NULL,
                          CONSTRAINT distrito_pk PRIMARY KEY (distritoId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Clientes
CREATE TABLE cliente (
                         clienteId INT AUTO_INCREMENT NOT NULL,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL,
                         username VARCHAR(100) NOT NULL,
                         password VARCHAR(100) NOT NULL,
                         direccion VARCHAR(100) DEFAULT NULL,
                         distritoId INT DEFAULT NULL,
                         PRIMARY KEY (clienteId),
                         UNIQUE KEY cliente_unique (email),
                         UNIQUE KEY cliente_unique_1 (username, email),
                         KEY cliente_distrito_fk (distritoId),
                         CONSTRAINT cliente_distrito_fk FOREIGN KEY (distritoId) REFERENCES distrito (distritoId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Categorías
CREATE TABLE categoria (
                           categoriaId INT AUTO_INCREMENT NOT NULL,
                           categoriaNombre VARCHAR(100) NOT NULL,
                           CONSTRAINT categoria_pk PRIMARY KEY (categoriaId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Producto
CREATE TABLE producto (
                          id INT AUTO_INCREMENT NOT NULL,
                          nombre VARCHAR(100) NOT NULL,
                          precioU DECIMAL(10, 2) NOT NULL,
                          uniMedida VARCHAR(50) NOT NULL,
                          stock INT NOT NULL,
                          marca VARCHAR(100) NOT NULL,
                          categoriaId INT NOT NULL,
                          CONSTRAINT producto_pk PRIMARY KEY (id),
                          CONSTRAINT producto_categoria_fk FOREIGN KEY (categoriaId) REFERENCES categoria (categoriaId)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insertar Distritos
INSERT INTO distrito (distritoId, nombre) VALUES
                                              (1, 'San Miguel'),
                                              (2, 'San Borja'),
                                              (3, 'Comas'),
                                              (4, 'Miraflores'),
                                              (5, 'Magdalena'),
                                              (6, 'Jesús María'),
                                              (7, 'La Victoria');

-- Insertar Usuarios
INSERT INTO usuario (usuarioId, username, password, email, role) VALUES
                                                                     (1, 'admin1', 'contrasegura95473', 'eladmin@gmail.com', 'ADMIN'),
                                                                     (2, 'mcabrejos', '938fjsidfa', 'monicac@gmail.com', 'OPERATOR'),
                                                                     (3, 'sysadmin', '0d8g0as9d0f', 'sergioe@gmail.com', 'ADMIN'),
                                                                     (4, 'jdoe', 'password123', 'johndoe@gmail.com', 'OPERATOR');

-- Insertar Clientes
INSERT INTO cliente (clienteId, nombre, email, username, password, direccion, distritoId) VALUES
                                                                                              (1, 'Juan Pérez', 'juan.perez@gmail.com', 'jperez', 'contrasena', 'Av. Del Ejemplo 674', 2),
                                                                                              (2, 'María Quispe', 'maria.quispe@gmail.com', 'marial', '9s7g8v', 'Calle Carrion 456', 3),
                                                                                              (3, 'Carlos Cano', 'carlos.cano@gmail.com', 'carcano', '0d8g90', 'Calle Santa Maria 789', 6),
                                                                                              (4, 'Lucía Fernández', 'lucia.fernandez@gmail.com', 'lfernan', '0f9f0df', 'Av Garzon 123', 6),
                                                                                              (5, 'Pedro Sánchez', 'pedro.sanchez@gmail.com', 'pedros', 'b97d9v', 'Av San Borja Norte 654', 2),
                                                                                              (6, 'Ana Martínez', 'ana.martinez@gmail.com', 'anam', 'sid8g89dgs', 'Av La Mar 987', 1);

-- Insertar Categorías
INSERT INTO categoria (categoriaId, categoriaNombre) VALUES
                                                         (1, 'Enlatado'),
                                                         (2, 'Lácteo'),
                                                         (3, 'Cereales'),
                                                         (4, 'Frutas'),
                                                         (5, 'Verduras'),
                                                         (6, 'Carne');

-- Insertar Productos
INSERT INTO producto (nombre, precioU, uniMedida, stock, marca, categoriaId) VALUES
                                                                                 ('Atún en aceite', 2.50, 'Lata', 120, 'San Jorge', 1),
                                                                                 ('Duraznos en almíbar', 3.20, 'Lata', 80, 'D\'Onofrio', 1),
('Sardinas', 1.80, 'Lata', 150, 'Florida', 1),
('Leche Entera', 1.50, 'Litro', 100, 'Laive', 2),
('Yogur Natural', 2.00, 'Botella', 90, 'Gloria', 2),
('Queso Fresco', 5.50, 'Kg', 50, 'Andino', 2),
('Avena en hojuelas', 2.00, 'Kg', 120, 'Quaker', 3),
('Arroz Integral', 3.20, 'Kg', 50, 'Costeño', 3),
('Maíz para cancha', 2.50, 'Kg', 70, 'Local', 3),
('Manzana Roja', 0.80, 'Kg', 200, 'Local', 4),
('Plátano', 0.60, 'Kg', 150, 'Local', 4),
('Uvas Verdes', 3.50, 'Kg', 100, 'Local', 4),
('Zanahoria', 1.20, 'Kg', 180, 'Local', 5),
('Papa Amarilla', 1.80, 'Kg', 150, 'Local', 5),
('Brócoli', 2.50, 'Kg', 70, 'Local', 5),
('Carne de Res', 8.50, 'Kg', 70, 'Local', 6),
('Pollo Entero', 6.00, 'Kg', 90, 'San Fernando', 6),
('Chuletas de Cerdo', 9.00, 'Kg', 60, 'Local', 6);

-- Actualizar roles de usuario
UPDATE usuario
SET role = 'ADMIN'
WHERE usuarioId IN (1, 3);

UPDATE usuario
SET role = 'OPERATOR'
WHERE usuarioId = 2;

-- Insertar para test

INSERT INTO usuario (usuarioId, username, password, email,role) VALUES
(4, 'admin', 'admin', 'admin@gmail.com','ADMIN'),
(5, 'operator', 'operator', 'op@gmail.com','OPERATOR'),
(6, 'client', 'client', 'client@gmail.com','CLIENT');

-- 

