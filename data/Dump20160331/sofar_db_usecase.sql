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
-- Table structure for table `usecase`
--

DROP TABLE IF EXISTS `usecase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usecase` (
  `usecaseNum` int(11) NOT NULL AUTO_INCREMENT,
  `usecaseID` varchar(45) NOT NULL,
  `projectID` varchar(45) NOT NULL,
  `usecaseName` varchar(500) NOT NULL,
  `usecaseDescription` varchar(1000) DEFAULT NULL,
  `preCondition` varchar(1000) DEFAULT NULL,
  `postCondition` varchar(1000) DEFAULT NULL,
  `includedOf` varchar(45) DEFAULT NULL,
  `extendsOf` varchar(45) DEFAULT NULL,
  `actor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`usecaseNum`),
  UNIQUE KEY `usecaseNum_UNIQUE` (`usecaseNum`)
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usecase`
--

LOCK TABLES `usecase` WRITE;
/*!40000 ALTER TABLE `usecase` DISABLE KEYS */;
INSERT INTO `usecase` VALUES (19,'atm01','ATM','Validate PIN','System validates customer PIN','ATM is idle, displaying aWelcome message.','Customer PIN has been validated.','null','null','ATM Customer'),(25,'atm04','ATM','Transfer Funds','Customer transfers funds from one valid bank account to another.','','','atm01','null','ATM Customer'),(26,'atm02','ATM','Withdraw Funds','Customer withdraws a specific amount of funds from a valid bank account.','ATM is idle, displaying aWelcome message.','Customer funds have been withdrawn.','null','null','ATM Customer'),(27,'atm03','ATM','Query Accoun','Customer receives the balance of a valid bank account.','ATM is idle, displaying aWelcome message.','Customer account has been queried.','null','null','ATM Customer'),(28,'oss01','OSS','Browse Catalog','Customer browses World Wide Web catalog, views various catalog items from the supplier’s catalog, and selects items from the catalog.','Customer browser is linked to supplier catalog Web site.','System has displayed list of selected catalog items.','null','null','Customer'),(29,'oss02','OSS','Make Order Request','Customer enters an order request to purchase catalog items. The customer’s credit card is checked for validity and sufficient credit to pay for the requested catalog items.','Customer has selected one or more catalog items','System has created a delivery order for the customer.','null','null','Customer'),(32,'oss04','OSS','Confirm Shipment and Bill Customer','Supplier prepares the shipment manually and then confirms that the delivery order is ready for shipment. System notifies customer that order is being shipped. System charges customer’s credit card for purchase and commits inventory items removal.','Inventory items have been reserved for customer delivery','System has committed inventory, charged customer, and sent confirmation.','null','null','Supplier'),(33,'oss03','OSS','Process Delivery Order','Supplier requests a delivery order; system determines that the inventory is available to fulfill the order, and displays the order.','Supplier needs to process a delivery order and a delivery order exists.','System has reserved inventory items for delivery order.','null','null','Supplier'),(34,'prs01','PRS','Login','This use case describes how a user logs into the Payroll System.','','','null','null',''),(35,'prs02','PRS','Select Payment Method','This use case allows an Employee to select a payment method.  The payment method controls how the Employee will be paid.  The Employee may choose to either: pick up his check directly, receive it in the mail, or have it deposited directly into a specified bank account.','','','null','null',''),(37,'prs03','PRS','Add an Employee','','','','null','null',''),(38,'prs04','PRS','Update an Employee','','','','null','null',''),(40,'prs06','PRS','Maintain Timecard','This use case allows the Employee to update and submit timecard information. Hourly and salaried employees must submit weekly timecards recording all hours worked that week and which projects the hours are billed to. An Employee can only make changes to the timecard for the current pay period and before the timecard has been submitted.','','','null','null',''),(42,'prs08','PRS','Create Administrative Report','The use case allows the Payroll Administrator to create either a “Total Hours Worked” or “Pay Year-to-Date” report.','','','null','null',''),(43,'prs09','PRS','Create Employee Report','The use case allows the Employee to create a “Total Hours Worked,” “Total Hours Worked for a Project”, “Vacation/Sick Leave,” or “Total Pay Year-to-Date” report.','','','null','null',''),(44,'prs07','PRS','Submit Timecard','','','','null','null',''),(45,'prs10','PRS','Create a Purchase Order','This use case allows a Commissioned Employee to record and maintain purchase orders.  This includes adding, changing, and deleting purchase orders.  Commissioned employees must record each of their purchase orders in order to receive commissions.','','','null','null',''),(46,'prs11','PRS','Update purchase order','','','','null','null',''),(47,'prs05','PRS','Delete an Employee','','','','null','null',''),(48,'prs12','PRS','Delete a purchase Order','','','','null','null',''),(49,'prs13','PRS','Run Payroll','The use case describes how the payroll is run every Friday and the last working day of the month.','','','null','null',''),(51,'cos01','cos','메뉴선택','카페테리아를 이용하는 사원이 예약 전 메뉴를 주문하기 위해서 메뉴의 내용을 조회하고 선택하거나 선택한 메뉴를 삭제한다. ','','','null','null','사원, 카페 직원'),(52,'cos02','cos','예약','사원은 선택 메뉴에서 저장한 내용을 예약하거나 예약한 내용을 변경할 수 있다. ','','','null','null',''),(56,'cos03','cos','예약 조회','','','','null','null',''),(58,'cos04','cos','예약 정보 변경','','','','null','null',''),(59,'cos05','cos','현장 주문 접수','카페 직원이 PDA를 이용하여 카페테리아를 이용하기 위해 방문한 사원의 주문을 입력받는다. 예약한 사원일 경우 예약 내용을 확인하여 주문한다. ','','','null','null',''),(60,'cos06','cos','주문 상태 조회','','','','null','null',''),(61,'cos07','cos','주문 취소','','','','null','null',''),(62,'cos08','cos','주문 추가','','','','null','null',''),(63,'cos09','cos','배달요청','카페직원은 당일 주문내역을 조회하고 배달준비를 한 다음 배달요청하고 시스템은 배달요원의 상태를 확인한 다음 배달요원에게 배달을 할당하고 SMS 로 배달요원에게 배달을 알린다.','','','null','null',''),(64,'cos10','cos','배달 현황 조회','카페직원은 배달 접수된 내용의 현재 배달 현황을 조회하고 배달요원의 위치를 파악하여 고객에게 SMS로 위치를 알린다.','','','null','null',''),(65,'cos11','cos','배달요원 위치 조회','','','','null','null',''),(66,'cos12','cos','배달요청 내역확인','배달요원이 PDA를 이용하여 배달요청 내역을 확인하고 필요할 경우 배달의 내역을 날짜, 지역별로 정렬할 수 있으며, 배달요청건별 상세 내역을 조회할 수 있다.','','','null','null',''),(67,'cos13','cos','배달확인','배달요원은 메뉴 주문자에게 주문한 메뉴를 배달 후 배달확인을 받도록 한다.','','','null','null',''),(68,'cos14','cos','배달위치 확인','배달요원이 배달 주소를 입력하여 배달 위치를 확인할 수 있으며, 원할 경우 출력도 가능하다','','','null','null',''),(70,'cos16','cos','메뉴조회','','','','null','null',''),(71,'cos17','cos','메뉴수정 ','','','','null','null',''),(72,'cos18','cos','메뉴삭제 ','','','','null','null',''),(73,'cos19','cos','매출내역출력','','','','null','null',''),(74,'cos20','cos','메뉴가격 등록 ','','','','null','null',''),(75,'cos21','cos','메뉴가격 수정','','','','null','null',''),(76,'cos22','cos','메뉴가격 조회 ','','','','null','null',''),(77,'cos23','cos','통계자료조회 ','','','','null','null',''),(78,'cos24','cos','통계자료출력 ','','','','null','null',''),(79,'cos25','cos','음식 비 청구 ','','','','null','null',''),(80,'cos26','cos','식자재 산출','지배인은 예약정보와 재고 정보를 비교하여 필요한 식 자재를 식 자재 재고 관리시스템에 주문 요청한다.','','','null','null','지배인, 식 자재 재고 관리 시스템'),(81,'cos15','cos','메뉴등록','','','','null','null',''),(82,'POS01','POS','Process Sale','','','','null','null',''),(83,'UIS01','UIS','로그인','','','','null','null',''),(84,'UIS02','UIS','암호변경','','','','null','null',''),(85,'UIS03','UIS','로그아웃','','','','null','null',''),(86,'UIS04','UIS','학생등록','','','','null','null',''),(88,'UIS05','UIS','학생 검색','','','','null','null',''),(89,'UIS06','UIS','학생 조회','','','','UIS05','null',''),(90,'UIS07','UIS','학생 수정','','','','UIS06','null',''),(91,'UIS08','UIS','학생정보 삭제','','','','UIS06','null',''),(92,'UIS09','UIS','교수등록','','','','null','null',''),(93,'UIS10','UIS','교수 검색 및 조회','','','','null','null',''),(96,'UIS12','UIS','교수 삭제','','','','null','null',''),(97,'UIS13','UIS','강의 등록','','','','null','null',''),(98,'UIS14','UIS','강의 검색 및 조회','','','','null','null',''),(99,'UIS15','UIS','강의 수정','','','','UIS14','null',''),(100,'UIS16','UIS','강의 삭제','','','','UIS14','null',''),(101,'UIS17','UIS','수강신청 등록','','','','null','null',''),(102,'UIS18','UIS','수강 취소','','','','null','null',''),(103,'UIS19','UIS','성적 조회','','','','null','null',''),(104,'TMS01','TMS','Route train','','','','null','null',''),(106,'TMS02','TMS','Develop new train based on an existing one','','','','null','null',''),(108,'TMS03','TMS','Modify route','','','','null','null',''),(109,'TMS04','TMS','Monitor Train Systems','','','','null','null',''),(110,'TMS05','TMS','Detailed monitoring of a system.','','','','null','null',''),(113,'EFF02','STS','Login','','','','null','null',''),(114,'EFF03','STS','Create Report','','','','null','null',''),(115,'EFF04','STS','Delete report','','','','null','null',''),(116,'EFF05','STS','Print report','','','','null','null',''),(117,'EFF06','STS','Save report','','','','null','null',''),(118,'EFF07','STS','Add item to shopping cart','','','','null','null',''),(119,'EFF08','STS','Buy Stock','','','','null','null',''),(120,'OPS01','OPS','Login','','','','null','null',''),(121,'OPS02','OPS','Place Order','','','','null','null',''),(122,'OPS03','OPS','Return Product','','','','null','null',''),(123,'OPS04','OPS','Search for Order','','','','null','null',''),(124,'OPS05','OPS','Cancel Order','','','','OPS04','null',''),(126,'TMS06','TMS','Request a failure prediction analysis for a system.','','','','null','null',''),(127,'BABY01','BABY','로그인','','','','null','null',''),(128,'BABY02','BABY','순위별 용품 검색','','','','null','null',''),(129,'BABY03','BABY','후기 작성','','','','null','null',''),(130,'BABY04','BABY','회원가입','','','','null','null',''),(132,'WEBS02','WEBS','웹 쇼핑몰 시스템 회원가입','','','','null','null',''),(136,'WEBS','WEBS','상품주문결제','','','','null','null',''),(137,'SKP01','SKP','표준 카테고리 목록 조회','','','','null','null',''),(138,'SKP02','SKP','표준 카테고리 이력 조회','','','','null','null',''),(139,'SKP03','SKP','전시 카테고리 목록 조회','','','','null','null',''),(140,'SKP04','SKP','전시 카테고리 조회','','','','null','null',''),(141,'SKP05','SKP','전시 카테고리 등록','','','','null','null',''),(142,'SKP06','SKP','전시 카테고리 수정','','','','null','null',''),(143,'SKP07','SKP','전시 카테고리 이동/복사','','','','null','null',''),(144,'SKP08','SKP','전시 카테고리 이력 조회','','','','null','null',''),(145,'SKP09','SKP','컨텐츠 메타 목록 조회','','','','null','null',''),(146,'SKP10','SKP','컨텐츠 메타 분류 조회','','','','null','null',''),(147,'SKP11','SKP','컨텐츠 메타 분류 등록','','','','null','null',''),(148,'SKP12','SKP','컨텐츠 메타 분류 수정','','','','null','null',''),(149,'SKP13','SKP','컨텐츠 메타 조회','','','','null','null',''),(150,'SKP14','SKP','컨텐츠 메타 등록','','','','null','null',''),(151,'SKP15','SKP','컨텐츠 메타 수정','','','','null','null',''),(152,'SKP16','SKP','상품 속성 목록 조회','','','','null','null',''),(153,'SKP17','SKP','상품 속성 분류 조회','','','','null','null',''),(154,'SKP18','SKP','상품 속성 분류 등록','','','','null','null',''),(156,'SKP19','SKP','상품 속성 분류 수정','','','','null','null',''),(157,'SKP20','SKP','상품 속성 정보 조회','','','','null','null',''),(158,'SKP21','SKP','상품 속성 정보 등록','','','','null','null',''),(159,'SKP22','SKP','상품 속성 정보 수정','','','','null','null',''),(160,'SKP23','SKP','컨텐츠 마스터 조회','','','','null','null',''),(161,'SKP24','SKP','컨텐츠 마스터 등록','','','','null','null',''),(163,'SKP26','SKP','컨텐츠 마스터 삭제','','','','null','null',''),(164,'SKP27','SKP','컨텐츠 마스터 일괄등록','','','','null','null',''),(165,'SKP28','SKP','컨텐츠 마스터 이력 목록 조회','','','','null','null',''),(166,'SKP29','SKP','컨텐츠 마스터 이력 정보 조회','','','','null','null',''),(167,'SKP30','SKP','상품 마스터 조회','','','','null','null',''),(168,'SKP31','SKP','상품 마스터 등록','','','','null','null',''),(169,'SKP32','SKP','상품마스터 수정','','','','null','null',''),(171,'SKP33','SKP','상품 마스터 삭제','','','','null','null',''),(172,'SKP34','SKP','상품승인 목록 조회','','','','null','null',''),(173,'SKP35','SKP','상품 승인 상세 조회','','','','null','null',''),(174,'SKP36','SKP','상품 승인 상세 수정','','','','null','null',''),(175,'SKP37','SKP','상품 마스터 이력 목록 조회','','','','null','null',''),(176,'SKP38','SKP','상품 마스터 이력 정보 조회','','','','null','null',''),(177,'SKP39','SKP','상품 평 목록 조회','','','','null','null',''),(178,'SKP40','SKP','상품 평 상세 조회','','','','null','null',''),(179,'SKP41','SKP','상품 평 전시 제외','','','','null','null',''),(180,'SKP42','SKP','상품 구매후기 목록 조회','','','','null','null',''),(181,'SKP43','SKP','상품 구매후기 상세 조회','','','','null','null',''),(182,'SKP44','SKP','상품 구매 후기 수정','','','','null','null',''),(183,'SKP25','SKP','컨텐츠 마스터 수정','','','','null','null',''),(184,'SKP45','SKP','상품 문의 목록 조회','','','','null','null',''),(185,'SKP47','SKP','상품 문의 상세 조회','','','','null','null',''),(186,'SKP48','SKP','상품 문의 상세 수정','','','','null','null',''),(187,'META01','META','파트너 조회 ','','','','null','null',''),(189,'META02','META','담당자 조회','','','','null','null',''),(190,'META03','META','레퍼런스 검색','','','','null','null',''),(191,'META04','META','레퍼런스 메타 관리','','','','null','null',''),(192,'META05','META','레퍼런스 메타 장르 관리','','','','null','null',''),(195,'META08','META','영화 레퍼런스 메타 관리','','','','null','null',''),(196,'META09','META','방송 레퍼런스 메타 관리','','','','null','null',''),(197,'META10','META','게임 레퍼런스 메타 관리','','','','null','null',''),(198,'META11','META','레퍼런스 메타데이터 항목 관리','','','','null','null',''),(199,'META12','META','서비스 메타 통합검색','','','','null','null',''),(200,'META13','META','서비스 메타 연계 역할 관리','','','','null','null',''),(201,'META14','META','서비스 메타 장르 관리','','','','null','null',''),(202,'META15','META','레퍼런스 서비스 연계 관리','','','','null','null',''),(203,'META16','META','서비스 간 연계 관리','','','','null','null',''),(204,'META17','META','메타데이터 분류체계 템플릿 관리','','','','null','null',''),(205,'META18','META','분류체계 유형별 서비스 메타데이터 항목 관리','','','','null','null',''),(206,'META19','META','게임 컨텐츠 정보 등록','','','록','null','null',''),(207,'META20','META','스타화보 서비스 메타 관리','','','','null','null',''),(208,'META21','META','UCC 서비스 메타 관리','','','','null','null',''),(209,'META22','META','영화 서비스 메타 관리','','','','null','null',''),(210,'META23','META','방송 서비스 메타 관리','','','','null','null',''),(211,'META24','META','벨소리/컬러링/음악 서비스 메타 관리','','','','null','null',''),(212,'META25','META','템플릿 조회 ','','','','null','null',''),(213,'META26','META','템플릿 등록','','','','null','null',''),(214,'META27','META','템플릿 검증 ','','','','null','null',''),(215,'META28','META','추천페이지 조회','','','','null','null',''),(216,'META29','META','추천페이지 등록','','','','null','null',''),(217,'META30','META','추천페이지 테스트','','','','null','null',''),(218,'META31','META','추천페이지 검증','','','','null','null',''),(219,'META32','META','WAP 페이지 생성','','','','null','null',''),(220,'META33','META','레퍼런스 메타 품질관리 ','','','','null','null',''),(221,'META34','META','서비스 메타 품질관리 ','','','','null','null',''),(222,'META35','META','메타데이터 항목별 데이터 품질관리 ','','','','null','null',''),(223,'META36','META','Verifier Rule 관리 ','','','','null','null',''),(224,'META37','META','공지사항','','','','null','null',''),(225,'META38','META','공지사항 등록','','','','null','null',''),(226,'META39','META','공지사항 수정','','','','null','null',''),(227,'META40','META','공지사항 답글 등록','','','','null','null',''),(228,'META41','META','공지사항 삭제','','','','null','null',''),(229,'META42','META','게시판 조회','','','','null','null',''),(230,'META43','META','게시판 등록','','','','null','null',''),(231,'META44','META','게시판을 수정하는 ','','','','null','null',''),(232,'META45','META','답글등록','','','','null','null',''),(233,'META46','META','게시판 삭제','','','','null','null',''),(234,'META47','META','자료실 조회','','','','null','null',''),(235,'META48','META','자료실 자료 등록','','','','null','null',''),(236,'META49','META','자료실 수정','','','','null','null',''),(237,'META50','META','자료실 자료 삭제','','','','null','null',''),(238,'META51','META','FAQ 조회','','','','null','null',''),(239,'META52','META','FAQ 등록','','','','null','null',''),(240,'META53','META','FAQ 수정','','','','null','null',''),(241,'META54','META','FAQ 답글 등록','','','','null','null',''),(242,'META55','META','FAQ 삭제','','','','null','null',''),(243,'META56','META','Q&A','','','','null','null',''),(244,'META57','META','Q&A 을 등록하는 ','','','','null','null',''),(245,'META58','META','Q&A 을 수정하는 ','','','','null','null',''),(246,'META59','META','Q&A 에 답글을 등록','','','','null','null',''),(247,'META60','META','Q&A을 삭제','','','','null','null',''),(248,'META61','META','메타데이터 등록 통계','','','','null','null',''),(249,'META62','META','메타데이터 등록 통계','','','','null','null',''),(250,'META63','META','메타데이터간 연계 통계','','','','null','null',''),(251,'META64','META','서비스 통계 ','','','','null','null',''),(252,'META65','META','서비스 간 연계 통계','','','','null','null',''),(253,'META66','META','추천페이지 서비스 통계','','','','null','null',''),(254,'META67','META','상품페이지 유효성 통계','','','','null','null',''),(255,'META68','META','인터페이스 통계','','','','null','null',''),(256,'META69','META','화면메뉴설정','','','','null','null',''),(257,'META70','META','화면메뉴를 수정','','','','null','null',''),(258,'META71','META','역할설정','','','','null','null',''),(259,'META72','META','역할&사용자설정 ','','','','null','null',''),(260,'META73','META','역할&화면설정','','','','null','null',''),(261,'META74','META','공통코드 설정','','','','null','null',''),(262,'META75','META','공통코드 수정','','','','null','null',''),(263,'META76','META','배포현황','','','','null','null',''),(264,'META77','META','배포요청이력조회','','','','null','null',''),(265,'META78','META','배포응답이력조회','','','','null','null',''),(266,'META79','META','서비스등록','','','','null','null',''),(267,'META80','META','일배치이력현황','','','','null','null',''),(268,'META81','META','음악메타데이터 배포 ','','','','null','null',''),(269,'META06','META','메타관','','','리','null','null',''),(270,'META07','META','인물 레퍼런스 메타 관리','','','','null','null',''),(272,'AMT_T1','AMT_T','Validate PIN','','','','null','null',''),(274,'POS02','POS','Item sale','','','','null','null',''),(275,'PAY01','PAY','가맹점 가입','','','','null','null',''),(276,'PAY02','PAY','가맹점 정보 조회/변경','','','','null','null',''),(277,'PAY03','PAY','가맹점 탈퇴','','','','null','null',''),(278,'PAY04','PAY','4.3.4	거래 등록','','','','null','null',''),(279,'PAY05','PAY','거래용 QR코드 생성','','','','null','null',''),(280,'PAY06','PAY','전자 화폐 계좌 정보 조회/변경','','','','null','null',''),(281,'PAY07','PAY','4.3.12	전자화폐 계좌개설','','','','null','null',''),(282,'PAY08','PAY','4.3.13	전자화폐 입금','','','','null','null',''),(283,'PAY09','PAY','4.3.14	전자화폐 출금','','','','null','null',''),(284,'PAY10','PAY','제품 구매','','','','null','null',''),(286,'PAY11','PAY','4.3.16	중계점 가입','','','','null','null',''),(287,'PAY12','PAY','4.3.17	중계점 정보 조회/변경','','','','null','null',''),(288,'PAY13','PAY','4.3.18	중계점 탈퇴','','','','null','null',''),(289,'PAY14','PAY','4.3.19	출금 은행 계좌 등록','','','','null','null',''),(290,'PAY15','PAY','회원 가입','','','','null','null',''),(291,'PAY16','PAY','회원탈퇴','','','','null','null',''),(292,'PAY17','PAY','회원정보 조회/변경','','','','null','null',''),(293,'UIS11','UIS','교수 수정',' ',' ',' ','null','null','');
/*!40000 ALTER TABLE `usecase` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-31 18:21:57
