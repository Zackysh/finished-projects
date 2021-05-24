-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 23-01-2021 a las 21:45:54
-- Versión del servidor: 5.7.31
-- Versión de PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pokedb`
--

CREATE DATABASE pokedb;
USE pokedb;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cpokemon`
--

DROP TABLE IF EXISTS `cpokemon`;
CREATE TABLE IF NOT EXISTS `cpokemon` (
  `idCPoke` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  `idPoke` int(11) NOT NULL,
  `idTeam` int(11) NOT NULL,
  PRIMARY KEY (`idCPoke`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`),
  KEY `idPoke_idx` (`idPoke`),
  KEY `idTeam_idx` (`idTeam`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cpokemon`
--

INSERT INTO `cpokemon` (`idCPoke`, `nickname`, `level`, `idPoke`, `idTeam`) VALUES
(47, 'Rottin', 1, 9, 1),
(74, 'Articuno', 1, 3, 5),
(76, 'Arceus', 100, 11, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

DROP TABLE IF EXISTS `pokemon`;
CREATE TABLE IF NOT EXISTS `pokemon` (
  `idpoke` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Number` int(11) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Skill` varchar(45) NOT NULL,
  `Category` varchar(45) NOT NULL,
  `Height` double NOT NULL,
  `Weight` double NOT NULL,
  `Sex` varchar(45) NOT NULL,
  `BaseAttributes` varchar(11) NOT NULL,
  PRIMARY KEY (`idpoke`),
  UNIQUE KEY `Name_UNIQUE` (`Name`),
  UNIQUE KEY `Number_UNIQUE` (`Number`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pokemon`
--

INSERT INTO `pokemon` (`idpoke`, `Name`, `Number`, `Description`, `Skill`, `Category`, `Height`, `Weight`, `Sex`, `BaseAttributes`) VALUES
(2, 'Zapdos', 145, 'It has the power to control electricity at will. According to popular belief it nests hidden in dark storm clouds.', 'Pressure', 'Electric', 1.6, 52.6, 'Unknown', '6/6/5/8/6/6'),
(3, 'Articuno', 144, 'Its beautiful blue wings are said to be made up of ice. It flies around the snowy mountains with its long tail blowing in the wind.', 'Preasure', 'Freeze', 1.7, 55.4, 'Unknown', '6/5/6/6/8/5'),
(4, 'Lucario', 448, 'It hunts its prey by manipulating an energy, called aura, whose power is capable of even smashing huge rocks.', 'Impassive', 'Aura', 1.2, 54, 'Male  Female', '5/7/5/7/5/6'),
(5, 'Togetic', 176, 'They say that he appears to good-hearted people and floods them with happiness.', 'Enthusiasm', 'Happiness', 0.6, 3.2, 'Male / Female', '4/3/5/5/7/3'),
(6, 'Ditto', 132, 'He redistributes the cells of his body to take on the appearance of what he sees, but returns to normal when he relaxes.', 'Flexibility', 'Transformation', 0.3, 4, 'Unknown', '3/3/3/3/3/3'),
(7, 'Feebas', 222, 'Its unattractive appearance makes it not very popular, but its great pipo is of great interest to science.', 'Fast swim', 'Fish', 0.6, 7.8, 'Male  Female', '9/9/3/3/9/6'),
(8, 'Milotic', 350, 'It is said to be the most beautiful Pokémon. It has been the source of inspiration for countless artists.', 'Special scale', 'Soft', 6.2, 162, 'Male / Female', '6/4/5/6/8/5'),
(9, 'Rotom', 479, 'Thanks to the inventiveness of a certain young man, the manufacture of various gadgets that take advantage of Rotoms full potential has been started.', 'Levitation', 'Plasma', 0.3, 0.3, 'Unknown', '3/3/5/6/5/6'),
(10, 'Unown', 201, 'These Pokémon are shaped like ancient characters. It is not known which came first, the old script or the different Unown. This question is still under study, but nothing has yet been found.', 'Levitation', 'Symbol', 0.5, 5, 'Unknown', '3/5/3/5/3/3'),
(11, 'Arceus', 493, 'According to Sinnoh mythology, Arceus hatched from an egg and then created the entire world.', 'Multitype', 'Alpha', 3.2, 320, 'Unknown', '8/8/8/8/8/8'),
(12, 'Moltres', 146, 'Its evil aura resembling a burning flame can scorch the soul of the one who touches it.', 'Anger', 'Malignancy', 2, 66, 'Unknown', '6/5/6/6/8/6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `poketype`
--

DROP TABLE IF EXISTS `poketype`;
CREATE TABLE IF NOT EXISTS `poketype` (
  `idpoke` int(11) NOT NULL,
  `idtype` int(11) NOT NULL,
  PRIMARY KEY (`idpoke`,`idtype`),
  KEY `idpoke_idx` (`idpoke`),
  KEY `idtipo_idx` (`idtype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `poketype`
--

INSERT INTO `poketype` (`idpoke`, `idtype`) VALUES
(2, 3),
(2, 4),
(3, 3),
(3, 5),
(4, 6),
(4, 7),
(5, 3),
(5, 8),
(6, 9),
(7, 10),
(8, 10),
(9, 4),
(9, 11),
(10, 1),
(11, 1),
(11, 2),
(11, 3),
(11, 4),
(11, 5),
(11, 6),
(11, 7),
(11, 8),
(11, 9),
(11, 10),
(11, 11),
(11, 12),
(11, 13),
(11, 14),
(11, 15),
(11, 16),
(11, 17),
(11, 18),
(12, 3),
(12, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `team`
--

DROP TABLE IF EXISTS `team`;
CREATE TABLE IF NOT EXISTS `team` (
  `idTeam` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY (`idTeam`),
  UNIQUE KEY `Name_UNIQUE` (`Name`),
  UNIQUE KEY `idUser_UNIQUE` (`idUser`),
  KEY `idUser_idx` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `team`
--

INSERT INTO `team` (`idTeam`, `Name`, `idUser`) VALUES
(1, 'PipoTeam', 1),
(2, 'ZackyTeam', 3),
(5, 'TestTeam', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type`
--

DROP TABLE IF EXISTS `type`;
CREATE TABLE IF NOT EXISTS `type` (
  `idtype` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `RGBColor` varchar(13) NOT NULL,
  PRIMARY KEY (`idtype`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `type`
--

INSERT INTO `type` (`idtype`, `Name`, `RGBColor`) VALUES
(1, 'Psychic', '(243,102,185)'),
(2, 'Fire', '(253,125,036)'),
(3, 'Flying', '(126,193,212)'),
(4, 'Electric', '(238,213,053)'),
(5, 'Ice', '(081,196,231)'),
(6, 'Fighting', '(213,103,035)'),
(7, 'Steel', '(158,183,184)'),
(8, 'Fairy', '(253,185,233)'),
(9, 'Normal', '(165,173,176)'),
(10, 'Water', '(069,146,196)'),
(11, 'Ghost', '(123,098,163)'),
(12, 'Bug', '(168,184,032)'),
(13, 'Poison', '(160,064,160)'),
(14, 'Rock', '(184,160,056)'),
(15, 'Ground', '(224,192,104)'),
(16, 'Dragon', '(112,056,248)'),
(17, 'Dark', '(112,088,072)'),
(18, 'Grass', '(120,200,080)');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`idUser`, `Username`, `Password`) VALUES
(1, 'Pipo', '1234'),
(3, 'Zackysh', '123546');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cpokemon`
--
ALTER TABLE `cpokemon`
  ADD CONSTRAINT `idTeam` FOREIGN KEY (`idTeam`) REFERENCES `team` (`idTeam`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `poketype`
--
ALTER TABLE `poketype`
  ADD CONSTRAINT `idpoke` FOREIGN KEY (`idpoke`) REFERENCES `pokemon` (`idpoke`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `idtype` FOREIGN KEY (`idtype`) REFERENCES `type` (`idtype`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
