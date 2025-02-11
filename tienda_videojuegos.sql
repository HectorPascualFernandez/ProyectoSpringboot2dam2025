-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda_videojuegos`
--
CREATE DATABASE IF NOT EXISTS `tienda_videojuegos` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci;
USE `tienda_videojuegos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
('pedro', '$2a$10$mDurJ0bnNE72lK.Jy8JTrerw9Ux6/G7IEsERzz4LI8/Ir165/p4ZG', 1),
('admin', '$2a$10$1i1vx9ez9ctO2eNDSHwjE.IWAtr2aAfWrE2hps.2uRgXYpvu69ufa', 1),
('pedro2', '$2a$10$DSlO5g8RfdKWAB7hkCOSeuKrfW8ggref7.mwPYCaSViNfkhspvPeu', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) 
VALUES 
(1, 2),
(2, 1),
(2, 2),
(3, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(200),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`nombre`, `descripcion`) VALUES
('Acción', 'Juegos de acción y aventura'),
('Deportes', 'Juegos deportivos'),
('RPG', 'Juegos de rol');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuegos`
--

DROP TABLE IF EXISTS `videojuegos`;
CREATE TABLE `videojuegos` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `categoria_id` int(3) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `precio` double(6,2) NOT NULL,
  `stock` int(5) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `videojuegos`
--

INSERT INTO `videojuegos` (`categoria_id`, `titulo`, `precio`, `stock`) VALUES
(1, 'God of War Ragnarök', 59.99, 25),
(2, 'FIFA 24', 69.99, 30),
(3, 'Final Fantasy XVI', 49.99, 15),
(1, 'Spider-Man 2', 69.99, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

DROP TABLE IF EXISTS `ventas`;
CREATE TABLE `ventas` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `videojuego_id` int(3) NOT NULL,
  `fecha_venta` DATE NOT NULL,
  `cantidad` int(2) NOT NULL,
  `precio_total` double(7,2) NOT NULL,
  `nombre_cliente` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`videojuego_id`) REFERENCES `videojuegos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`videojuego_id`, `fecha_venta`, `cantidad`, `precio_total`, `nombre_cliente`) VALUES
(1, '2024-02-15', 2, 119.98, 'Juan Pérez'),
(2, '2024-02-16', 1, 69.99, 'María López'),
(3, '2024-02-15', 3, 149.97, 'Carlos García'),
(4, '2024-02-17', 1, 69.99, 'Ana Martínez');

--
-- Índices adicionales para búsquedas frecuentes
--
CREATE INDEX idx_videojuego_categoria ON videojuegos(categoria_id);
CREATE INDEX idx_venta_videojuego ON ventas(videojuego_id);
CREATE INDEX idx_venta_fecha ON ventas(fecha_venta);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET CHARACTERS_CONNECTION=@OLD_COLLATION_CONNECTION */;