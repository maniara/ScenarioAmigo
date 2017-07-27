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
-- Table structure for table `verbcluster`
--

DROP TABLE IF EXISTS `verbcluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verbcluster` (
  `clusterID` int(11) NOT NULL AUTO_INCREMENT,
  `representives` varchar(45) NOT NULL,
  `verbs` varchar(1000) DEFAULT NULL,
  `iscustom` tinyint(1) NOT NULL,
  `subjectType` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`clusterID`)
) ENGINE=InnoDB AUTO_INCREMENT=3105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verbcluster`
--

LOCK TABLES `verbcluster` WRITE;
/*!40000 ALTER TABLE `verbcluster` DISABLE KEYS */;
INSERT INTO `verbcluster` VALUES (2876,'delete','delete;remove',1,'s'),(2877,'finish','finish;complete;exit',1,'s'),(2878,'insert','insert;input;enter',1,'u'),(2888,'save','save;store;record',1,'s'),(2893,'display','display;show;present;print',1,'s'),(2895,'alert','notify;alert;alarm',1,'s'),(3092,'confirm','confirm',0,'u'),(3093,'request','request;submit;click',0,'u'),(3094,'modify','modify;change;update',0,'u'),(3095,'insert','insert;input;enter;requst',0,'u'),(3096,'choose','choose;select',0,'u'),(3097,'delete','delete;remove',0,'u'),(3098,'modify','modify;update;change',0,'s'),(3099,'search','search;retrieve',0,'s'),(3100,'display','display;show;present;print;publish;create;make',0,'s'),(3101,'save','save;store;record;regist;request;retain;eject;send;assign',0,'s'),(3102,'perform','perform',0,'s'),(3103,'check','check;verify;validate;confirm',0,'s'),(3104,'delete','delete;remove',0,'s');
/*!40000 ALTER TABLE `verbcluster` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-04 12:46:20
