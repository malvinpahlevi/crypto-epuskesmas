-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for javaenterpriseapplication
CREATE DATABASE IF NOT EXISTS `javaenterpriseapplication` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `javaenterpriseapplication`;


-- Dumping structure for table javaenterpriseapplication.rumah_sakit
CREATE TABLE IF NOT EXISTS `rumah_sakit` (
  `kode` varchar(6) NOT NULL DEFAULT '',
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `koordinat` varchar(50) DEFAULT NULL,
  `kecamatan` varchar(50) DEFAULT NULL,
  `kabupaten` varchar(50) DEFAULT NULL,
  `propinsi` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telp` varchar(50) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `kepemilikan` varchar(12) DEFAULT NULL,
  `jenis` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table javaenterpriseapplication.rumah_sakit: ~2 rows (approximately)
/*!40000 ALTER TABLE `rumah_sakit` DISABLE KEYS */;
INSERT INTO `rumah_sakit` (`kode`, `nama`, `alamat`, `koordinat`, `kecamatan`, `kabupaten`, `propinsi`, `email`, `telp`, `type`, `kepemilikan`, `jenis`) VALUES
	('RS01', 'RSUD Panembahan Senopati', 'Jl. Dr. Wahidin Sudirohusodo Bantul', '7.79302777777778', 'Bantul', 'Bantul', 'Yogyakarta', 'rsudps@bantulkab.go.id', '0274 - 367381', 'A', 'Rumah Sakit', 'Kebidanan'),
	('RS02', 'RS Rajawali Citra', 'Jl. PLERET, Banguntapan, Bantul, Daerah Istimewa Yogyakarta 55196, Indonesia', '071100,0000', 'Banyumas', 'Bantul', 'Daerah Istimewa Yogyakarta', 'rsu_rajawalicitra@yahoo.com', '62 274 9240754', 'A', 'Swasta', 'Umum');
/*!40000 ALTER TABLE `rumah_sakit` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
