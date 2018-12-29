-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.13 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for codelab
CREATE DATABASE IF NOT EXISTS `codelab` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `codelab`;

-- Dumping structure for table codelab.commongridsetup
CREATE TABLE IF NOT EXISTS `commongridsetup` (
  `commongridsetupId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromClause` longtext,
  `json` longtext,
  `moduleId` bigint(20) DEFAULT NULL,
  `subModuleId` bigint(20) DEFAULT NULL,
  `createdById` bigint(20) DEFAULT NULL,
  `createdBy` varchar(50) DEFAULT NULL,
  `modifiedById` bigint(20) DEFAULT NULL,
  `modifiedBy` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`commongridsetupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table codelab.email
CREATE TABLE IF NOT EXISTS `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL,
  `senderId` bigint(20) DEFAULT NULL,
  `sender` varchar(256) DEFAULT NULL,
  `recipient` varchar(256) NOT NULL,
  `cc` varchar(256) DEFAULT NULL,
  `bcc` varchar(256) DEFAULT NULL,
  `subject` varchar(256) NOT NULL,
  `body` varchar(4000) NOT NULL,
  `signature` varchar(256) DEFAULT NULL,
  `isSent` varchar(4) NOT NULL,
  `attempts` int(11) NOT NULL,
  `maxAttempts` int(11) NOT NULL,
  `sentTime` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table codelab.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table codelab.module
CREATE TABLE IF NOT EXISTS `module` (
  `moduleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `moduleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`moduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table codelab.submodule
CREATE TABLE IF NOT EXISTS `submodule` (
  `submoduleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `moduleId` bigint(20) DEFAULT NULL,
  `subModuleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`submoduleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table codelab.user
CREATE TABLE IF NOT EXISTS `user` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(200) DEFAULT NULL,
  `Password` varchar(200) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT '1',
  `FirstName` varchar(50) DEFAULT '1',
  `LastName` varchar(50) DEFAULT '1',
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `City` varchar(50) DEFAULT NULL,
  `State` varchar(50) DEFAULT NULL,
  `Country` varchar(50) DEFAULT NULL,
  `PostalCode` varchar(20) DEFAULT NULL,
  `ResetPasswordFlag` tinyint(4) DEFAULT NULL,
  `ProfilePic` varchar(50) DEFAULT NULL,
  `CreatedOn` datetime DEFAULT NULL,
  `UpdatedOn` datetime DEFAULT NULL,
  `createdById` bigint(20) DEFAULT NULL,
  `updatedById` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
