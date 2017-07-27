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
) ENGINE=InnoDB AUTO_INCREMENT=219525 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verbfrequency`
--

LOCK TABLES `verbfrequency` WRITE;
/*!40000 ALTER TABLE `verbfrequency` DISABLE KEYS */;
INSERT INTO `verbfrequency` VALUES (219490,'request',553,0.676039,'u',818),(219491,'check',17,0.019473,'s',873),(219492,'display',575,0.658648,'s',873),(219493,'enter',7,0.008557,'u',818),(219494,'publish',1,0.001145,'s',873),(219495,'eject',2,0.002291,'s',873),(219496,'perform',10,0.011455,'s',873),(219497,'print',4,0.004582,'s',873),(219498,'input',141,0.172372,'u',818),(219499,'save',72,0.082474,'s',873),(219500,'insert',1,0.001222,'u',818),(219501,'select',24,0.02934,'u',818),(219502,'update',2,0.002291,'s',873),(219503,'send',1,0.001145,'s',873),(219504,'validate',3,0.003436,'s',873),(219505,'modify',48,0.05868,'u',818),(219506,'modify',49,0.056128,'s',873),(219507,'retrieve',96,0.109966,'s',873),(219508,'assign',1,0.001145,'s',873),(219509,'retain',1,0.001145,'s',873),(219510,'make',1,0.001145,'s',873),(219511,'verify',6,0.006873,'s',873),(219512,'confirm',19,0.023227,'u',818),(219513,'delete',14,0.017115,'u',818),(219514,'delete',14,0.016037,'s',873),(219515,'remove',8,0.009164,'s',873),(219516,'show',2,0.002291,'s',873),(219517,'remove',6,0.007335,'u',818),(219518,'search',3,0.003436,'s',873),(219519,'choose',4,0.00489,'u',818),(219520,'confirm',1,0.001145,'s',873),(219521,'create',1,0.001145,'s',873),(219522,'regist',2,0.002291,'s',873),(219523,'request',1,0.001145,'s',873),(219524,'requst',1,0.001222,'u',818);
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

-- Dump completed on 2016-05-04 12:46:21
