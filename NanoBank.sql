-- Creación y selección de la base de datos

CREATE DATABASE IF NOT EXISTS NanoBank;
USE NanoBank;

-- TABLA USUARIO
CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    saldo FLOAT(20,5) DEFAULT 1000.0,
    dni VARCHAR(10) NOT NULL,
    contraseña VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono INT (9) NOT NULL, 
    email VARCHAR(50) NOT NULL,
    iban VARCHAR(50) NOT NULL
);



-- TABLA TRANSACCIONES
CREATE TABLE IF NOT EXISTS Transacciones (
id_transaciones INT PRIMARY KEY AUTO_INCREMENT,
cuentaOrigen VARCHAR (50) NOT NULL,
cuentaDestino VARCHAR (100) NOT NULL,
nombreDestino VARCHAR (50) NOT NULL,
cantidad FLOAT (20,5) NOT NULL,
concepto VARCHAR (250) NOT NULL,
id_usuario INT,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);



-- Insertamos datos del usuario ADMIN (Giovanni) si no existe ya
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
SELECT * FROM (SELECT '30269762Z', 'admin123', 'Giovanni', '644126909', 'giovanni.baralv@gmail.com', 'ES62 2100 8434 7525 1401 1100') AS temp
WHERE NOT EXISTS (
    SELECT * FROM Usuario WHERE dni = '30269762Z' AND contraseña = 'admin123'
);


SELECT * FROM Usuario;
SELECT * FROM Transacciones;
