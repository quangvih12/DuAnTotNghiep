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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuc_vu`
--

LOCK TABLES `chuc_vu` WRITE;
/*!40000 ALTER TABLE `chuc_vu` DISABLE KEYS */;
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

DROP TABLE IF EXISTS `thong_bao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thong_bao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `id_san_pham_chi_tiet` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
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

LOCK TABLES `thong_bao` WRITE;
/*!40000 ALTER TABLE `thong_bao` DISABLE KEYS */;
/*!40000 ALTER TABLE `thong_bao` ENABLE KEYS */;
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
  `id_quan_huyen` int DEFAULT NULL,
  `id_tinh_thanh` int DEFAULT NULL,
  `id_phuong_xa` varchar(255) DEFAULT NULL,
  `loai_dia_chi` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `ten_quan_huyen` varchar(255) DEFAULT NULL,
  `ten_tinh_thanh` varchar(255) DEFAULT NULL,
  `ten_phuong_xa` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbfv0wiqy3kyntfe72a952m3ym` (`id_user`),
  CONSTRAINT `FKbfv0wiqy3kyntfe72a952m3ym` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_chi`
--

LOCK TABLES `dia_chi` WRITE;
/*!40000 ALTER TABLE `dia_chi` DISABLE KEYS */;
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
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
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
  `ly_do` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ngay_nhan` datetime(6) DEFAULT NULL,
  `ngay_ship` datetime(6) DEFAULT NULL,
  `ngay_sua` datetime(6) DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `ngay_thanh_toan` datetime(6) DEFAULT NULL,
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
  `ly_do` varchar(255) DEFAULT NULL,
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
  `anh` varchar(10000) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa4gihn9sp5ngnuwb70tce2nxx` (`id_san_pham`),
  CONSTRAINT `FKa4gihn9sp5ngnuwb70tce2nxx` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
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
  `gia_tri_giam` int DEFAULT NULL,
  `giam_toi_da` decimal(20,0) DEFAULT NULL,
  `kieu_giam_gia` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` datetime(6) DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime(6) DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime(6) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyen_mai`
--

