-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS NanoBank;
USE NanoBank;

-- TABLA USUARIO
CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    saldo FLOAT(20,2) DEFAULT 1000.00,
    dni VARCHAR(10) NOT NULL,
    contraseña VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono INT (9) NOT NULL, 
    email VARCHAR(50) NOT NULL,
    iban VARCHAR(50) NOT NULL,
    fecha_liquidacion DATE DEFAULT NULL
);

-- TABLA TRANSACCIONES
CREATE TABLE IF NOT EXISTS Transferencia (
    id_transaciones INT PRIMARY KEY AUTO_INCREMENT,
    cuentaOrigen VARCHAR(50) NOT NULL,
    cuentaDestino VARCHAR(100) NOT NULL,
    nombreDestino VARCHAR(50) NOT NULL,
    cantidad DECIMAL(20,2) NOT NULL,
    concepto VARCHAR(250) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- TABLA BIZUM
CREATE TABLE IF NOT EXISTS Bizum (
    id_bizum INT PRIMARY KEY AUTO_INCREMENT,
    cuentaOrigen VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL, -- Cambiado a VARCHAR para manejar códigos de país
    nombreDestino VARCHAR(50) NOT NULL,
    cantidad DECIMAL(20,2) NOT NULL,
    concepto VARCHAR(250) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- TABLA FORMULARIOCARTERAS
CREATE TABLE IF NOT EXISTS FormularioCarteras (
    id_cartera INT PRIMARY KEY AUTO_INCREMENT,
    porcentajeRF DECIMAL(5,2) NOT NULL,
    porcentajeRV DECIMAL(5,2) NOT NULL,
    cantidadInvertida DECIMAL(20,2) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Insertamos datos de usuarios si no existen
INSERT IGNORE INTO Usuario (dni, contraseña, nombre, telefono, email, iban) VALUES 
    ('30269762Z', 'admin123', 'Giovanni', '644126909', 'giovanni.baralv@gmail.com', 'ES62 2100 8434 5702 0011 1410'),
    ('28724060G', 'admin123', 'Francisco', '696787146', 'joframan@hotmail.es', 'ES51 2100 8434 5821 0011 9914'),
    ('28591788M', 'admin123', 'Mari Carmen', '669997119', 'LUNERA1000@HOTMAIL.COM', 'ES11 2100 8434 5802 0011 2182'),
    ('29738294H', 'admin123', 'Jose', '601234567', 'jose@gmail.com', 'ES24 2100 8434 5810 0011 1234'),
    ('30367482J', 'admin123', 'Ana', '602345678', 'ana@gmail.com', 'ES33 2100 8434 5820 0011 2345'),
    ('31658924K', 'admin123', 'Luis', '603456789', 'luis@gmail.com', 'ES42 2100 8434 5830 0011 3456'),
    ('32456782L', 'admin123', 'Laura', '604567890', 'laura@gmail.com', 'ES51 2100 8434 5840 0011 4567'),
    ('33248967M', 'admin123', 'Carlos', '605678901', 'carlos@gmail.com', 'ES62 2100 8434 5850 0011 5678'),
    ('34658924N', 'admin123', 'Elena', '606789012', 'elena@gmail.com', 'ES73 2100 8434 5860 0011 6789'),
    ('35246789O', 'admin123', 'Marta', '607890123', 'marta@gmail.com', 'ES84 2100 8434 5870 0011 7890')
;

-- SELECT PARA COMPROBAR TABLAS
SELECT * FROM Usuario;
SELECT * FROM Transferencia;
SELECT * FROM Bizum;
SELECT * FROM FormularioCarteras;
