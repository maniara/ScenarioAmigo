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
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `idanswer` int(11) NOT NULL,
  `mode` varchar(45) DEFAULT NULL,
  `questionid` varchar(45) DEFAULT NULL,
  `volunteer` varchar(45) DEFAULT NULL,
  `answer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idanswer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'Miss','3846','maniara5','y'),(2,'Over','4320','maniara5','n'),(3,'Miss','4631','maniara5','n'),(4,'Miss','4303','maniara5','n'),(5,'Miss','4506','maniara5','n'),(6,'Miss','4097','maniara5','n'),(7,'Over','4199','maniara5','n'),(8,'Miss','4434','maniara5','y'),(9,'Miss','3946','maniara5','y'),(10,'Miss','4704','maniara5','y'),(11,'Over','4119','maniara5','n'),(12,'Miss','4354','maniara5','y'),(13,'Miss','4012','maniara5','y'),(14,'Miss','4167','maniara5','y'),(15,'Over','4296','maniara5','y'),(16,'Miss','4569','maniara5','y'),(17,'Miss','3886','maniara5','n'),(18,'Over','4327','maniara5','y'),(19,'Miss','4322','maniara5','n'),(20,'Miss','3989','maniara5','y'),(21,'Miss','4134','maniara5','y'),(22,'Over','4222','maniara5','y'),(23,'Miss','4519','maniara5','y'),(24,'Miss','4694','maniara5','y'),(25,'Miss','4075','maniara5','y'),(26,'Over','4147','maniara5','y'),(27,'Miss','4403','maniara5','y'),(28,'Miss','4059','maniara5','n'),(29,'Miss','4231','maniara5','y'),(30,'Over','4361','maniara5','n'),(31,'Miss','3827','maniara5','y'),(32,'Miss','3867','maniara5','n'),(33,'Over','4098','maniara5','n'),(34,'Miss','4635','maniara5','y'),(35,'Miss','4304','maniara5','y'),(36,'Miss','4588','maniara5','y'),(37,'Miss','4119','maniara5','n'),(38,'Over','4215','maniara5','y'),(39,'Miss','4740','maniara5','y'),(40,'Miss','3965','maniara5','y'),(41,'Miss','4725','maniara5','y'),(42,'Over','4134','maniara5','y'),(43,'Miss','4366','maniara5','y'),(44,'Miss','4024','maniara5','y'),(45,'Miss','4172','maniara5','n'),(46,'Over','4070','maniara5','n'),(47,'Miss','4571','maniara5','n'),(48,'Miss','3897','maniara5','n'),(49,'Over','4342','maniara5','n'),(50,'Over','4117','maniara5','n'),(51,'Miss','4326','maniara5','n'),(52,'Miss','4020','maniara5','y'),(53,'Miss','4154','maniara5','y'),(54,'Over','4288','maniara5','y'),(55,'Miss','4523','maniara5','y'),(56,'Miss','4482','maniara5','n'),(57,'Miss','4087','maniara5','n'),(58,'Over','4177','maniara5','n'),(59,'Miss','4417','maniara5','n'),(60,'Miss','4609','maniara5','y'),(61,'Miss','4280','maniara5','n'),(62,'Over','4279','maniara5','n'),(63,'Miss','3834','maniara5','n'),(64,'Miss','3862','maniara5','n'),(65,'Over','4097','maniara5','n'),(66,'Miss','4634','maniara5','y'),(67,'Miss','4301','maniara5','n'),(68,'Miss','4509','maniara5','n'),(69,'Miss','4095','maniara5','n'),(70,'Over','4203','maniara5','n'),(71,'Miss','4430','maniara5','y'),(72,'Miss','3956','maniara5','n'),(73,'Miss','4710','maniara5','y'),(74,'Over','4126','maniara5','n'),(75,'Miss','4351','maniara5','y'),(76,'Miss','4018','maniara5','n'),(77,'Miss','4165','maniara5','n'),(78,'Over','4064','maniara5','y'),(79,'Miss','4567','maniara5','y'),(80,'Miss','3887','maniara5','y'),(81,'Over','4341','maniara5','n'),(82,'Over','4367','maniara5','y'),(83,'Miss','4321','maniara5','y'),(84,'Miss','3986','maniara5','y'),(85,'Miss','4148','maniara5','n'),(86,'Over','4377','maniara5','n'),(87,'Miss','4522','maniara5','n'),(88,'Miss','4483','maniara5','y'),(89,'Miss','4081','maniara5','y'),(90,'Over','4167','maniara5','n'),(91,'Miss','4414','maniara5','y'),(92,'Miss','4608','maniara5','n'),(93,'Miss','4267','maniara5','y'),(94,'Over','4278','maniara5','n'),(95,'Miss','3828','maniara5','n'),(96,'Miss','3869','maniara5','y'),(97,'Over','4101','maniara5','n'),(98,'Miss','4663','maniara5','n'),(99,'Miss','4320','maniara5','n'),(100,'Miss','4592','maniara5','n'),(101,'Miss','4133','maniara5','y'),(102,'Over','4223','maniara5','y'),(103,'Miss','4747','maniara5','n'),(104,'Miss','4688','maniara5','y'),(105,'Miss','4069','maniara5','n'),(106,'Over','4150','maniara5','y'),(107,'Miss','4382','maniara5','y'),(108,'Miss','4048','maniara5','y'),(109,'Miss','4208','maniara5','n'),(110,'Over','4075','maniara5','y'),(111,'Miss','4679','maniara5','n'),(112,'Miss','3919','maniara5','y'),(113,'Over','4340','maniara5','n'),(114,'Over','4122','maniara5','n'),(115,'Miss','4339','maniara5','y'),(116,'Miss','4016','maniara5','y'),(117,'Miss','4158','maniara5','n'),(118,'Over','4285','maniara5','y'),(119,'Miss','4536','maniara5','y'),(121,'Miss','4093','maniara5','y'),(122,'Over','4179','maniara5','n'),(123,'Miss','4428','maniara5','n'),(124,'Miss','4656','maniara5','n'),(125,'Miss','4283','maniara5','y'),(126,'Over','4319','maniara5','n'),(127,'Miss','3849','maniara5','y'),(128,'Miss','4506','maniara5','y'),(129,'Miss','4666','maniara5','n');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
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
