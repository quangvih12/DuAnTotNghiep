CREATE DATABASE  IF NOT EXISTS `datn` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `datn`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: datn
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chuc_vu`
--

DROP TABLE IF EXISTS `chuc_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuc_vu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuc_vu`
--

LOCK TABLES `chuc_vu` WRITE;
/*!40000 ALTER TABLE `chuc_vu` DISABLE KEYS */;
INSERT INTO `chuc_vu` VALUES (1,'CV1','2023-11-10','2023-10-24','Nhân viên',1),(2,'CV3','2023-11-10','2023-10-24','Giám đốc',1),(3,'CV4','2023-11-10','2023-10-24','Nhân viên Maketing',1);
/*!40000 ALTER TABLE `chuc_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `noi_dung` varchar(10000) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlxsdbnoq5ojkkll758ewqyfbg` (`id_san_pham_chi_tiet`),
  KEY `FKd8074xyo14wtlxe0uoqi3492j` (`id_user`),
  CONSTRAINT `FKd8074xyo14wtlxe0uoqi3492j` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKlxsdbnoq5ojkkll758ewqyfbg` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danh_sach_yeu_thich`
--

DROP TABLE IF EXISTS `danh_sach_yeu_thich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `danh_sach_yeu_thich` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dd115i50w4aw5s2ciyepw3gx` (`id_san_pham_chi_tiet`),
  KEY `FKskwt6maa75a67qfpgigo1kwam` (`id_user`),
  CONSTRAINT `FK5dd115i50w4aw5s2ciyepw3gx` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`),
  CONSTRAINT `FKskwt6maa75a67qfpgigo1kwam` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danh_sach_yeu_thich`
--

LOCK TABLES `danh_sach_yeu_thich` WRITE;
/*!40000 ALTER TABLE `danh_sach_yeu_thich` DISABLE KEYS */;
/*!40000 ALTER TABLE `danh_sach_yeu_thich` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dia_chi`
--