LOCK TABLES `khuyen_mai` WRITE;
/*!40000 ALTER TABLE `khuyen_mai` DISABLE KEYS */;
/*!40000 ALTER TABLE `khuyen_mai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyen_mai_ctsp`
--

DROP TABLE IF EXISTS `khuyen_mai_ctsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyen_mai_ctsp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_tien_con_lai` decimal(20,0) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_khuyen_mai` int DEFAULT NULL,
  `id_ctsp` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe781xl7r1mbttpsw0k5tydfky` (`id_khuyen_mai`),
  KEY `FKknc3jbrh4y8bl4umf81oce3v` (`id_ctsp`),
  CONSTRAINT `FKe781xl7r1mbttpsw0k5tydfky` FOREIGN KEY (`id_khuyen_mai`) REFERENCES `khuyen_mai` (`id`),
  CONSTRAINT `FKknc3jbrh4y8bl4umf81oce3v` FOREIGN KEY (`id_ctsp`) REFERENCES `san_pham_chi_tiet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyen_mai_ctsp`
--

LOCK TABLES `khuyen_mai_ctsp` WRITE;
/*!40000 ALTER TABLE `khuyen_mai_ctsp` DISABLE KEYS */;
/*!40000 ALTER TABLE `khuyen_mai_ctsp` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai`
--

LOCK TABLES `loai` WRITE;
/*!40000 ALTER TABLE `loai` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mau_sac`
--

LOCK TABLES `mau_sac` WRITE;
/*!40000 ALTER TABLE `mau_sac` DISABLE KEYS */;
/*!40000 ALTER TABLE `mau_sac` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phuong_thuc_thanh_toan`
--

LOCK TABLES `phuong_thuc_thanh_toan` WRITE;
/*!40000 ALTER TABLE `phuong_thuc_thanh_toan` DISABLE KEYS */;
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
  `anh` varchar(255) DEFAULT NULL,
  `dem_lot` varchar(255) DEFAULT NULL,
  `ma` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(10000) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `quai_deo` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_loai` int DEFAULT NULL,
  `id_thuong_hieu` int DEFAULT NULL,
  `id_vat_lieu` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ietyty0m29mftr3yaff0v4wb` (`id_loai`),
  KEY `FKng0be3yah8qweo3tnmxm9pnrw` (`id_thuong_hieu`),
  KEY `FKk978ncnc6kbpaj5ugxf5y6ia3` (`id_vat_lieu`),
  CONSTRAINT `FK3ietyty0m29mftr3yaff0v4wb` FOREIGN KEY (`id_loai`) REFERENCES `loai` (`id`),
  CONSTRAINT `FKk978ncnc6kbpaj5ugxf5y6ia3` FOREIGN KEY (`id_vat_lieu`) REFERENCES `vat_lieu` (`id`),
  CONSTRAINT `FKng0be3yah8qweo3tnmxm9pnrw` FOREIGN KEY (`id_thuong_hieu`) REFERENCES `thuong_hieu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
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
  `anh` varchar(255) DEFAULT NULL,
  `gia_ban` decimal(20,0) DEFAULT NULL,
  `gia_nhap` decimal(20,0) DEFAULT NULL,
  `gia_sau_giam` decimal(20,0) DEFAULT NULL,
  `ngay_sua` varchar(255) DEFAULT NULL,
  `ngay_tao` varchar(255) DEFAULT NULL,
  `so_luong_ton` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_khuyen_mai` int DEFAULT NULL,
  `id_mau_sac` int DEFAULT NULL,
  `id_san_pham` int DEFAULT NULL,
  `id_size` int DEFAULT NULL,
  `id_trong_luong` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1rjmi0xu7agob46sq32ns1587` (`id_khuyen_mai`),
  KEY `FKdt9xxy835agedr1aa67p8vw0o` (`id_mau_sac`),
  KEY `FKmby561odp360b0sfqx4mmarja` (`id_san_pham`),
  KEY `FK899rggxfp9dtblqirh75pme1` (`id_size`),
  KEY `FKdrjkavdharm2dd7tsh3wywlr1` (`id_trong_luong`),
  CONSTRAINT `FK1rjmi0xu7agob46sq32ns1587` FOREIGN KEY (`id_khuyen_mai`) REFERENCES `khuyen_mai` (`id`),
  CONSTRAINT `FK899rggxfp9dtblqirh75pme1` FOREIGN KEY (`id_size`) REFERENCES `size` (`id`),
  CONSTRAINT `FKdrjkavdharm2dd7tsh3wywlr1` FOREIGN KEY (`id_trong_luong`) REFERENCES `trong_luong` (`id`),
  CONSTRAINT `FKdt9xxy835agedr1aa67p8vw0o` FOREIGN KEY (`id_mau_sac`) REFERENCES `mau_sac` (`id`),
  CONSTRAINT `FKmby561odp360b0sfqx4mmarja` FOREIGN KEY (`id_san_pham`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham_chi_tiet`
--

LOCK TABLES `san_pham_chi_tiet` WRITE;
/*!40000 ALTER TABLE `san_pham_chi_tiet` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuong_hieu`
--

LOCK TABLES `thuong_hieu` WRITE;
/*!40000 ALTER TABLE `thuong_hieu` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trong_luong`
--

LOCK TABLES `trong_luong` WRITE;
/*!40000 ALTER TABLE `trong_luong` DISABLE KEYS */;
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
  `anh` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
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
  `id_user` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `id_voucher` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm34eg0v242ck9tt3fw8yo0vko` (`id_user`),
  KEY `FKeilm6oxouwrda1lqagxv9f22a` (`id_voucher`),
  CONSTRAINT `FKeilm6oxouwrda1lqagxv9f22a` FOREIGN KEY (`id_voucher`) REFERENCES `voucher` (`id`),
  CONSTRAINT `FKm34eg0v242ck9tt3fw8yo0vko` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
ALTER TABLE `user_voucher`
DROP COLUMN `dieu_kien`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vat_lieu`
--

LOCK TABLES `vat_lieu` WRITE;
/*!40000 ALTER TABLE `vat_lieu` DISABLE KEYS */;
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
  `so_luong` int DEFAULT NULL,
  `gia_tri_giam` int DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoi_gian_bat_dau` datetime(6) DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime(6) DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2023-11-07 23:03:43
