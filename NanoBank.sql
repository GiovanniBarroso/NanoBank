-- Creación y selección de la base de datos

CREATE DATABASE IF NOT EXISTS NanoBank;
USE NanoBank;

-- Creación de Tablas
CREATE TABLE IF NOT EXISTS Usuario(
    id INT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(10) NOT NULL,
    contraseña VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    telefono INT (9) NOT NULL,
    email VARCHAR(50) NOT NULL,
    iban VARCHAR (50) NOT NULL
);

-- Insertamos datos del usuario ADMIN (Giovanni) si no existe ya
INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) 
SELECT * FROM (SELECT '30269762Z', 'admin123', 'Giovanni', '644126909', 'giovanni.baralv@gmail.com', 'ES62 2100 8434 7525 1401 1100') AS temp
WHERE NOT EXISTS (
    SELECT * FROM Usuario WHERE dni = '30269762' AND contraseña = 'admin123'
);


SELECT * FROM Usuario;