DROP TABLE IF EXISTS `dia_chi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dia_chi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(10000) DEFAULT NULL,
  `tinh_thanh` int DEFAULT NULL,
  `quan_huyen` int DEFAULT NULL,
  `phuong_xa` varchar(255) DEFAULT NULL,
  `loai_dia_chi` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbfv0wiqy3kyntfe72a952m3ym` (`id_user`),
  CONSTRAINT `FKbfv0wiqy3kyntfe72a952m3ym` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_chi`
--

LOCK TABLES `dia_chi` WRITE;
/*!40000 ALTER TABLE `dia_chi` DISABLE KEYS */;
INSERT INTO `dia_chi` VALUES  (1,'Số 1, Hàng Than',269,2264,'90816','Công ty','2023-09-10','2023-04-03',1,2),
                              (2,'Số 3, Mỹ Đình',268,2194,'220714','Công ty','2023-10-10','2023-08-23',1,2),
                              (3,'Thọ Hải, Thọ Xuân',249,1768,'190211','Nhà riêng','2023-07-10','2023-05-13',1,3);
/*!40000 ALTER TABLE `dia_chi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gio_hang`
--

DROP TABLE IF EXISTS `gio_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gio_hang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` datetime DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpvcogldmug8cho628f0m28em0` (`id_user`),
  CONSTRAINT `FKpvcogldmug8cho628f0m28em0` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gio_hang`
--

LOCK TABLES `gio_hang` WRITE;
/*!40000 ALTER TABLE `gio_hang` DISABLE KEYS */;
/*!40000 ALTER TABLE `gio_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gio_hang_chi_tiet`
--

DROP TABLE IF EXISTS `gio_hang_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gio_hang_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_gia` decimal(20,0) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_gio_hang` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkt2s807w7uf9vgc64x6r3cl2n` (`id_gio_hang`),
  KEY `FKjrkuss0lgfn76maw426puheeu` (`id_san_pham_chi_tiet`),
  CONSTRAINT `FKjrkuss0lgfn76maw426puheeu` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`),
  CONSTRAINT `FKkt2s807w7uf9vgc64x6r3cl2n` FOREIGN KEY (`id_gio_hang`) REFERENCES `gio_hang` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gio_hang_chi_tiet`
--

LOCK TABLES `gio_hang_chi_tiet` WRITE;
/*!40000 ALTER TABLE `gio_hang_chi_tiet` DISABLE KEYS */;
/*!40000 ALTER TABLE `gio_hang_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hinh_thuc_giao_hang` int DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_nhan` datetime DEFAULT NULL,
  `ngay_ship` datetime DEFAULT NULL,
  `ngay_sua` datetime DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL,
  `ngay_thanh_toan` datetime DEFAULT NULL,
  `ten_nguoi_nhan` varchar(255) DEFAULT NULL,
  `tien_sau_khi_giam_gia` decimal(20,0) DEFAULT NULL,
  `tien_ship` decimal(20,0) DEFAULT NULL,
  `tong_tien` decimal(20,0) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_dia_chi_sdt` int DEFAULT NULL,
  `id_phuong_thuc_thanh_toan` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3hpv2auavm1xw5cm0m7f0g8eu` (`id_dia_chi_sdt`),
  KEY `FKi74bvrg7meqbrhpwk8ff6o8xi` (`id_phuong_thuc_thanh_toan`),
  KEY `FKm5hgwxf6p05vqdw5ptm19p0lj` (`id_user`),
  CONSTRAINT `FK3hpv2auavm1xw5cm0m7f0g8eu` FOREIGN KEY (`id_dia_chi_sdt`) REFERENCES `dia_chi` (`id`),
  CONSTRAINT `FKi74bvrg7meqbrhpwk8ff6o8xi` FOREIGN KEY (`id_phuong_thuc_thanh_toan`) REFERENCES `phuong_thuc_thanh_toan` (`id`),
  CONSTRAINT `FKm5hgwxf6p05vqdw5ptm19p0lj` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_chi_tiet`
--

DROP TABLE IF EXISTS `hoa_don_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_gia` decimal(20,0) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_hoa_don` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3igy4tfvmm2b6mypd176k4948` (`id_hoa_don`),
  KEY `FKmm0mt4gwrghnll65uq9b5b4ox` (`id_san_pham_chi_tiet`),
  CONSTRAINT `FK3igy4tfvmm2b6mypd176k4948` FOREIGN KEY (`id_hoa_don`) REFERENCES `hoa_don` (`id`),
  CONSTRAINT `FKmm0mt4gwrghnll65uq9b5b4ox` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_chi_tiet`
--

