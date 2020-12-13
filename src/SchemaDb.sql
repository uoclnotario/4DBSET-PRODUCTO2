
DROP TABLE IF EXISTS `colaboradores`;


CREATE TABLE `colaboradores` (
  `idPersonal` int NOT NULL,
  `tipoColaboracion` varchar(45) NOT NULL,
  PRIMARY KEY (`idPersonal`),
  KEY `fk_COLABORADORES_PERSONAL1_idx` (`idPersonal`),
  CONSTRAINT `fk_COLABORADORES_PERSONAL1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `contratados`;
CREATE TABLE `contratados` (
  `idPersonal` int NOT NULL,
  `tipoContrato` varchar(45) NOT NULL,
  `salario` decimal(2,0) NOT NULL,
  PRIMARY KEY (`idPersonal`),
  KEY `fk_CONTRATADOS_PERSONAL1_idx` (`idPersonal`),
  CONSTRAINT `fk_CONTRATADOS_PERSONAL1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `delegacion`;
CREATE TABLE `delegacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipoPersona` varchar(45) NOT NULL,
  `nif_dni` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `domicilio` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `personal`;
CREATE TABLE `personal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fechaAlta` date NOT NULL,
  `fechaBaja` date DEFAULT NULL,
  `estado` tinyint NOT NULL,
  `idDelegacion` int DEFAULT NULL,
  `idPersona` int DEFAULT NULL,
  `idProyecto` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PERSONAL_DELEGACION1_idx` (`idDelegacion`),
  KEY `fk_PERSONAL_PERSONA1_idx` (`idPersona`),
  KEY `fk_PERSONAL_PROYECTOS1_idx` (`idProyecto`),
  CONSTRAINT `fk_PERSONAL_DELEGACION1` FOREIGN KEY (`idDelegacion`) REFERENCES `delegacion` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_PERSONAL_PERSONA1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_PERSONAL_PROYECTOS1` FOREIGN KEY (`idProyecto`) REFERENCES `proyectos` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `proyectos`;
CREATE TABLE `proyectos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fechaAlta` date NOT NULL,
  `fechaBaja` date NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `fechaDeInicio` date NOT NULL,
  `estado` bit(1) NOT NULL,
  `idDelegacion` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PROYECTOS_DELEGACION1_idx` (`idDelegacion`),
  CONSTRAINT `fk_PROYECTOS_DELEGACION1` FOREIGN KEY (`idDelegacion`) REFERENCES `delegacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `tipoUsuario` int NOT NULL,
  `hashing` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `voluntarios`;
CREATE TABLE `voluntarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idPersonal` int NOT NULL,
  `areaVoluntariado` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`idPersonal`),
  KEY `fk_VOLUNTARIOS_PERSONAL1_idx` (`idPersonal`),
  CONSTRAINT `fk_VOLUNTARIOS_PERSONAL1` FOREIGN KEY (`idPersonal`) REFERENCES `personal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `voluntarios internacionales`;
CREATE TABLE `voluntarios internacionales` (
  `volunariosId` int NOT NULL,
  `pais` varchar(45) NOT NULL,
  PRIMARY KEY (`volunariosId`),
  CONSTRAINT `fk_VOLUNTARIOS INTERNACIONALES_VOLUNTARIOS1` FOREIGN KEY (`volunariosId`) REFERENCES `voluntarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

