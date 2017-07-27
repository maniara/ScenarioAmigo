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
-- Table structure for table `verbfrequency`
--

DROP TABLE IF EXISTS `verbfrequency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verbfrequency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `verb` varchar(45) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `frequency` float DEFAULT NULL,
  `type` varchar(1) NOT NULL,
  `totalCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207366 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verbfrequency`
--

LOCK TABLES `verbfrequency` WRITE;
/*!40000 ALTER TABLE `verbfrequency` DISABLE KEYS */;
INSERT INTO `verbfrequency` VALUES (207332,'request',524,0.675258,'u',776),(207333,'check',16,0.019231,'s',832),(207334,'display',546,0.65625,'s',832),(207335,'enter',11,0.014175,'u',776),(207336,'publish',1,0.001202,'s',832),(207337,'eject',2,0.002404,'s',832),(207338,'perform',9,0.010817,'s',832),(207339,'print',4,0.004808,'s',832),(207340,'input',132,0.170103,'u',776),(207341,'save',71,0.085337,'s',832),(207342,'insert',1,0.001289,'u',776),(207343,'select',24,0.030928,'u',776),(207344,'update',2,0.002404,'s',832),(207345,'send',1,0.001202,'s',832),(207346,'validate',3,0.003606,'s',832),(207347,'modify',44,0.056701,'u',776),(207348,'modify',45,0.054087,'s',832),(207349,'retrieve',96,0.115385,'s',832),(207350,'assign',1,0.001202,'s',832),(207351,'retain',1,0.001202,'s',832),(207352,'make',1,0.001202,'s',832),(207353,'verify',8,0.009615,'s',832),(207354,'confirm',19,0.024485,'u',776),(207355,'delete',11,0.014175,'u',776),(207356,'delete',10,0.012019,'s',832),(207357,'remove',8,0.009615,'s',832),(207358,'show',2,0.002404,'s',832),(207359,'remove',5,0.006443,'u',776),(207360,'choose',4,0.005155,'u',776),(207361,'confirm',1,0.001202,'s',832),(207362,'create',1,0.001202,'s',832),(207363,'regist',2,0.002404,'s',832),(207364,'request',1,0.001202,'s',832),(207365,'requst',1,0.001289,'u',776);
/*!40000 ALTER TABLE `verbfrequency` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-31 18:21:58
