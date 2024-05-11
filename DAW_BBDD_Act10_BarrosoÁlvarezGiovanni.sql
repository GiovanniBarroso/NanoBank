/*** Creción y selección de la base de datos ***/
DROP DATABASE IF EXISTS club_juegos_mesa;
CREATE DATABASE club_juegos_mesa character set utf8 collate utf8_spanish_ci;
USE club_juegos_mesa;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `club_juegos_mesa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `fechaNac` date DEFAULT NULL,
  `juegoAlquilado` tinyint(1) DEFAULT 0,
  `mesa` int(11) DEFAULT NULL,
  `DNI` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `nombre`, `apellidos`, `fechaNac`, `juegoAlquilado`, `mesa`, `DNI`) VALUES
(1, 'Manuel', 'Garrido Sánchez', '1975-05-14', 0, 1, '15654439D'),
(2, 'Sandra', 'Parrilla Gómez', '2005-08-20', 1, NULL, '72669348M'),
(3, 'Sergio', 'Acosta Blanco', '1987-02-08', 1, 1, '18236430S');



CREATE TABLE cliente_bk (
  id_cliente INT(11) NOT NULL,
  nombre VARCHAR(15) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  fecha_nacimiento DATE DEFAULT NULL,
  DNI VARCHAR(16) DEFAULT NULL,
  PRIMARY KEY (id_cliente)
);
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente_juegoalquiler`
--

CREATE TABLE `cliente_juegoalquiler` (
  `idCliente` int(11) NOT NULL,
  `idJuegoAlquiler` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `total` int(11) NOT NULL,
  `formaPago` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`id`, `fecha`, `total`, `formaPago`, `idCliente`) VALUES