LOCK TABLES `hoa_don_chi_tiet` WRITE;
/*!40000 ALTER TABLE `hoa_don_chi_tiet` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoa_don_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(50000) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa4gihn9sp5ngnuwb70tce2nxx` (`id_san_pham_chi_tiet`),
  CONSTRAINT `FKa4gihn9sp5ngnuwb70tce2nxx` FOREIGN KEY (`id_san_pham_chi_tiet`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/klIZba_MG_3349.jpg','IM1','2023-05-05','2023-04-13',1,1),(2,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/TLXYEi_MG_3350.jpg','IM2','2023-05-05','2023-04-13',1,1),(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/NOYMt4_MG_3352.jpg','IM3','2023-05-05','2023-04-13',1,1),(4,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gqyBwx_MG_3348.jpg','IM4','2023-05-05','2023-04-13',1,1),(5,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/b4hEP5SwGTU0royal-m139-v7.jpg','IM5','2023-05-05','2023-04-13',1,2),(6,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/3IqhWcroyal-m139-v7-1.jpg','IM6','2023-05-05','2023-04-13',1,2),(7,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hD1uIXroyal-m139-v7-2.jpg','IM7','2023-05-05','2023-04-13',1,2),(8,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/u50Ujeroyal-m139-v7-3.jpg','IM8','2023-05-05','2023-04-13',1,2),(9,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/RsOSbzroyal-m139-v7-4.jpg','IM9','2023-05-05','2023-04-13',1,2),(10,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xKHMALIMG_5434.jpg','IM10','2023-05-05','2023-04-13',1,3),(11,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/OSknJrIMG_5435.jpg','IM11','2023-05-05','2023-04-13',1,3),(12,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gJnFjBIMG_5433.jpg','IM12','2023-05-05','2023-04-13',1,3),(13,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/ikwHUAIMG_5436.jpg','IM13','2023-05-05','2023-04-13',1,3),(14,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cDpyPNIMG_5437.jpg','IM14','2023-05-05','2023-04-13',1,3),(15,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg','IM15','2023-05-05','2023-04-13',1,4),(16,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4SNrqJ_MG_9657.jpg','IM16','2023-05-05','2023-04-13',1,4),(17,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qKPAJv_MG_9659.jpg','IM17','2023-05-05','2023-04-13',1,4);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyen_mai`
--

DROP TABLE IF EXISTS `khuyen_mai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyen_mai` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dieu_kien` decimal(20,0) DEFAULT NULL,
  `gia_tri_giam` decimal(20,0) DEFAULT NULL,
  `giam_toi_da` decimal(20,0) DEFAULT NULL,
  `kieu_giam_gia` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` datetime DEFAULT NULL,
  `ngay_tao` datetime DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyen_mai`
--

LOCK TABLES `khuyen_mai` WRITE;
/*!40000 ALTER TABLE `khuyen_mai` DISABLE KEYS */;
INSERT INTO `khuyen_mai` VALUES (1,NULL,5,NULL,NULL,'KM1','giảm 5% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-10','2023-10-24',100,'giảm 5%','2023-11-15','2023-11-23',1),
(2,NULL,15,NULL,NULL,'KM2','giảm 15% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-10-10','2023-09-24',80,'giảm 15%','2023-10-15','2023-10-23',1),
(3,NULL,20,NULL,NULL,'KM2','giảm 20% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-07-10','2023-06-24',60,'giảm 20%','2023-07-15','2023-07-23',0),
(4,NULL,25,NULL,NULL,'KM4','giảm 25% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-09-10','2023-08-24',400,'giảm 25%','2023-09-15','2023-09-23',1);
/*!40000 ALTER TABLE `khuyen_mai` ENABLE KEYS */;
UNLOCK TABLES;




--
-- Table structure for table `loai`
--

DROP TABLE IF EXISTS `loai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai`
--

