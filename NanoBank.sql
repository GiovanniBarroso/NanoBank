-- Creación y selección de la base de datos

DROP DATABASE IF EXISTS NanoBank;
CREATE DATABASE IF NOT EXISTS NanoBank;
USE NanoBank;

-- Creación de Tablas

CREATE TABLE IF NOT EXISTS Usuario(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
dni INT NOT NULL,
contraseña VARCHAR(20) NOT NULL,
nombre VARCHAR(50) NOT NULL,
telefono INT (9) NOT NULL,
email VARCHAR(50) NOT NULL,
iban VARCHAR (50) NOT NULL
);


-- Insertamos datos del usuario ADMIN (Giovanni)
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
VALUES ('30269762', 'admin', 'Giovanni', '644126909', 'giovanni.baralv@gmail.com', 'ES62 2100 8434 7525 1401 1100');