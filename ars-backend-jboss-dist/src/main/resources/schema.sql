# Source on localhost: ... connected.
# Exporting metadata from ars
DROP DATABASE IF EXISTS ars;
CREATE DATABASE ars;
USE ars;
# TABLE: ars.aircraft
CREATE TABLE `aircraft` (
  `id` char(36) NOT NULL,
  `version` bigint(20) NOT NULL,
  `registration_code` varchar(8) NOT NULL,
  `aircraft_type` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qxhcqr977ev4tdh939y80k12q` (`registration_code`),
  KEY `FK_bise9lj7ud4hurrklmtc4hvic` (`aircraft_type`),
  CONSTRAINT `FK_bise9lj7ud4hurrklmtc4hvic` FOREIGN KEY (`aircraft_type`) REFERENCES `aircraft_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# TABLE: ars.aircraft_type
CREATE TABLE `aircraft_type` (
  `id` char(36) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ht9xr3rlc4cketeoeom76l2qo` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# TABLE: ars.licence
CREATE TABLE `licence` (
  `id` char(36) NOT NULL,
  `version` bigint(20) NOT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `user_id` varchar(64) NOT NULL,
  `aircraft_type` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ld7i5t7q0vgfim6u289prp988` (`aircraft_type`),
  CONSTRAINT `FK_ld7i5t7q0vgfim6u289prp988` FOREIGN KEY (`aircraft_type`) REFERENCES `aircraft_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# TABLE: ars.reservation
CREATE TABLE `reservation` (
  `id` char(36) NOT NULL,
  `version` bigint(20) NOT NULL,
  `aircraft_id` char(36) NOT NULL,
  `begin_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `state` varchar(16) NOT NULL,
  `user_id` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# TABLE: ars.user
CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `version` bigint(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
#...done.