LOCK TABLES `loai` WRITE;
/*!40000 ALTER TABLE `loai` DISABLE KEYS */;
INSERT INTO `loai` VALUES (1,'L1','2023-09-10','2023-09-05','3/4 đầu',1),(2,'L2','2023-09-15','2023-09-10','Fullrace',1),(3,'L3','2023-09-23','2023-09-19','1/2 đầu',0),(4,'L4','2023-09-24','2023-09-20','Xe đạp',0),(5,'L5','2023-08-10','2023-07-25','Trẻ em',1),(6,'L6','2023-07-19','2023-07-15','Lật cằm',1);
/*!40000 ALTER TABLE `loai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mau_sac`
--

DROP TABLE IF EXISTS `mau_sac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mau_sac` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mau_sac`
--

LOCK TABLES `mau_sac` WRITE;
/*!40000 ALTER TABLE `mau_sac` DISABLE KEYS */;
INSERT INTO `mau_sac` VALUES (1,'MS1',NULL,'2023-05-05','2023-04-13','Vàng',1),(2,'MS2',NULL,'2023-05-05','2023-04-13','Đen',1),(3,'MS3',NULL,'2023-05-05','2023-04-13','Trắng',1),(4,'MS4',NULL,'2023-05-05','2023-04-13','Xanh',1),(5,'MS5',NULL,'2023-05-05','2023-04-13','Đỏ',1),(6,'MS6',NULL,'2023-05-05','2023-04-13','Hồng',1),(7,'MS7',NULL,'2023-05-05','2023-04-13','Rêu',1),(8,'MS8',NULL,'2023-05-05','2023-04-13','Sữa',1);
/*!40000 ALTER TABLE `mau_sac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mau_sac_ctsp`
--

DROP TABLE IF EXISTS `mau_sac_ctsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mau_sac_ctsp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(50000) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_mau_sac` int DEFAULT NULL,
  `id_ctsp` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK90wg4hfm5w6pfpgstcvi8m0mb` (`id_mau_sac`),
  KEY `FKr94uquvys8b1ish8yirhj3ah5` (`id_ctsp`),
  CONSTRAINT `FK90wg4hfm5w6pfpgstcvi8m0mb` FOREIGN KEY (`id_mau_sac`) REFERENCES `mau_sac` (`id`),
  CONSTRAINT `FKr94uquvys8b1ish8yirhj3ah5` FOREIGN KEY (`id_ctsp`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mau_sac_ctsp`
--

LOCK TABLES `mau_sac_ctsp` WRITE;
/*!40000 ALTER TABLE `mau_sac_ctsp` DISABLE KEYS */;
INSERT INTO `mau_sac_ctsp` VALUES (1,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg',NULL,'2023-05-05','2023-04-13',1,1,1),
(2,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P0jXDB_MG_3354.jpg',NULL,'2023-05-05','2023-04-13',1,2,1),
(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K6uk81_MG_3354-muc.jpg','Xanh mực','2023-05-05','2023-04-13',1,4,1),
(4,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/0IAa3e_MG_0585.jpg','Trắng bóng','2023-05-05','2023-04-13',1,3,1),
(5,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/ahK7SV_MG_0593.jpg','Đen bóng','2023-05-05','2023-04-13',1,2,1),
(6,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/FofRWKroyal-m139-v1-5.jpg','V1-Đen','2023-05-05','2023-04-13',1,2,2),
(7,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5JoMl9royal-m139-v2-2.jpg','V2-Đen','2023-05-05','2023-04-13',1,2,2),
(8,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg','Trắng bóng','2023-05-05','2023-04-13',1,3,2),
(9,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4eOVB1royal-m139-v9.jpg','V1-Trắng','2023-05-05','2023-04-13',1,3,2),
(10,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xKHMALIMG_5434.jpg','Xanh ngọc','2023-05-05','2023-04-13',1,4,3),
(11,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/AfmhwnIMG_5283.jpg','Đỏ đô','2023-05-05','2023-04-13',1,5,3),
(12,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg','Sữa bóng','2023-05-05','2023-04-13',1,8,3),
(13,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/PeCI1GIMG_3358.jpg','Đen mờ','2023-05-05','2023-04-13',1,2,3),(14,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg','Đen ','2023-05-05','2023-04-13',1,2,4);
/*!40000 ALTER TABLE `mau_sac_ctsp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phuong_thuc_thanh_toan`
--

DROP TABLE IF EXISTS `phuong_thuc_thanh_toan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phuong_thuc_thanh_toan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phuong_thuc_thanh_toan`
--

LOCK TABLES `phuong_thuc_thanh_toan` WRITE;
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` DISABLE KEYS */;
INSERT INTO `phuong_thuc_thanh_toan` VALUES (1,'TT1','2022-09-07','2022-08-06','Online',1),(2,'TT2','2022-09-07','2022-08-06','Tiền mặt',1);
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham`
--

DROP TABLE IF EXISTS `san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dem_lot` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `quai_deo` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `anh` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_loai` int DEFAULT NULL,
  `id_thuong_hieu` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ietyty0m29mftr3yaff0v4wb` (`id_loai`),
  KEY `FKng0be3yah8qweo3tnmxm9pnrw` (`id_thuong_hieu`),
  CONSTRAINT `FK3ietyty0m29mftr3yaff0v4wb` FOREIGN KEY (`id_loai`) REFERENCES `loai` (`id`),
  CONSTRAINT `FKng0be3yah8qweo3tnmxm9pnrw` FOREIGN KEY (`id_thuong_hieu`) REFERENCES `thuong_hieu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
INSERT INTO `san_pham` (id,dem_lot,anh,ma,mo_ta,ngay_sua,ngay_tao,quai_deo,ten,trang_thai,id_loai,id_thuong_hieu) VALUES 
(1,'Vải nâu đất, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg','SP1','Được thiết kế theo phong cách hiện đại với những đường nét bo tròn đầy tinh tế gọn gàng đã khiến ngoại hình của Royal M20C trở nên tinh tế gọn hàng hơn rất nhiều so với những thế hệ mũ bảo hiểm ¾ trước đây. Một nguyên nhân khiến cho mọi người thường chọn mũ bảo hiểm nửa đầu thay vì nón fullface hoặc mũ ¾ chính là vì sự gọn gàng, tiện lợi trong sử dụng, bảo quản, nhưng có thể thấy, với Royal M20C, bạn hoàn toàn yên tâm về sự gọn gàng, tiện lợi trong khi sử dụng','2023-09-10','2023-09-05','Có thể tháo rời','ROYAL M20D',1,1,9),
(2,'Vải nâu đất, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P0jXDB_MG_3354.jpg','SP2','Thiết kế kính âm mang tới sự khác biệt của Royal M139 so với các sản phẩm mũ bảo hiểm khác. Khi được kéo lên, lớp kính bảo vệ sẽ ẩn mình vào bên trong nón, biến Royal M139 thành chiếc mũ bảo hiểm  ¾  đầy năng động, phong cách. Khi kéo xuống, lớp kính chắc chắn, trong suốt sẽ bảo vệ bạn khỏi khói bụi, dị vật mà vẫn mang đến cho bạn tầm nhìn thoáng đãng và  phong cách thời trang trên mọi cung đường.','2023-09-15','2023-09-10','có thể tháo rời','ROYAL M139',1,1,9),
(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K6uk81_MG_3354-muc.jpg','Vải đen , có thể tháo rời','SP3','Mũ bảo hiểm Royal M268 siêu phẩm mũ ¾ hai kính mới nhất của Royal Helmet. Giữ nguyên những đặc điểm“đẳng cấp” làm nên thương hiệu Royal, Royal M268 vẫn biết làm nổi bật mình với thiết kế 2 kính độc đáo mang lại những trải nghiệm thú vị, tươi mới cho người đội!','2023-09-23','2023-09-19','có thể tháo rời','ROYAL M268 TRƠN - 2 KÍNH',0,1,9),
(4,'Vải đen , có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/0IAa3e_MG_0585.jpg','SP4','Mũ bảo hiểm Royal M134C do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.','2023-09-10','2023-09-05','có thể tháo rời','ROYAL M134C',1,1,9),
(5,'Vải nâu đất, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/ahK7SV_MG_0593.jpg','SP5','Mũ bảo hiểm nguyên đầu Andes Luxury 810B là dòng mũ bảo hiểm fullface cao cấp cải tiến mới của Andes. Nhờ thiết kế nguyên khối bền chắc, Andes Luxury 810B có thể bao bọc toàn bộ phần đầu như mặt, tai, gáy và cả cằm, bền bỉ bảo vệ người đội mũ trong suốt chặng đường dài','2023-07-19','2023-07-15','có thể tháo rời','Andes Luxury 810B',1,2,5),
(6,'Vải đen, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/FofRWKroyal-m139-v1-5.jpg','SP6','Mũ bảo hiểm nguyên đầu Andes 3S555 là dòng mũ bảo hiểm fullface cao cấp cải tiến mới của Andes. Nhờ thiết kế nguyên khối bền chắc, Andes 3S555 có thể bao bọc toàn bộ phần đầu như mặt, tai, gáy và cả cằm, bền bỉ bảo vệ người đội mũ trong suốt chặng đường dài.','2023-08-10','2023-07-25','có thể tháo rời','Andes 3S555',0,2,5),
(7,'Vải nâu đất, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5JoMl9royal-m139-v2-2.jpg','SP7','Mũ bảo hiểm Royal M138B do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.','2023-09-10','2023-09-05','có thể tháo rời','ROYAL M138B',1,2,9),
(8,'Vải nâu đất, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg','SP8','là dòng nón bảo hiểm fullface 1 kính cao cấp mới nhất.Với mức giá rất cạnh tranh và chất lượng cao vượt trội. Đây là mẫu fullface định hướng xuất khẩu nên chất lượng khá tốt','2023-09-10','2023-09-05','có thể tháo rời','ROYAL M141K',0,2,9),
(10,'Vải lưới 3 lớp, có thể tháo rời','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4eOVB1royal-m139-v9.jpg','SP10','Royal M266 là dòng nón bảo hiểm fullface 2 kính mới ra mắt của nón Royal. Đây là nón fullface có thể nói rẻ nhất Việt Nam','2023-09-15','2023-09-10','có thể tháo rời','ROYAL M226 DESIGN',1,2,9),
(11,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/AfmhwnIMG_5283.jpg','Vải nâu đất, có thể tháo rời','SP11','Mũ bảo hiểm nửa đầu có kính Andes 3S126 có thiết kế đơn giản, bắt mắt với bộ sưu tập màu, tem hơn 20 mẫu cho cả nhám lẫn bóng. Andes 3S126 còn được gọi là chiếc nón thanh xuân bởi sự đa dạng và trẻ trung ở thiết kế, đây chắc chắn là sản phẩm thú vị dành cho những quý khách hàng yêu thích sự nổi bật.','2023-09-15','2023-09-10',NULL,'Andes 3S126',1,3,5),
(12,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg','Vải nâu đất, có thể tháo rời','SP12','Giữ nguyên những đặc điểm“đẳng cấp” làm nên thương hiệu Royal, Royal 152K vẫn biết làm nổi bật mình với hệ thống thông gió \"xịn sò\" thiết kế nổi bật với các đường nét góc cạnh, tinh tế được tính toán cẩn thận.','2023-09-15','2023-09-10',NULL,'ROYAL 152K',1,3,9),
(13,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/PeCI1GIMG_3358.jpg','Vải đen , có thể tháo rời','SP13','Với vẻ ngoài thời trang, chất lượng hoàn hảo cùng những trải nghiệm thú vị, Mũ bảo hiểm Royal M153LT đang rất được mọi người ưa thích.','2023-09-15','2023-09-10',NULL,'ROYAL 153LT',0,3,9);
/*!40000 ALTER TABLE `san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham_chi_tiet`
--

DROP TABLE IF EXISTS `san_pham_chi_tiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham_chi_tiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gia_ban` decimal(20,0) DEFAULT NULL,
  `gia_nhap` decimal(20,0) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong_ton` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
 `gia_sau_giam` decimal(20,0) DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  `id_trong_luong` int DEFAULT NULL,
  `id_vat_lieu` int DEFAULT NULL,
  `id_khuyen_mai` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmby561odp360b0sfqx4mmarja` (`id_san_pham`),
  KEY `FKdrjkavdharm2dd7tsh3wywlr1` (`id_trong_luong`),
  KEY `FK4sre5v1q6gi2fhpeogsgd71bc` (`id_vat_lieu`),
  KEY `FKe781xl7r1mbttpsw0k5tydfky` (`id_khuyen_mai`),
  CONSTRAINT `FKe781xl7r1mbttpsw0k5tydfky` FOREIGN KEY (`id_khuyen_mai`) REFERENCES `khuyen_mai` (`id`),
  CONSTRAINT `FK4sre5v1q6gi2fhpeogsgd71bc` FOREIGN KEY (`id_vat_lieu`) REFERENCES `vat_lieu` (`id`),
  CONSTRAINT `FKdrjkavdharm2dd7tsh3wywlr1` FOREIGN KEY (`id_trong_luong`) REFERENCES `trong_luong` (`id`),
  CONSTRAINT `FKmby561odp360b0sfqx4mmarja` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham_chi_tiet`
--

LOCK TABLES `san_pham_chi_tiet` WRITE;
/*!40000 ALTER TABLE `san_pham_chi_tiet` DISABLE KEYS */;
INSERT INTO `san_pham_chi_tiet` VALUES 
(1,780000,700000,'2023-05-05','2023-04-13',50,1,null,1,1,1,null),
(2,730000,600000,'2023-05-05','2023-04-13',100,1,null,2,1,1,null),
(3,6400000,500000,'2023-05-05','2023-04-13',25,1,null,3,1,1,null),
(4,540000,400000,'2023-05-05','2023-04-13',30,1,null,4,2,1,null),
(5,750000,600000,'2023-05-05','2023-04-13',120,1,null,5,1,1,null);
/*!40000 ALTER TABLE `san_pham_chi_tiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'S1','56-57cm','2023-11-10','2023-10-24','L',1),(2,'S2','58-59cm','2023-11-10','2023-10-24','XL',1),(3,'S3','52-54cm','2023-11-10','2023-10-24','S',1),(4,'S4','54-55cm','2023-11-10','2023-10-24','M',1);
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size_ctsp`
--

DROP TABLE IF EXISTS `size_ctsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size_ctsp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_ctsp` int DEFAULT NULL,
  `id_size` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7sx2xsvfe1gorvlkii0nw12q2` (`id_ctsp`),
  KEY `FKkvjo22xxixaj9gu4dstu4q313` (`id_size`),
  CONSTRAINT `FK7sx2xsvfe1gorvlkii0nw12q2` FOREIGN KEY (`id_ctsp`) REFERENCES `san_pham_chi_tiet` (`id`),
  CONSTRAINT `FKkvjo22xxixaj9gu4dstu4q313` FOREIGN KEY (`id_size`) REFERENCES `size` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size_ctsp`
--

LOCK TABLES `size_ctsp` WRITE;
/*!40000 ALTER TABLE `size_ctsp` DISABLE KEYS */;
INSERT INTO `size_ctsp` VALUES (1,NULL,'2023-05-05',NULL,23,1,1,1),(2,NULL,'2023-05-05',NULL,10,1,1,4),(3,NULL,'2023-05-05',NULL,17,1,1,3),(4,NULL,'2023-05-05',NULL,55,1,2,1),(5,NULL,'2023-05-05',NULL,2,1,2,2),(6,NULL,'2023-05-05',NULL,3,1,2,4),(7,NULL,'2023-05-05',NULL,40,1,2,3),(8,NULL,'2023-05-05',NULL,20,1,3,1),(9,NULL,'2023-05-05',NULL,5,1,3,3),(10,NULL,'2023-05-05',NULL,10,1,4,1),(11,NULL,'2023-05-05',NULL,10,1,4,2),(12,NULL,'2023-05-05',NULL,10,1,4,4),(13,NULL,'2023-05-05',NULL,40,1,5,1),(14,NULL,'2023-05-05',NULL,20,1,5,2),(15,NULL,'2023-05-05',NULL,20,1,5,3),(16,NULL,'2023-05-05',NULL,40,1,5,4);
/*!40000 ALTER TABLE `size_ctsp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuong_hieu`
--

DROP TABLE IF EXISTS `thuong_hieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuong_hieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuong_hieu`
--

LOCK TABLES `thuong_hieu` WRITE;
/*!40000 ALTER TABLE `thuong_hieu` DISABLE KEYS */;
INSERT INTO `thuong_hieu` VALUES (1,'1001','2023-09-10','2023-09-05','Amoro',1),(2,'1002','2023-09-15','2023-09-10','Protec',1),(3,'1003','2023-09-23','2023-09-19','HSL',0),(4,'1004','2023-09-24','2023-09-20','Hitech',0),(5,'1005','2023-08-10','2023-07-25','Andes',1),(6,'1006','2023-07-19','2023-07-15','Honda',1),(7,'1007','2023-05-20','2023-05-15','Sankyo',0),(8,'1008','2023-10-10','2023-10-05','Avex',0),(9,'1009','2023-07-10','2023-07-01','Royal',0);
/*!40000 ALTER TABLE `thuong_hieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pddrhgwxnms2aceeku9s2ewy5` (`token`),
  KEY `FKhgmxjxe8lwtgjmrwyb7kx6cs0` (`id_user`),
  CONSTRAINT `FKhgmxjxe8lwtgjmrwyb7kx6cs0` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trong_luong`
--

DROP TABLE IF EXISTS `trong_luong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trong_luong` (
  `id` int NOT NULL AUTO_INCREMENT,
  `don_vi` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `value` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trong_luong`
--

LOCK TABLES `trong_luong` WRITE;
/*!40000 ALTER TABLE `trong_luong` DISABLE KEYS */;
INSERT INTO `trong_luong` VALUES (1,'gam','TL1','2023-09-10','2023-08-20',1,850),(2,'gam','TL2','2023-09-10','2023-08-20',1,1050),(3,'gam','TL3','2023-09-10','2023-08-20',1,1050),(4,'gam','TL4','2023-09-10','2023-08-20',1,752),(5,'gam','TL5','2023-09-10','2023-08-20',1,350);
/*!40000 ALTER TABLE `trong_luong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `gioi_tinh` int DEFAULT NULL,
  `anh` varchar(50000) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sinh` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `id_chuc_vu` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpfbko0r45dpco2igftw1al9` (`id_chuc_vu`),
  CONSTRAINT `FKgpfbko0r45dpco2igftw1al9` FOREIGN KEY (`id_chuc_vu`) REFERENCES `chuc_vu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'levana@gmail.com',1,NULL,'U1','2002-10-07','2023-11-10','2023-10-24','12345','ADMIN','0987678987','Lê Văn A',1,'levana',NULL),(2,'levanb@gmail.com',1,NULL,'U2','2002-11-07','2023-11-10','2023-10-24','12345','NHANVIEN','0987678987','Lê Văn B',1,'levanb',1),(3,'levanc@gmail.com',1,NULL,'U3','2002-12-07','2023-11-10','2023-10-24','12345','USER','0987678987','Lê Văn C',1,'levanc',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_voucher`
--

DROP TABLE IF EXISTS `user_voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_voucher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dieu_kien` decimal(20,0) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  `id_voucher` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm34eg0v242ck9tt3fw8yo0vko` (`id_user`),
  KEY `FKeilm6oxouwrda1lqagxv9f22a` (`id_voucher`),
  CONSTRAINT `FKeilm6oxouwrda1lqagxv9f22a` FOREIGN KEY (`id_voucher`) REFERENCES `voucher` (`id`),
  CONSTRAINT `FKm34eg0v242ck9tt3fw8yo0vko` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_voucher`
--

LOCK TABLES `user_voucher` WRITE;
/*!40000 ALTER TABLE `user_voucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_voucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vat_lieu`
--

DROP TABLE IF EXISTS `vat_lieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vat_lieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vat_lieu`
--

LOCK TABLES `vat_lieu` WRITE;
/*!40000 ALTER TABLE `vat_lieu` DISABLE KEYS */;
INSERT INTO `vat_lieu` VALUES (1,'VL1','độ bền cao','2023-11-10','2023-10-24','nhựa ABS nguyên sinh',1),(2,'VL2','êm ái, thông thoáng bảo','2023-11-10','2023-10-24','xốp EPS ',1),(3,'VL3','không gỉ,độ bền cao','2023-11-10','2023-10-24','da simili',1);
/*!40000 ALTER TABLE `vat_lieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `giam_toi_da` decimal(20,0) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
   `so_luong` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `voucher`(`giam_toi_da`,`mo_ta`, `ten`, `thoi_gian_bat_dau`,`thoi_gian_ket_thuc`, `trang_thai`)
     VALUES (15000,'ma giam gia zomot','zô mốt','2023/06/23','2023/06/25',1),
                           (13000,'freeship','freeship','2023/06/23','2023/06/25',1),
                           (16000,'thời trang','thời trang','2023/06/23','2023/06/25',1),
                           (15000,'siêu voucher','siêu voucher','2023/06/23','2023/06/25',1),
                           (11000,'06-06','06-06','2023/06/23','2023/06/25',1);
--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;