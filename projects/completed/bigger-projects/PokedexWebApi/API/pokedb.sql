-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 24-05-2021 a las 19:13:06
-- Versión del servidor: 10.5.4-MariaDB
-- Versión de PHP: 7.3.21

--
-- Base de datos: `pokedb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

CREATE DATABASE pokedb;
USE pokedb;

DROP TABLE IF EXISTS `pokemon`;
CREATE TABLE IF NOT EXISTS `pokemon` (
  `name` varchar(45) NOT NULL,
  `number` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `skill` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `height` double NOT NULL,
  `weight` double NOT NULL,
  `sex` varchar(45) NOT NULL,
  `image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`number`,`name`),
  UNIQUE KEY `pokemon_un` (`name`)
);

--
-- Volcado de datos para la tabla `pokemon`
--

INSERT INTO `pokemon` (`name`, `number`, `description`, `skill`, `category`, `height`, `weight`, `sex`, `image`) VALUES
('Ditto', 132, 'He redistributes the cells of his body to take on the appearance of what he sees, but returns to normal when he relaxes.', 'Flexibility', 'Transformation', 0.3, 4, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/132.png'),
('Articuno', 144, 'Its beautiful blue wings are said to be made up of ice. It flies around the snowy mountains with its long tail blowing in the wind.', 'Preasure', 'Freeze', 1.7, 55.4, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/144.png'),
('Zapdos', 145, 'It has the power to control electricity at will. According to popular belief, it nests hidden in dark storm clouds.', 'Pressure', 'Electric', 1.6, 52.6, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/145.png'),
('Moltres', 146, 'Its evil aura resembling a burning flame can warm the soul of the one who touches it.', 'Anger', 'Malignancy', 2, 66, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/146.png'),
('Togetic', 176, 'They say that he appears to good-hearted people and floods them with happiness.', 'Enthusiasm', 'Happiness', 0.6, 3.2, 'Male / Female', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/176.png'),
('Unown', 201, 'These Pokémon are shaped like ancient characters. It is not known which came first, the old script or the different Unown. This question is still under study, but nothing has yet been found.', 'Levitation', 'Symbol', 0.5, 5, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/201.png'),
('Feebas', 349, 'Its unattractive appearance makes it not very popular, but its great vitality is of great interest to science.', 'Fast swim', 'Fish', 0.6, 7.4, 'Male / Female', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/349.png'),
('Milotic', 350, 'It is said to be the most beautiful Pokémon. It has been the source of inspiration for countless artists.', 'Special scale', 'Soft', 6.2, 162, 'Male / Female', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/350.png'),
('Lucario', 448, 'It hunts its prey by manipulating an energy, called aura, whose power is capable of even smashing huge rocks.', 'Impassive', 'Aura', 1.2, 54, 'Male / Female', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/448.png'),
('Rotom', 479, 'Thanks to the inventiveness of a certain young man, the manufacture of various gadgets that take advantage of Rotom\'s full potential has been started.', 'Levitation', 'Plasma', 0.3, 0.3, 'Unknown', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/479.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type`
--

DROP TABLE IF EXISTS `type`;
CREATE TABLE IF NOT EXISTS `type` (
  `id_type` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `rgb_color` varchar(16) NOT NULL,
  PRIMARY KEY (`id_type`,`name`)
);

--
-- Volcado de datos para la tabla `type`
--

INSERT INTO `type` (`id_type`, `name`, `rgb_color`) VALUES
(1, 'Psychic', 'rgb(243,102,185)'),
(2, 'Fire', 'rgb(253,125,036)'),
(3, 'Flying', 'rgb(126,193,212)'),
(4, 'Electric', 'rgb(238,213,053)'),
(5, 'Ice', 'rgb(081,196,231)'),
(6, 'Fighter', 'rgb(213,103,035)'),
(7, 'Steel', 'rgb(158,183,184)'),
(8, 'Fairy', 'rgb(253,185,233)'),
(9, 'Normal', 'rgb(165,173,176)'),
(10, 'Water', 'rgb(069,146,196)'),
(11, 'Ghost', 'rgb(123,098,163)'),
(12, 'Pipo2', 'rgb(122,122,122)');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type_pokemon`
--

DROP TABLE IF EXISTS `type_pokemon`;
CREATE TABLE IF NOT EXISTS `type_pokemon` (
  `num_pokemon` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  PRIMARY KEY (`num_pokemon`,`id_type`),
  KEY `id_type_idx` (`id_type`) USING BTREE,
  KEY `num_pokemon_idx` (`num_pokemon`)
);

--
-- Volcado de datos para la tabla `type_pokemon`
--

INSERT INTO `type_pokemon` (`num_pokemon`, `id_type`) VALUES
(132, 9),
(144, 3),
(144, 5),
(145, 3),
(145, 4),
(146, 2),
(146, 3),
(176, 3),
(176, 8),
(201, 1),
(349, 10),
(350, 10),
(448, 6),
(448, 7),
(479, 4),
(479, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `Username_UNIQUE` (`username`)
);

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, 'Pipo', '1234'),
(9, 'string', '$2a$11$ad/iUTQNkyoWOsI9ptmCq.n.fI6y8DizEQDoejO1/kVmWEBoXbRAu'),
(10, 'zackysh', '$2a$11$uKJVdhbqglOceY8cDTqITe8mHN.fXbVY0mMPefGfZlSKLxSgHRFCC'),
(11, 'Pipin', '$2a$11$Liun6i93YBErynzGXfnBueKD6..bdgFTLNaQwGvAm0TcGfHLBVsGW'),
(12, 'Pipin2', '$2a$11$lcksZLbU0szwCbckdnkV1.7Hm379fQU2KU4bM17934LI9cymL5uXy'),
(13, 'ocelot', '$2a$11$hq4IsbzsT7WRr2eScJQ7kuMjuna1.7qQriCk1bURdLy1mq5X205AW'),
(14, 'zopl', '$2a$11$Kci9LcNWbjV8hqO9CETsOe4KRe/l2ZuWL3oQIDhjCp6BYrrrRQsdG'),
(15, 'gffgg', '$2a$11$rErB0UeYsa5fQpWsVBo3.eWsgEt5QEHfDc0wljX4Sdax4Efo2npw6'),
(16, 'stringxxx', '$2a$11$V/Ofdb0e2v27w4vVa/LLVu7YxRhg1igdQStFb2ve5.wb8/HbWLITS'),
(17, 'pruebaFinal', '$2a$11$W2FdsSUPeIVh.MsSlfGm0eVG1Bf/fcLz2K2pP6sgAQ7T85npCskjC'),
(18, 'pruebaFinal2', '$2a$11$VaVkBT3zAbfQJqovscEuYOLz./PR94va2qHCqpnBFJlnaaRuZPuYi');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `type_pokemon`
--
ALTER TABLE `type_pokemon`
  ADD CONSTRAINT `id_type` FOREIGN KEY (`id_type`) REFERENCES `type` (`id_type`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `num_pokemon` FOREIGN KEY (`num_pokemon`) REFERENCES `pokemon` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;
