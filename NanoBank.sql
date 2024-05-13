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
cuentaOrigen VARCHAR (50) NOT NULL,
cuentaDestino VARCHAR (100) NOT NULL,
nombreDestino VARCHAR (50) NOT NULL,
cantidad FLOAT (20,2) NOT NULL,
concepto VARCHAR (250) NOT NULL,
id_usuario INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);



-- TABLA BIZUM
CREATE TABLE IF NOT EXISTS Bizum (
id_transaciones INT PRIMARY KEY AUTO_INCREMENT,
cuentaOrigen VARCHAR (50) NOT NULL,
telefono INT (10) NOT NULL,
nombreDestino VARCHAR (50) NOT NULL,
cantidad FLOAT (20,2) NOT NULL,
concepto VARCHAR (250) NOT NULL,
id_usuario INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);



-- TABLA FORMULARIOCARTERAS
CREATE TABLE IF NOT EXISTS FormularioCarteras (
id_cartera INT PRIMARY KEY AUTO_INCREMENT,
porcentajeRF FLOAT (20,2) NOT NULL,
porcentajeRV FLOAT (20,2) NOT NULL,
cantidadInvertida FLOAT (20,2) NOT NULL,
id_usuario INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);



-- Insertamos datos del usuario Giovanni si no existe
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
SELECT * FROM (SELECT '30269762Z', 'admin123', 'Giovanni', '644126909', 'giovanni.baralv@gmail.com', 'ES62 2100 8434 5702 0011 1410') AS temp
WHERE NOT EXISTS (
    SELECT * FROM Usuario WHERE dni = '30269762Z' AND contraseña = 'admin123'
);


-- Insertamos datos del usuario Francisco si no existe 
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
SELECT * FROM (SELECT '28724060G', 'admin123', 'Francisco', '696787146', 'joframan@hotmail.es', 'ES51 2100 8434 5821 0011 9914') AS temp
WHERE NOT EXISTS (
    SELECT * FROM Usuario WHERE dni = '28724060G' AND contraseña = 'admin123'
);

-- Insertamos datos del usuario Mari Carmen si no existe 
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
SELECT * FROM (SELECT '28591788M', 'admin123', 'Mari Carmen', '669997119', 'LUNERA1000@HOTMAIL.COM', 'ES11 2100 8434 5802 0011 2182') AS temp
WHERE NOT EXISTS (
    SELECT * FROM Usuario WHERE dni = '28591788M' AND contraseña = 'admin123'
);



-- SELECT PARA COMPROBAR TABLAS
SELECT * FROM Usuario;
SELECT * FROM Transferencia;
SELECT * FROM Bizum;
SELECT * FROM FormularioCarteras;