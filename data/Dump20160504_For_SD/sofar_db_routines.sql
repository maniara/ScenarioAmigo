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
-- Temporary view structure for view `training_use_case`
--

DROP TABLE IF EXISTS `training_use_case`;
/*!50001 DROP VIEW IF EXISTS `training_use_case`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `training_use_case` AS SELECT 
 1 AS `usecaseNum`,
 1 AS `usecaseID`,
 1 AS `projectID`,
 1 AS `usecaseName`,
 1 AS `usecaseDescription`,
 1 AS `preCondition`,
 1 AS `postCondition`,
 1 AS `includedOf`,
 1 AS `extendsOf`,
 1 AS `actor`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `training_basicflow_sentence`
--

DROP TABLE IF EXISTS `training_basicflow_sentence`;
/*!50001 DROP VIEW IF EXISTS `training_basicflow_sentence`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `training_basicflow_sentence` AS SELECT 
 1 AS `sentenceNum`,
 1 AS `projectID`,
 1 AS `usecaseID`,
 1 AS `sentenceOrder`,
 1 AS `sentenceContents`,
 1 AS `flowID`,
 1 AS `sentenceType`,
 1 AS `sentenceSeq`,
 1 AS `isRepeatable`,
 1 AS `isOptional`,
 1 AS `mainVerb`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `training_use_case`
--

/*!50001 DROP VIEW IF EXISTS `training_use_case`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `training_use_case` AS select `usecase`.`usecaseNum` AS `usecaseNum`,`usecase`.`usecaseID` AS `usecaseID`,`usecase`.`projectID` AS `projectID`,`usecase`.`usecaseName` AS `usecaseName`,`usecase`.`usecaseDescription` AS `usecaseDescription`,`usecase`.`preCondition` AS `preCondition`,`usecase`.`postCondition` AS `postCondition`,`usecase`.`includedOf` AS `includedOf`,`usecase`.`extendsOf` AS `extendsOf`,`usecase`.`actor` AS `actor` from `usecase` where `usecase`.`projectID` in (select `project`.`projectID` from `project` where (`project`.`forTraining` = '1')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `training_basicflow_sentence`
--

/*!50001 DROP VIEW IF EXISTS `training_basicflow_sentence`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `training_basicflow_sentence` AS select `sentence`.`sentenceNum` AS `sentenceNum`,`sentence`.`projectID` AS `projectID`,`sentence`.`usecaseID` AS `usecaseID`,`sentence`.`sentenceOrder` AS `sentenceOrder`,`sentence`.`sentenceContents` AS `sentenceContents`,`sentence`.`flowID` AS `flowID`,`sentence`.`sentenceType` AS `sentenceType`,`sentence`.`sentenceSeq` AS `sentenceSeq`,`sentence`.`isRepeatable` AS `isRepeatable`,`sentence`.`isOptional` AS `isOptional`,`sentence`.`mainVerb` AS `mainVerb` from `sentence` where (`sentence`.`projectID` in (select `project`.`projectID` from `project` where (`project`.`forTraining` = 1)) and `sentence`.`flowID` in (select `flow`.`flowID` from `flow` where (`flow`.`isAlternative` = 'N'))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-04 12:46:22
