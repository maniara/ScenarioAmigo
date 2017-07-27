-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sofar_db
-- ------------------------------------------------------
-- Server version	5.7.9-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `projectNum` int(11) NOT NULL AUTO_INCREMENT,
  `projectID` varchar(45) NOT NULL,
  `projectName` varchar(45) DEFAULT NULL,
  `projectDescription` varchar(1000) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `createPerson` varchar(45) DEFAULT NULL,
  `forTraining` tinyint(1) DEFAULT '0',
  `domain` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`projectNum`),
  UNIQUE KEY `projectID_UNIQUE` (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (29,'ATM','ATM','','2015-03-10 14:46:42','',1,NULL),(30,'OSS','Online Shopping System','','2015-03-18 15:10:49','H.Gomaa',1,NULL),(31,'PRS','Payroll system','','2015-03-23 11:09:02','anonymous',1,NULL),(32,'cos','Cafeteria ordering system','','2015-03-24 13:01:40','HMS',1,NULL),(33,'POS','POS System','from Applying uml and patterns','2015-04-09 12:44:29','',1,NULL),(34,'UIS','대학정보시스템','','2015-04-15 15:10:22','채흥석',1,NULL),(35,'TMS','Traffic Management','OOAD with App thrid edition','2015-04-15 17:51:25','',1,NULL),(36,'STS','Stock Trading System','','2015-05-19 11:08:33','A.Cockburn',1,NULL),(37,'OPS','Opder processing system','','2015-05-20 12:11:23','IBM',1,NULL),(38,'BABY','아기용 쇼핑몰','아기용 쇼핑몰','2015-11-03 18:22:00','A class',0,NULL),(39,'WEBS','웹쇼핑몰','','2015-11-04 20:37:02','쿠크다스',0,NULL),(40,'SKP','상품관리','','2015-11-10 11:50:49','SKT',1,NULL),(41,'META','모바일 컨텐츠 관리 시스템','','2015-11-11 15:40:52','SKT',1,NULL),(42,'AMT_T','test atm','','2015-12-21 21:39:47','',0,NULL),(43,'PAY','Online Payment System','','2016-02-11 15:54:46','KDY',1,NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-04 12:46:21
