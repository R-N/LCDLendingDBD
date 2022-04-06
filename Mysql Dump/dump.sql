-- MySQL dump 10.16  Distrib 10.1.34-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: lcddbd
-- ------------------------------------------------------
-- Server version	10.1.34-MariaDB

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
-- Table structure for table `barang`
--

DROP TABLE IF EXISTS `barang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barang` (
  `TIPE_BARANG` char(1) NOT NULL,
  `NOMOR_BARANG` int(11) NOT NULL,
  PRIMARY KEY (`TIPE_BARANG`,`NOMOR_BARANG`),
  CONSTRAINT `FK_BARANG_BERTIPE_TIPE_BAR` FOREIGN KEY (`TIPE_BARANG`) REFERENCES `tipe_barang` (`TIPE_BARANG`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barang`
--

LOCK TABLES `barang` WRITE;
/*!40000 ALTER TABLE `barang` DISABLE KEYS */;
INSERT INTO `barang` VALUES ('K',1),('K',2),('L',1),('L',2),('L',3);
/*!40000 ALTER TABLE `barang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `barang_terkini`
--

DROP TABLE IF EXISTS `barang_terkini`;
/*!50001 DROP VIEW IF EXISTS `barang_terkini`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `barang_terkini` (
  `Nomor_Barang` tinyint NOT NULL,
  `Tipe_Barang` tinyint NOT NULL,
  `Status_Barang` tinyint NOT NULL,
  `Nomor_Peminjaman` tinyint NOT NULL,
  `Keterangan_Barang` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `keperluan_pinjam`
--

DROP TABLE IF EXISTS `keperluan_pinjam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keperluan_pinjam` (
  `ID_KEPERLUAN_PINJAM` int(11) NOT NULL AUTO_INCREMENT,
  `NOMOR_PEMINJAMAN` int(11) NOT NULL,
  `KEPERLUAN_PINJAM` varchar(64) NOT NULL,
  `NAMA_PENANGGUNG_JAWAB` varchar(64) NOT NULL,
  `RUANG_PINJAM` varchar(64) NOT NULL,
  `WAKTU_MULAI` datetime NOT NULL,
  `WAKTU_SELESAI` datetime NOT NULL,
  PRIMARY KEY (`ID_KEPERLUAN_PINJAM`),
  KEY `FK_KEPERLUA_PINJAM_UN_PEMINJAM` (`NOMOR_PEMINJAMAN`),
  CONSTRAINT `FK_KEPERLUA_PINJAM_UN_PEMINJAM` FOREIGN KEY (`NOMOR_PEMINJAMAN`) REFERENCES `peminjaman` (`NOMOR_PEMINJAMAN`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keperluan_pinjam`
--

LOCK TABLES `keperluan_pinjam` WRITE;
/*!40000 ALTER TABLE `keperluan_pinjam` DISABLE KEYS */;
INSERT INTO `keperluan_pinjam` VALUES (1,1,'adsfasdf','asdfasdf','asdfasdf','2019-01-04 01:00:00','2019-01-04 02:00:00'),(2,2,'PTI','Pak Khalid','Labkom1','2019-01-04 01:00:00','2019-01-04 04:00:00'),(3,3,'dbd','trisha','labkom 1','2019-01-04 13:00:00','2019-01-04 16:00:00'),(4,4,'pbo','riski','labkom 1','2019-01-04 12:00:00','2019-01-04 13:00:00'),(5,5,'ifest','trisha','sport center','2019-01-04 06:00:00','2019-01-04 11:00:00');
/*!40000 ALTER TABLE `keperluan_pinjam` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_INSERT_KEPERLUAN before insert
on KEPERLUAN_PINJAM for each row
begin

    IF NEW.Keperluan_Pinjam IS NULL OR NEW.Keperluan_Pinjam = ' ' THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan_Pinjam tidak valid';
    END IF;
    IF NEW.Nama_Penanggung_Jawab IS NULL OR NEW.Nama_Penanggung_Jawab = ' ' OR CHAR_LENGTH(NEW.Nama_Penanggung_Jawab) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama_Penanggung_Jawab tidak valid';
    END IF;
    IF NEW.Ruang_Pinjam IS NULL OR NEW.Ruang_Pinjam = ' ' OR CHAR_LENGTH(NEW.Ruang_Pinjam) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Ruang_Pinjam tidak valid';
    END IF;
    IF NEW.Waktu_Mulai IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu_Mulai tidak boleh kosong';
    END IF;
    IF NEW.Waktu_Selesai IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu_Selesai tidak boleh kosong';
    END IF;
    
    IF NEW.Waktu_Selesai <= NEW.Waktu_Mulai THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu mulai dan selesai tidak valid';
    END IF;
    
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger AFTER_INSERT_KEPERLUAN_PINJAM after insert
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(NEW.Nomor_Peminjaman);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_UPDATE_KEPERLUAN before update
on KEPERLUAN_PINJAM for each row
begin
    IF NEW.Nomor_Peminjaman IS NULL THEN
        SET NEW.Nomor_Peminjaman = OLD.Nomor_Peminjaman;
    END IF;
    IF NEW.Keperluan_Pinjam IS NULL THEN
        SET NEW.Keperluan_Pinjam = OLD.Keperluan_Pinjam;
    END IF;
    IF NEW.Nama_Penanggung_Jawab IS NULL THEN
        SET NEW.Nama_Penanggung_Jawab = OLD.Nama_Penanggung_Jawab;
    END IF;
    IF NEW.Ruang_Pinjam IS NULL THEN
        SET NEW.Ruang_Pinjam = OLD.Ruang_Pinjam;
    END IF;
    IF NEW.Waktu_Mulai IS NULL THEN
        SET NEW.Waktu_Mulai = OLD.Waktu_Mulai;
    END IF;
    IF NEW.Waktu_Selesai IS NULL THEN
        SET NEW.Waktu_Selesai = OLD.Waktu_Selesai;
    END IF;
    
    IF NEW.Waktu_Selesai <= NEW.Waktu_Mulai THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu mulai dan selesai tidak valid';
    END IF;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger AFTER_UPDATE_KEPERLUAN_PINJAM after update
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(NEW.Nomor_Peminjaman);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger AFTER_DELETE_KEPERLUAN_PINJAM after delete
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(OLD.Nomor_Peminjaman);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `peminjam`
--

DROP TABLE IF EXISTS `peminjam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peminjam` (
  `IDENTITAS_PEMINJAM` varchar(18) NOT NULL,
  `USERNAME_PETUGAS` varchar(64) NOT NULL,
  `NAMA_PEMINJAM` varchar(64) NOT NULL,
  `ALAMAT_PEMINJAM` varchar(256) NOT NULL,
  `NOMOR_HP_PEMINJAM` varchar(14) NOT NULL,
  `KETERANGAN_BLACKLIST` varchar(256) DEFAULT NULL,
  `WAKTU_ADMIN_PEMINJAM_TERAKHIR` datetime NOT NULL,
  PRIMARY KEY (`IDENTITAS_PEMINJAM`),
  KEY `FK_PEMINJAM_ADMINISTR_PETUGAS` (`USERNAME_PETUGAS`),
  CONSTRAINT `FK_PEMINJAM_ADMINISTR_PETUGAS` FOREIGN KEY (`USERNAME_PETUGAS`) REFERENCES `petugas` (`USERNAME_PETUGAS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peminjam`
--

LOCK TABLES `peminjam` WRITE;
/*!40000 ALTER TABLE `peminjam` DISABLE KEYS */;
INSERT INTO `peminjam` VALUES ('H00000000','admin','Nama Mhs','Surabaya','0808080808',NULL,'2019-01-04 10:53:21'),('H0876543212','admin','TRISHA','SURABAYA','08765431324',NULL,'2019-01-04 11:00:50'),('h121212123','admin','rifa','sidoarjo','07654321423','Tidak bisa dihubungi','2019-01-04 11:06:40'),('H76217063','admin','Nama mhs','sdfasdfadsfsdfds','0808080808',NULL,'2019-01-04 08:23:25');
/*!40000 ALTER TABLE `peminjam` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_INSERT_PEMINJAM before insert
on PEMINJAM for each row
begin
    
    IF NEW.Nama_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nama_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama peminjam tidak valid';
    END IF;
    IF NEW.Alamat_Peminjam = ' ' OR CHAR_LENGTH(NEW.Alamat_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Alamat peminjam tidak valid';
    END IF;
    IF NEW.Identitas_Peminjam = ' ' OR CHAR_LENGTH(NEW.Identitas_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Identitas peminjam  tidak valid';
    END IF;
    IF NEW.Nomor_HP_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nomor_HP_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor HP peminjam tidak valid';
    END IF;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_UPDATE_PEMINJAM before update
on PEMINJAM for each row
begin
    IF NEW.Nama_Peminjam IS NULL THEN
        SET NEW.Nama_Peminjam=OLD.Nama_Peminjam;
    END IF;
    IF NEW.Alamat_Peminjam IS NULL THEN
        SET NEW.Alamat_Peminjam=OLD.Alamat_Peminjam;
    END IF;
    IF NEW.Identitas_Peminjam IS NULL THEN
        SET NEW.Identitas_Peminjam=OLD.Identitas_Peminjam;
    END IF;
    IF NEW.Nomor_HP_Peminjam IS NULL THEN
        SET NEW.Nomor_HP_Peminjam=OLD.Nomor_HP_Peminjam;
    END IF;
    IF NEW.Nama_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nama_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama peminjam tidak valid';
    END IF;
    IF NEW.Alamat_Peminjam = ' ' OR CHAR_LENGTH(NEW.Alamat_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Alamat peminjam tidak valid';
    END IF;
    IF NEW.Identitas_Peminjam = ' ' OR CHAR_LENGTH(NEW.Identitas_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Identitas peminjam tidak valid';
    END IF;
    IF NEW.Nomor_HP_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nomor_HP_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor HP peminjam tidak valid';
    END IF;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `peminjaman`
--

DROP TABLE IF EXISTS `peminjaman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peminjaman` (
  `NOMOR_PEMINJAMAN` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME_PETUGAS` varchar(64) DEFAULT NULL,
  `IDENTITAS_PEMINJAM` varchar(18) NOT NULL,
  `STATUS_PEMINJAMAN` char(1) NOT NULL,
  `KETERANGAN_PEMINJAMAN` varchar(256) DEFAULT NULL,
  `WAKTU_PINJAM` datetime NOT NULL,
  `WAKTU_HARUS_KEMBALI` datetime NOT NULL,
  `WAKTU_KEMBALI` datetime DEFAULT NULL,
  `WAKTU_ADMIN_PEMINJAMAN_TERAKHIR` datetime NOT NULL,
  PRIMARY KEY (`NOMOR_PEMINJAMAN`),
  KEY `FK_PEMINJAMAN_ADMINISTR_PETUGAS` (`USERNAME_PETUGAS`),
  KEY `FK_PEMINJAM_MELAKUKAN_PEMINJAM` (`IDENTITAS_PEMINJAM`),
  KEY `FK_PEMINJAM_PEMINJAMA_STATUS_P` (`STATUS_PEMINJAMAN`),
  CONSTRAINT `FK_PEMINJAMAN_ADMINISTR_PETUGAS` FOREIGN KEY (`USERNAME_PETUGAS`) REFERENCES `petugas` (`USERNAME_PETUGAS`),
  CONSTRAINT `FK_PEMINJAM_MELAKUKAN_PEMINJAM` FOREIGN KEY (`IDENTITAS_PEMINJAM`) REFERENCES `peminjam` (`IDENTITAS_PEMINJAM`) ON UPDATE CASCADE,
  CONSTRAINT `FK_PEMINJAM_PEMINJAMA_STATUS_P` FOREIGN KEY (`STATUS_PEMINJAMAN`) REFERENCES `status_peminjaman` (`STATUS_PEMINJAMAN`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peminjaman`
--

LOCK TABLES `peminjaman` WRITE;
/*!40000 ALTER TABLE `peminjaman` DISABLE KEYS */;
INSERT INTO `peminjaman` VALUES (1,'admin','H76217063','S',NULL,'2019-01-04 08:23:51','2019-01-04 02:30:00','2019-01-04 10:58:28','2019-01-04 10:58:28'),(2,'admin','H00000000','A',NULL,'2019-01-04 10:57:05','2019-01-04 04:30:00',NULL,'2019-01-04 10:57:05'),(3,'admin','h121212123','S',NULL,'2019-01-04 11:04:09','2019-01-04 16:30:00','2019-01-04 11:07:00','2019-01-04 11:07:00'),(4,'admin','H0876543212','A',NULL,'2019-01-04 11:05:06','2019-01-04 13:30:00',NULL,'2019-01-04 11:05:06'),(5,'admin','H76217063','A',NULL,'2019-01-04 11:06:00','2019-01-04 11:30:00',NULL,'2019-01-04 11:06:00');
/*!40000 ALTER TABLE `peminjaman` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_INSERT_PEMINJAMAN before insert
on PEMINJAMAN for each row
begin
    DECLARE keterangan_blacklist VARCHAR(320);
    
    
    SELECT P.Keterangan_Blacklist 
    INTO keterangan_blacklist 
    FROM Peminjam P 
    WHERE P.Identitas_Peminjam=NEW.Identitas_Peminjam;
    
    IF keterangan_blacklist IS NOT NULL THEN
        SET keterangan_blacklist = CONCAT('Peminjam terblacklist. Keterangan: ', keterangan_blacklist);
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT=keterangan_blacklist;
    END IF;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_UPDATE_PEMINJAMAN before update
on PEMINJAMAN for each row
begin
    IF NEW.Status_Peminjaman IS NULL THEN
        SET NEW.Status_Peminjaman = OLD.Status_Peminjaman;
    END IF;
    IF NEW.Waktu_Pinjam IS NULL THEN
        SET NEW.Waktu_Pinjam = OLD.Waktu_Pinjam;
    END IF;
    IF NEW.Keterangan_Peminjaman IS NULL THEN
        SET NEW.Keterangan_Peminjaman = OLD.Keterangan_Peminjaman;
    END IF;
    IF NEW.Waktu_Harus_Kembali IS NULL THEN
        SET NEW.Waktu_Harus_Kembali = OLD.Waktu_Harus_Kembali;
    END IF;
    IF NEW.Waktu_Kembali IS NULL AND NEW.Status_Peminjaman > 1 THEN
        SET NEW.Waktu_Kembali = OLD.Waktu_Kembali;
    END IF;
    
    IF NEW.Username_Petugas IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username petugas harus diisi';
    END IF;
    
    
    IF OLD.Status_Peminjaman <> NEW.Status_Peminjaman THEN
        IF NEW.Status_Peminjaman='A' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Anda tidak dapat membuka kembali peminjaman. Silahkan buat peminjaman baru.';
        ELSE
            IF EXISTS (SELECT * FROM Barang_Terkini BR WHERE BR.Nomor_Peminjaman=NEW.Nomor_Peminjaman) THEN
                SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Masih ada barang yang belum dikembalikan';
            END IF;
            SET NEW.Waktu_Kembali=NOW();
        END IF;
    END IF;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `petugas`
--

DROP TABLE IF EXISTS `petugas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `petugas` (
  `USERNAME_PETUGAS` varchar(64) NOT NULL,
  `NAMA_PETUGAS` varchar(64) NOT NULL,
  `HASHPASS_PETUGAS` varchar(64) NOT NULL,
  `SESI_PETUGAS` varchar(64) DEFAULT NULL,
  `TIMEOUT_SESI_PETUGAS` datetime DEFAULT NULL,
  PRIMARY KEY (`USERNAME_PETUGAS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petugas`
--

LOCK TABLES `petugas` WRITE;
/*!40000 ALTER TABLE `petugas` DISABLE KEYS */;
INSERT INTO `petugas` VALUES ('admin','Nama Admin','c92bc8fbbfddb05b005b4cbbe8d2b9a0','8ca0cac4049055ec085b6743e8e262ce','2019-01-04 11:12:00');
/*!40000 ALTER TABLE `petugas` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger BEFORE_INSERT_PETUGAS before insert
on PETUGAS for each row
begin
    IF NEW.Username_Petugas IS NULL OR NEW.Username_Petugas = ' ' OR CHAR_LENGTH(NEW.Username_Petugas) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username tidak valid';
    END IF;
    IF NEW.Nama_Petugas IS NULL OR NEW.Nama_Petugas = ' ' OR CHAR_LENGTH(NEW.Nama_Petugas) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama tidak valid';
    END IF;
    
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `riwayat_barang`
--

DROP TABLE IF EXISTS `riwayat_barang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `riwayat_barang` (
  `ID_RIWAYAT_BARANG` int(11) NOT NULL AUTO_INCREMENT,
  `TIPE_BARANG` char(1) NOT NULL,
  `NOMOR_BARANG` int(11) NOT NULL,
  `USERNAME_PETUGAS` varchar(64) NOT NULL,
  `NOMOR_PEMINJAMAN` int(11) DEFAULT NULL,
  `STATUS_BARANG` char(1) NOT NULL,
  `KETERANGAN_BARANG` varchar(256) DEFAULT NULL,
  `WAKTU_MULAI_RIWAYAT_BARANG` datetime NOT NULL,
  `WAKTU_BERAKHIR_RIWAYAT_BARANG` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_RIWAYAT_BARANG`),
  UNIQUE KEY `UNIQUE_START_TIMELINE` (`TIPE_BARANG`,`NOMOR_BARANG`,`WAKTU_MULAI_RIWAYAT_BARANG`),
  UNIQUE KEY `UNIQUE_END_TIMELINE` (`TIPE_BARANG`,`NOMOR_BARANG`,`WAKTU_BERAKHIR_RIWAYAT_BARANG`),
  KEY `FK_RIWAYAT__ADMINISTR_PETUGAS` (`USERNAME_PETUGAS`),
  KEY `FK_RIWAYAT__BARANG_BE_STATUS_B` (`STATUS_BARANG`),
  KEY `FK_RIWAYAT__BARANG_DI_PEMINJAM` (`NOMOR_PEMINJAMAN`),
  CONSTRAINT `FK_RIWAYAT__ADMINISTR_PETUGAS` FOREIGN KEY (`USERNAME_PETUGAS`) REFERENCES `petugas` (`USERNAME_PETUGAS`),
  CONSTRAINT `FK_RIWAYAT__BARANG_BE_STATUS_B` FOREIGN KEY (`STATUS_BARANG`) REFERENCES `status_barang` (`STATUS_BARANG`) ON UPDATE CASCADE,
  CONSTRAINT `FK_RIWAYAT__BARANG_DI_PEMINJAM` FOREIGN KEY (`NOMOR_PEMINJAMAN`) REFERENCES `peminjaman` (`NOMOR_PEMINJAMAN`),
  CONSTRAINT `FK_RIWAYAT__MEMILIKI__BARANG` FOREIGN KEY (`TIPE_BARANG`, `NOMOR_BARANG`) REFERENCES `barang` (`TIPE_BARANG`, `NOMOR_BARANG`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `riwayat_barang`
--

LOCK TABLES `riwayat_barang` WRITE;
/*!40000 ALTER TABLE `riwayat_barang` DISABLE KEYS */;
INSERT INTO `riwayat_barang` VALUES (1,'L',1,'admin',NULL,'A',NULL,'2019-01-04 08:23:08','2019-01-04 08:23:51'),(2,'L',1,'admin',1,'P',NULL,'2019-01-04 08:23:51','2019-01-04 10:58:28'),(3,'L',2,'admin',NULL,'A',NULL,'2019-01-04 10:53:54','2019-01-04 10:57:05'),(4,'L',2,'admin',2,'P',NULL,'2019-01-04 10:57:05',NULL),(5,'L',1,'admin',NULL,'A',NULL,'2019-01-04 10:58:28','2019-01-04 11:04:09'),(6,'L',3,'admin',NULL,'A',NULL,'2019-01-04 10:59:46','2019-01-04 11:05:06'),(7,'K',1,'admin',NULL,'A',NULL,'2019-01-04 10:59:53','2019-01-04 11:04:09'),(8,'K',2,'admin',NULL,'A',NULL,'2019-01-04 10:59:59','2019-01-04 11:06:00'),(9,'L',1,'admin',3,'P',NULL,'2019-01-04 11:04:09','2019-01-04 11:07:00'),(10,'K',1,'admin',3,'P',NULL,'2019-01-04 11:04:09','2019-01-04 11:07:00'),(11,'L',3,'admin',4,'P',NULL,'2019-01-04 11:05:06',NULL),(12,'K',2,'admin',5,'P',NULL,'2019-01-04 11:06:00',NULL),(13,'K',1,'admin',NULL,'A',NULL,'2019-01-04 11:07:00',NULL),(14,'L',1,'admin',NULL,'A',NULL,'2019-01-04 11:07:00',NULL);
/*!40000 ALTER TABLE `riwayat_barang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_barang`
--

DROP TABLE IF EXISTS `status_barang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_barang` (
  `STATUS_BARANG` char(1) NOT NULL,
  `TEKS_STATUS_BARANG` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`STATUS_BARANG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_barang`
--

LOCK TABLES `status_barang` WRITE;
/*!40000 ALTER TABLE `status_barang` DISABLE KEYS */;
INSERT INTO `status_barang` VALUES ('A','Ada'),('P','Dipinjam'),('R','Rusak');
/*!40000 ALTER TABLE `status_barang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_peminjaman`
--

DROP TABLE IF EXISTS `status_peminjaman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_peminjaman` (
  `STATUS_PEMINJAMAN` char(1) NOT NULL,
  `TEKS_STATUS_PEMINJAMAN` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`STATUS_PEMINJAMAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_peminjaman`
--

LOCK TABLES `status_peminjaman` WRITE;
/*!40000 ALTER TABLE `status_peminjaman` DISABLE KEYS */;
INSERT INTO `status_peminjaman` VALUES ('A','Aktif'),('B','Batal'),('S','Selesai');
/*!40000 ALTER TABLE `status_peminjaman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipe_barang`
--

DROP TABLE IF EXISTS `tipe_barang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipe_barang` (
  `TIPE_BARANG` char(1) NOT NULL,
  `TEKS_TIPE_BARANG` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`TIPE_BARANG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipe_barang`
--

LOCK TABLES `tipe_barang` WRITE;
/*!40000 ALTER TABLE `tipe_barang` DISABLE KEYS */;
INSERT INTO `tipe_barang` VALUES ('K','Kabel'),('L','LCD');
/*!40000 ALTER TABLE `tipe_barang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'lcddbd'
--
/*!50003 DROP FUNCTION IF EXISTS `GET_NAMA_PETUGAS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `GET_NAMA_PETUGAS`(Username VARCHAR(16)) RETURNS varchar(64) CHARSET latin1
BEGIN
    DECLARE Nama VARCHAR(64);
    SELECT P.Nama_Petugas INTO Nama FROM Petugas P WHERE P.Username_Petugas=Username;
    RETURN Nama;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DELETE_KEPERLUAN_PINJAM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DELETE_KEPERLUAN_PINJAM`(
    IN Id INTEGER, 
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    DELETE FROM Keperluan_Pinjam
    WHERE Id_Keperluan_Pinjam=Id;
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan pinjam tidak ditemukan';
    END IF;
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `HITUNG_WAKTU_HARUS_KEMBALI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `HITUNG_WAKTU_HARUS_KEMBALI`(
    IN Nomor_Peminjaman INTEGER
)
BEGIN
    DECLARE err VARCHAR(64);
    DECLARE waktu_harus_kembali DATETIME;
    DECLARE username VARCHAR(16);
    
    
    
    SET waktu_harus_kembali=(
        SELECT MAX(KP.Waktu_Selesai)+INTERVAL 30 MINUTE
        FROM Keperluan_Pinjam KP
        WHERE KP.Nomor_Peminjaman=Nomor_Peminjaman
    );
    
    
    
    UPDATE Peminjaman P
    SET
        P.Waktu_Harus_Kembali=waktu_harus_kembali,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=Nomor_Peminjaman;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INSERT_BARANG` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `INSERT_BARANG`(
    IN Nomor_Barang INTEGER,
    IN Tipe_Barang CHAR(1),
    IN Keterangan_Barang VARCHAR(256), 
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    IF EXISTS (SELECT * FROM Barang B WHERE B.Nomor_Barang=Nomor_Barang AND B.Tipe_Barang=Tipe_Barang) THEN
        IF EXISTS (SELECT * FROM Riwayat_Barang RB WHERE RB.Nomor_Barang=Nomor_Barang AND RB.Tipe_Barang=Tipe_Barang AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL) THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang sudah ada';
        END IF;
    ELSE
        INSERT INTO Barang(Nomor_Barang, Tipe_Barang) VALUES(Nomor_Barang, Tipe_Barang);
    END IF;
    
    INSERT INTO Riwayat_Barang(
        Tipe_Barang,
        Nomor_Barang,
        Username_Petugas,
        Status_Barang,
        Keterangan_Barang,
        Waktu_Mulai_Riwayat_Barang
    ) VALUES (
        Tipe_Barang,
        Nomor_Barang,
        username,
        'A',
        Keterangan_Barang,
        NOW()
    );
    
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INSERT_KEPERLUAN_PINJAM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `INSERT_KEPERLUAN_PINJAM`(
    IN Keperluan_Pinjam VARCHAR(64),
    IN Nama_Penanggung_Jawab VARCHAR(64),
    IN Ruang_Pinjam VARCHAR(16),
    IN Waktu_Mulai DATETIME,
    IN Waktu_Selesai DATETIME,
    IN Nomor_Peminjaman INTEGER,
    IN Sesi CHAR(32),
    OUT Id_Keperluan_Pinjam INTEGER,
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    
    START TRANSACTION;
    
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    INSERT INTO Keperluan_Pinjam(
        Nomor_Peminjaman,
        Keperluan_Pinjam,
        Nama_Penanggung_Jawab,
        Ruang_Pinjam,
        Waktu_Mulai,
        Waktu_Selesai
    ) VALUES (
        Nomor_Peminjaman,
        Keperluan_Pinjam,
        Nama_Penanggung_Jawab,
        Ruang_Pinjam,
        Waktu_Mulai,
        Waktu_Selesai
    );
    SET Id_Keperluan_Pinjam = LAST_INSERT_ID();
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INSERT_PEMINJAM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `INSERT_PEMINJAM`(
    IN Identitas_Peminjam VARCHAR(18),
    IN Nama_Peminjam VARCHAR(64),
    IN Alamat_Peminjam VARCHAR(256),
    IN Nomor_HP_Peminjam VARCHAR(14),
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    INSERT INTO Peminjam  (
        Identitas_Peminjam,
        Nama_Peminjam,
        Alamat_Peminjam,
        Nomor_HP_Peminjam,
        Username_Petugas,
        Waktu_Admin_Peminjam_Terakhir
    ) VALUES (
        Identitas_Peminjam,
        Nama_Peminjam,
        Alamat_Peminjam,
        Nomor_HP_Peminjam,
        username,
        NOW()
    );
    
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INSERT_PEMINJAMAN` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `INSERT_PEMINJAMAN`(
    IN Identitas_Peminjam VARCHAR(18),
    IN Waktu_Harus_Kembali DATETIME,
    IN Keterangan_Peminjaman VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Nomor_Peminjaman INTEGER,
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE waktu DATETIME;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    SET waktu=NOW();
    
    START TRANSACTION;
    
    
    INSERT INTO Peminjaman(
        Username_Petugas,
        Identitas_Peminjam,
        Status_Peminjaman,
        Keterangan_Peminjaman,
        Waktu_Pinjam,
        Waktu_Harus_Kembali,
        Waktu_Admin_Peminjaman_Terakhir
    ) VALUES (
        username,
        Identitas_Peminjam,
        'A',
        Keterangan_Peminjaman,
        waktu,
        Waktu_Harus_Kembali,
        waktu
    );
    SET Nomor_Peminjaman = LAST_INSERT_ID();
    
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INSERT_PETUGAS_UNSAFE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `INSERT_PETUGAS_UNSAFE`(
    IN Username VARCHAR(16), 
    IN Password VARCHAR(16),
    IN Nama VARCHAR(64)
)
    SQL SECURITY INVOKER
BEGIN
    IF Password IS NULL OR Password = ' ' OR CHAR_LENGTH(Password) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Password tidak valid';
    END IF;

    INSERT INTO Petugas(
        Username_Petugas, 
        Hashpass_Petugas, 
        Nama_Petugas
    ) VALUES(
        Username, 
        MD5(CONCAT(MD5(CONCAT(Password, 'Petugas')), 'Admin')),
        Nama
    );
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LOGIN_PETUGAS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LOGIN_PETUGAS`(IN Username VARCHAR(16), IN Hashpass CHAR(32), OUT Nama VARCHAR(64), OUT Sesi CHAR(32), OUT Timeout DATETIME)
BEGIN
    DECLARE datetime_now DATETIME;
    
    IF Username IS NULL OR Username = ' ' OR CHAR_LENGTH(Username) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username tidak valid';
    END IF;
    IF Hashpass IS NULL OR Hashpass = ' ' OR CHAR_LENGTH(Hashpass) < 32 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Hashpass tidak valid';
    END IF;
    IF NOT EXISTS (SELECT * FROM Petugas WHERE Petugas.Username_Petugas = Username AND Petugas.Hashpass_Petugas = MD5(CONCAT(Hashpass, 'Admin'))) THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username atau password salah';
    END IF;
    SET datetime_now = NOW();
    SET Sesi = MD5(CONCAT(datetime_now, Username));
    WHILE EXISTS (SELECT * FROM Petugas WHERE Sesi_Petugas=Sesi) DO
        SET datetime_now = NOW();
        SET Sesi = MD5(CONCAT(datetime_now, Username));
    END WHILE;
    SET Timeout = datetime_now + INTERVAL 5 MINUTE;
    
    UPDATE Petugas SET Sesi_Petugas = Sesi, Timeout_Sesi_Petugas=Timeout WHERE Username_Petugas=Username;
    SELECT Nama_Petugas INTO Nama FROM Petugas WHERE Sesi_Petugas=Sesi;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LOGIN_PETUGAS_UNSAFE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LOGIN_PETUGAS_UNSAFE`(IN Username VARCHAR(16), IN Password VARCHAR(16), OUT Nama VARCHAR(64), OUT Sesi CHAR(32), OUT Timeout DATETIME)
    SQL SECURITY INVOKER
BEGIN
    IF Password IS NULL OR Password = ' ' OR CHAR_LENGTH(Password) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Password tidak valid';
    END IF;
    CALL Login_Petugas(Username, MD5(CONCAT(Password, 'Petugas')), Nama, Sesi, Timeout);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LOGOUT_PETUGAS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LOGOUT_PETUGAS`(
    IN Sesi_Petugas CHAR(32)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
   
    
    START TRANSACTION;
    
    UPDATE Petugas P
    SET P.Sesi_Petugas=NULL,
        P.Timeout_Sesi_Petugas=NULL
    WHERE P.Sesi_Petugas=Sesi;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Sesi tidak ada';
    END IF;
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PING_SESI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `PING_SESI`(
    IN Sesi CHAR(32), 
    OUT Username VARCHAR(16),
    OUT Timeout DATETIME
)
BEGIN
    DECLARE datetime_now DATETIME;
    
    SET datetime_now = NOW();
    SELECT Username_Petugas INTO Username FROM Petugas WHERE Sesi_Petugas=Sesi AND Timeout_Sesi_Petugas > datetime_now;
    IF (SELECT ROW_COUNT()) = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Sesi tidak ada atau sudah hangus';
    END IF;
    
    SET Timeout = datetime_now + INTERVAL 5 MINUTE;
    UPDATE Petugas SET Timeout_Sesi_Petugas=Timeout WHERE Username_Petugas=Username;
    
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_DETAIL_BARANG` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UPDATE_DETAIL_BARANG`(
    IN Nomor_Barang INTEGER,
    IN Tipe_Barang CHAR(1),
    IN Status_Barang CHAR(1),
    IN Nomor_Peminjaman INTEGER,
    IN Keterangan_Barang VARCHAR(256),
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
proc_label: BEGIN
    DECLARE username VARCHAR(16);
    DECLARE id_riwayat_barang_lama INTEGER;
    DECLARE nomor_peminjaman_lama INTEGER;
    DECLARE status_barang_lama CHAR(1);
    DECLARE keterangan_barang_lama VARCHAR(256);
    DECLARE waktu DATETIME;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    SELECT 
        RB.Id_Riwayat_Barang,
        RB.Status_Barang, 
        RB.Nomor_Peminjaman, 
        RB.Keterangan_Barang 
    INTO 
        id_riwayat_barang_lama,
        status_barang_lama, 
        nomor_peminjaman_lama, 
        keterangan_barang_lama 
    FROM Riwayat_Barang RB 
    WHERE RB.Nomor_Barang=Nomor_Barang
        AND RB.Tipe_Barang=Tipe_Barang
        AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL;
        
    IF Status_Barang = status_barang_lama
        AND Nomor_Peminjaman = nomor_peminjaman_lama
        AND Keterangan_Barang = keterangan_barang_lama THEN
        
        LEAVE proc_label;
    END IF;
        
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang tidak ada';
    END IF;
    
    IF Status_Barang='P' AND Nomor_Peminjaman IS NULL THEN
        SET Nomor_Peminjaman=nomor_peminjaman_lama;
        IF Nomor_Peminjaman IS NULL THEN 
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor peminjaman harus diisi untuk barang berstatus dipinjam';    
        END IF;
    END IF;
    
    IF Status_Barang='P' AND Status_Barang=status_barang_lama THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam.';
    END IF;
    
    IF Status_Barang<>'P' AND Nomor_Peminjaman IS NOT NULL THEN
        SET Nomor_Peminjaman=NULL;
    END IF;
    
    IF Keterangan_Barang IS NULL THEN
        SET Keterangan_Barang=keterangan_barang_lama;
    END IF;
    
    SET waktu=NOW();
    
    
        
        
    IF Status_Barang <> status_barang_lama THEN
        IF Status_Barang = 'P' AND status_barang_lama <> 'A' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang tidak bisa dipinjam';
        END IF;
        IF Status_Barang <> 'A' AND status_barang_lama = 'P' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam';
        END IF;
        
        IF Status_Barang='P' THEN
            IF (
                SELECT P.Status_Peminjaman 
                FROM Peminjaman P
                WHERE P.Nomor_Peminjaman=Nomor_Peminjaman
            ) <> 'A' THEN
                SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak aktif';
            END IF;
        END IF;
            
    ELSEIF Status_Barang='P' AND Nomor_Peminjaman<>nomor_peminjaman_lama THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam';
    END IF;
    
    UPDATE Riwayat_Barang RB 
    SET RB.Waktu_Berakhir_Riwayat_Barang=waktu 
    WHERE RB.Nomor_Barang=Nomor_Barang
        AND RB.Tipe_Barang=Tipe_Barang
        AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL;
    
    INSERT INTO Riwayat_Barang(
        Tipe_Barang,
        Nomor_Barang,
        Username_Petugas,
        Nomor_Peminjaman,
        Status_Barang,
        Keterangan_Barang,
        Waktu_Mulai_Riwayat_Barang
    ) VALUES (
        Tipe_Barang,
        Nomor_Barang,
        username,
        Nomor_Peminjaman,
        Status_Barang,
        Keterangan_Barang,
        waktu
    );
    
    IF Status_Barang = 'A' AND status_barang_lama = 'P' THEN
        UPDATE Peminjaman P
        SET P.Username_Petugas=username,
            P.Waktu_Admin_Peminjaman_Terakhir=NOW()
        WHERE P.Nomor_Peminjaman=nomor_peminjaman_lama;
    END IF;
    
    IF nomor_peminjaman_lama IS NOT NULL AND NOT EXISTS (SELECT * FROM Barang_Terkini BT WHERE BT.Nomor_Peminjaman=nomor_peminjaman_lama) THEN
        UPDATE Peminjaman P
        SET P.Username_Petugas=username,
            P.Waktu_Admin_Peminjaman_Terakhir=NOW(),
            P.Waktu_Kembali=NOW(),
            P.Status_Peminjaman='S'
        WHERE P.Nomor_Peminjaman=nomor_peminjaman_lama;
    END IF;
    COMMIT;
    
END; ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_DETAIL_PEMINJAMAN` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UPDATE_DETAIL_PEMINJAMAN`(
    IN Nomor_Peminjaman INTEGER,
    IN Status_Peminjaman CHAR(1),
    IN Waktu_Harus_Kembali DATETIME,
    IN Keterangan_Peminjaman VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    START TRANSACTION;
    
    UPDATE Peminjaman P
    SET
        P.Status_Peminjaman=Status_Peminjaman,
        P.Keterangan_Peminjaman=Keterangan_Peminjaman,
        P.Waktu_Harus_Kembali=Waktu_Harus_Kembali,
        P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=Nomor_Peminjaman;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak ditemukan';
    END IF;
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_KEPERLUAN_PINJAM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UPDATE_KEPERLUAN_PINJAM`(
    IN Id_Keperluan_Pinjam INTEGER, 
    IN Keperluan_Pinjam VARCHAR(64),
    IN Nama_Penanggung_Jawab VARCHAR(64),
    IN Ruang_Pinjam VARCHAR(16), 
    IN Waktu_Mulai DATETIME,
    IN Waktu_Selesai DATETIME,
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    IF NOT EXISTS(SELECT * FROM Peminjaman P WHERE P.Nomor_Peminjaman=Nomor_Peminjaman) THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak ada';
    END IF;
    
    UPDATE Keperluan_Pinjam KP
    SET KP.Keperluan_Pinjam = Keperluan_Pinjam,
        KP.Nama_Penanggung_Jawab = Nama_Penanggung_Jawab,
        KP.Ruang_Pinjam = Ruang_Pinjam,
        KP.Waktu_Mulai = Waktu_Mulai,
        KP.Waktu_Selesai = Waktu_Selesai
    WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam;
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan pinjam tidak ditemukan';
    END IF;
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_PEMINJAM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UPDATE_PEMINJAM`(
    IN Identitas_Peminjam VARCHAR(18),
    IN Nama_Peminjam VARCHAR(64),
    IN Alamat_Peminjam VARCHAR(256),
    IN Nomor_HP_Peminjam VARCHAR(14),
    IN Keterangan_Blacklist VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Timeout DATETIME
)
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    START TRANSACTION;
    
    UPDATE Peminjam P
    SET
        P.Nama_Peminjam=Nama_Peminjam,
        P.Alamat_Peminjam=Alamat_Peminjam,
        P.Nomor_HP_Peminjam=Nomor_HP_Peminjam,
        P.Keterangan_Blacklist=Keterangan_Blacklist,
        P.Username_Petugas=username,
        P.Waktu_Admin_Peminjam_Terakhir=NOW()
    WHERE P.Identitas_Peminjam=Identitas_Peminjam;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjam tidak ditemukan';
    END IF;
    COMMIT;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `barang_terkini`
--

/*!50001 DROP TABLE IF EXISTS `barang_terkini`*/;
/*!50001 DROP VIEW IF EXISTS `barang_terkini`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `barang_terkini` AS select `riwayat_barang`.`NOMOR_BARANG` AS `Nomor_Barang`,`riwayat_barang`.`TIPE_BARANG` AS `Tipe_Barang`,`riwayat_barang`.`STATUS_BARANG` AS `Status_Barang`,`riwayat_barang`.`NOMOR_PEMINJAMAN` AS `Nomor_Peminjaman`,`riwayat_barang`.`KETERANGAN_BARANG` AS `Keterangan_Barang` from `riwayat_barang` where isnull(`riwayat_barang`.`WAKTU_BERAKHIR_RIWAYAT_BARANG`) */;
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

-- Dump completed on 2019-01-04 22:14:27