(1, '2021-01-17', 75, 'Efectivo', 2),
(2, '2021-03-05', 15, 'Tarjeta', 3),
(3, '2021-06-08', 30, 'Efectivo', 3),
(4, '2022-03-01', 246, 'Paypal', 3),
(5, '2022-04-12', 35, 'Tarjeta', 1),
(6, '2022-03-30', 150, 'Bizum', 2),
(7, '2022-05-03', 91, 'Efectivo', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` int(11) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `nombreEmpresa` varchar(64) DEFAULT NULL,
  `cp` varchar(5) NOT NULL,
  `calle` varchar(40) NOT NULL,
  `poblacion` varchar(20) NOT NULL,
  `ciudad` varchar(20) NOT NULL,
  `idCompra` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juego`
--

CREATE TABLE `juego` (
  `id` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `edadRecomendada` int(11) NOT NULL,
  `numJugadores` int(11) NOT NULL,
  `tiempo_estimado` int(11) DEFAULT NULL,
  `editor` varchar(128) DEFAULT NULL,
  `indice_equilibrio` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `juego`
--

INSERT INTO `juego` (`id`, `nombre`, `edadRecomendada`, `numJugadores`, `tiempo_estimado`, `editor`, `indice_equilibrio`) VALUES
(1, 'Catán', 10, 4, 75, 'Devir', NULL),
(2, 'Carcassone', 7, 5, 35, 'Devir', NULL),
(3, 'Virus', 8, 6, 25, 'Tranjis Games', NULL),
(4, 'Dixit Classic', 8, 6, 30, 'Asmodee', NULL),
(5, 'Terraforming Mars', 12, 5, 90, 'Maldito Games', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegoalquiler`
--

CREATE TABLE `juegoalquiler` (
  `id` int(11) NOT NULL,
  `alquilado` tinyint(1) DEFAULT 0,
  `fechaPrimerAlquiler` date DEFAULT NULL,
  `antiguedad` int(11) DEFAULT NULL,
  `idJuego` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegofavorito`
--

CREATE TABLE `juegofavorito` (
  `id` int(11) NOT NULL,
  `juego` varchar(20) NOT NULL,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegosuelealquilar`
--

CREATE TABLE `juegosuelealquilar` (
  `id` int(11) NOT NULL,
  `juego` varchar(20) DEFAULT NULL,
  `idOcasional` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juegoventa`
--

CREATE TABLE `juegoventa` (
  `id` int(11) NOT NULL,
  `precio` float(20,6) DEFAULT NULL,
  `stock` int(11) DEFAULT 0,
  `idJuego` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `juegoventa`
--

INSERT INTO `juegoventa` (`id`, `precio`, `stock`, `idJuego`) VALUES
(1, 9.999999, 2, 1),
(2, 20.500000, 11, 3),
(3, 9.999999, 3, 4),
(4, 35.950001, 7, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineacompra`
--

CREATE TABLE `lineacompra` (
  `id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` int(11) NOT NULL,
  `idCompra` int(11) NOT NULL,
  `idJuegoVenta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `lineacompra`
--

INSERT INTO `lineacompra` (`id`, `cantidad`, `precio`, `idCompra`, `idJuegoVenta`) VALUES
(1, 1, 45, 1, 1),
(2, 2, 30, 1, 4),
(3, 1, 15, 2, 2),
(4, 1, 30, 3, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesa`
--

CREATE TABLE `mesa` (
  `id` int(11) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `tipoJuego` varchar(20) NOT NULL,
  `esOcupada` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mesa`
--

INSERT INTO `mesa` (`id`, `capacidad`, `tipoJuego`, `esOcupada`) VALUES
(1, 6, 'De miniaturas', 1),
(2, 3, 'De tablero', 1),
(3, 8, 'De tablero', 0),
(4, 10, 'De tablero', 0),
(5, 4, 'De miniaturas', 1),
(6, 12, 'De cartas', 0),
(7, 6, 'De miniaturas', 0),
(8, 20, 'De miniaturas', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesa_juego`
--

CREATE TABLE `mesa_juego` (
  `idMesa` int(11) NOT NULL,
  `idJuego` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mesa_juego`
--

INSERT INTO `mesa_juego` (`idMesa`, `idJuego`) VALUES
(2, 3),
(3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ocasional`
--

CREATE TABLE `ocasional` (
  `id` int(11) NOT NULL,
  `fechaUltVisita` date DEFAULT NULL,
  `acompanadoSocio` tinyint(1) DEFAULT 0,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socio`
--

CREATE TABLE `socio` (
  `id` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `antiguedad` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `socio`
--

INSERT INTO `socio` (`id`, `fechaInicio`, `antiguedad`, `idCliente`) VALUES
(1, '2012-04-18', 10, 2),
(2, '2020-10-21', 1, 3),
(3, '2021-12-06', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket`
--

CREATE TABLE `ticket` (
  `idTicket` int(11) NOT NULL,
  `dependiente` varchar(32) DEFAULT NULL,
  `idCompra` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `DNI` (`DNI`),
  ADD KEY `cod_mesa` (`mesa`);

--
-- Indices de la tabla `cliente_juegoalquiler`
--
ALTER TABLE `cliente_juegoalquiler`
  ADD PRIMARY KEY (`idCliente`,`idJuegoAlquiler`),
  ADD KEY `fkJuegoAlq` (`idJuegoAlquiler`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkClienteCompra` (`idCliente`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkCompraFactura` (`idCompra`);

--
-- Indices de la tabla `juego`
--
ALTER TABLE `juego`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `juegoalquiler`
--
ALTER TABLE `juegoalquiler`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkJuegoAlquiler` (`idJuego`);

--
-- Indices de la tabla `juegofavorito`
--
ALTER TABLE `juegofavorito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkCliente` (`idCliente`);

--
-- Indices de la tabla `juegosuelealquilar`
--
ALTER TABLE `juegosuelealquilar`
  ADD KEY `fkOcasional` (`idOcasional`);

--
-- Indices de la tabla `juegoventa`
--
ALTER TABLE `juegoventa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkJuegoVenta` (`idJuego`);

--
-- Indices de la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkCompraLinea` (`idCompra`),
  ADD KEY `fkJuegVenta` (`idJuegoVenta`);

--
-- Indices de la tabla `mesa`
--
ALTER TABLE `mesa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mesa_juego`
--
ALTER TABLE `mesa_juego`
  ADD PRIMARY KEY (`idMesa`,`idJuego`),
  ADD KEY `fkJuego` (`idJuego`);

--
-- Indices de la tabla `ocasional`
--
ALTER TABLE `ocasional`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkClient` (`idCliente`);

--
-- Indices de la tabla `socio`
--
ALTER TABLE `socio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkClientesocio` (`idCliente`);

--
-- Indices de la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`idTicket`),
  ADD KEY `fkidCompraTicket` (`idCompra`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `juego`
--
ALTER TABLE `juego`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `juegoalquiler`
--
ALTER TABLE `juegoalquiler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `juegofavorito`
--
ALTER TABLE `juegofavorito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `juegoventa`
--
ALTER TABLE `juegoventa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `mesa`
--
ALTER TABLE `mesa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `ocasional`
--
ALTER TABLE `ocasional`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `socio`
--
ALTER TABLE `socio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ticket`
--
ALTER TABLE `ticket`
  MODIFY `idTicket` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cod_mesa` FOREIGN KEY (`mesa`) REFERENCES `mesa` (`id`);

--
-- Filtros para la tabla `cliente_juegoalquiler`
--
ALTER TABLE `cliente_juegoalquiler`
  ADD CONSTRAINT `fkClienteJueg` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `fkJuegoAlq` FOREIGN KEY (`idJuegoAlquiler`) REFERENCES `juegoalquiler` (`id`);

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `fkClienteCompra` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `fkCompraFactura` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`id`);

--
-- Filtros para la tabla `juegoalquiler`
--
ALTER TABLE `juegoalquiler`
  ADD CONSTRAINT `fkJuegoAlquiler` FOREIGN KEY (`idJuego`) REFERENCES `juego` (`id`);

--
-- Filtros para la tabla `juegofavorito`
--
ALTER TABLE `juegofavorito`
  ADD CONSTRAINT `fkCliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `juegosuelealquilar`
--
ALTER TABLE `juegosuelealquilar`
  ADD CONSTRAINT `fkOcasional` FOREIGN KEY (`idOcasional`) REFERENCES `ocasional` (`id`);

--
-- Filtros para la tabla `juegoventa`
--
ALTER TABLE `juegoventa`
  ADD CONSTRAINT `fkJuegoVenta` FOREIGN KEY (`idJuego`) REFERENCES `juego` (`id`);

--
-- Filtros para la tabla `lineacompra`
--
ALTER TABLE `lineacompra`
  ADD CONSTRAINT `fkCompraLinea` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`id`),
  ADD CONSTRAINT `fkJuegVenta` FOREIGN KEY (`idJuegoVenta`) REFERENCES `juegoventa` (`id`);

--
-- Filtros para la tabla `mesa_juego`
--
ALTER TABLE `mesa_juego`
  ADD CONSTRAINT `fkJuego` FOREIGN KEY (`idJuego`) REFERENCES `juego` (`id`),
  ADD CONSTRAINT `fkMesa` FOREIGN KEY (`idMesa`) REFERENCES `mesa` (`id`);

--
-- Filtros para la tabla `ocasional`
--
ALTER TABLE `ocasional`
  ADD CONSTRAINT `fkClient` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `socio`
--
ALTER TABLE `socio`
  ADD CONSTRAINT `fkClientesocio` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `fkidCompraTicket` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


-- Ejercicio 1
DELIMITER //
CREATE PROCEDURE RegistrarComprasEnEfectivo()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE compra_id INT;
    DECLARE forma_pago VARCHAR(15);
    DECLARE dependiente_random INT;
    DECLARE cur CURSOR FOR SELECT id, formaPago FROM compra WHERE formaPago = 'Efectivo';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO compra_id, forma_pago;
        IF done THEN
            LEAVE read_loop;
        END IF;
        SET dependiente_random = FLOOR(1 + RAND() * 5);
        INSERT INTO ticket (dependiente, idCompra) VALUES (dependiente_random, compra_id);
    END LOOP;
    CLOSE cur;
END //
DELIMITER ;


-- Ejercicio 2
DELIMITER //
CREATE PROCEDURE CalcularIndiceEquilibrio()
BEGIN

    DECLARE done INT DEFAULT 0;
    DECLARE juego_id INT;
    DECLARE edad_recomendada INT;
    DECLARE num_jugadores INT;
    DECLARE tiempoEstimado INT;
    DECLARE indice_equilibrio FLOAT;

    -- Declarar un cursor para seleccionar los juegos
    DECLARE cur CURSOR FOR SELECT id, edadRecomendada, numJugadores, tiempo_estimado FROM juego;
    -- Manejador para el caso de no encontrar resultados
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- Abrir el cursor
    OPEN cur;
    
    -- Inicio del bucle
    read_loop: LOOP
        -- Intentar obtener datos del cursor
        FETCH cur INTO juego_id, edad_recomendada, num_jugadores, tiempoEstimado;
        -- Si no hay más datos, salir del bucle
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Calcular el índice de equilibrio, evitando la división por cero
        IF (edad_recomendada * num_jugadores) = 0 THEN
            SET indice_equilibrio = NULL;
        ELSE
            SET indice_equilibrio = tiempoEstimado / (edad_recomendada * num_jugadores);
        END IF;

        -- Actualizar el índice de equilibrio en la tabla juego
        UPDATE juego SET indice_equilibrio = indice_equilibrio WHERE id = juego_id;
    END LOOP;

    -- Cerrar el cursor
    CLOSE cur;
END //

DELIMITER ;



-- Ejercicio 4
DELIMITER //
CREATE TRIGGER AplicarDescuento
BEFORE INSERT ON compra
FOR EACH ROW
BEGIN
    IF NEW.total > 100 THEN
        SET NEW.total = NEW.total * 0.8; -- Descuento del 20% si la compra es superior a 100€
    ELSE
        SET NEW.total = NEW.total * 0.9; -- Descuento del 10% si la compra es inferior a 100€
    END IF;
END;
//



-- Ejercicio 5
CREATE TRIGGER CopiarClienteABackup
AFTER INSERT ON cliente
FOR EACH ROW
BEGIN
    INSERT INTO cliente_bk (id_cliente, nombre, apellidos, fecha_nacimiento, DNI)
    VALUES (NEW.id, NEW.nombre, NEW.apellidos, NEW.fechaNac, NEW.DNI);
END;
//


-- Ejercicio 6
CREATE TRIGGER EliminarClienteDeBackup
AFTER DELETE ON cliente
FOR EACH ROW
BEGIN
    -- Eliminar entradas relacionadas en la tabla 'compra'
    DELETE FROM compra WHERE idCliente = OLD.id;
    
    -- Eliminar el cliente de la tabla de respaldo 'cliente_bk'
    DELETE FROM cliente_bk WHERE id_cliente = OLD.id;
END;
//
DELIMITER ;




/* Ejecutar el procedimiento almacenado para registrar compras en efectivo
y realizamos consulta de la tabla ticket para comprobar si se aplican los registros */
CALL RegistrarComprasEnEfectivo();
SELECT * FROM ticket;



-- Insertamos 2 valores en compra para comprobar si se aplica el descuento del precio
INSERT INTO compra (fecha, total, formaPago, idCliente) 
VALUES ('2024-05-10', 120, 'Efectivo', 1), ('2024-05-11', 55, 'Tarjeta', 2);
SELECT * FROM compra;



-- Consultar la tabla de juegos para verificar si se calculó el índice de equilibrio correctamente
CALL CalcularIndiceEquilibrio();
select * from juego;



-- Insertar un nuevo cliente y comprobamos si se añade a la tabla cliente_bk
INSERT INTO cliente (nombre, apellidos, fechaNac, juegoAlquilado, mesa, DNI) 
VALUES ('Ana', 'García Pérez', '1990-12-15', 0, 1, '12345678A');
SELECT * FROM cliente;
SELECT * FROM cliente_bk;



-- Eliminar un cliente para probar trigger
SELECT * FROM cliente;
DELETE FROM cliente WHERE id = 4;
SELECT * FROM cliente; -- Consulta tras eliminar el cliente