-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.19-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for bluebank
CREATE DATABASE IF NOT EXISTS `bluebank` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bluebank`;

-- Dumping structure for table bluebank.conta
CREATE TABLE IF NOT EXISTS `conta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) NOT NULL DEFAULT '0',
  `agencia` int(11) NOT NULL DEFAULT '0',
  `numero` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `conta_UQ` (`cpf`,`agencia`,`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='É uma conta.';

-- Dumping data for table bluebank.conta: ~3 rows (approximately)
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` (`id`, `cpf`, `agencia`, `numero`) VALUES
	(12, '11111111111111', 1234, 1234),
	(13, '22222222222222', 12345, 12345),
	(15, '22222232222222', 123455, 123455),
	(19, '999', 8888, 7777);
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;

-- Dumping structure for table bluebank.operacao
CREATE TABLE IF NOT EXISTS `operacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) NOT NULL,
  `tipo` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Tabela para lista das operações disponíveis';

-- Dumping data for table bluebank.operacao: ~3 rows (approximately)
/*!40000 ALTER TABLE `operacao` DISABLE KEYS */;
INSERT INTO `operacao` (`id`, `nome`, `tipo`) VALUES
	(1, 'Saque', 1),
	(2, 'Depósito', 2),
	(3, 'Transferência', 3);
/*!40000 ALTER TABLE `operacao` ENABLE KEYS */;

-- Dumping structure for procedure bluebank.realizarDeposito
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `realizarDeposito`(IN idContaDeposito INT, IN valorDeposito DOUBLE, IN idOperacao INT, IN tipoOperacao BIT)
BEGIN
	
	START TRANSACTION;
	
		INSERT INTO transacao VALUES (NULL, 
												idContaDeposito, 
												idContaDeposito,
												valorDeposito, 
												idOperacao,
												tipoOperacao,
												NOW()
												);
	COMMIT;
	
END//
DELIMITER ;

-- Dumping structure for table bluebank.transacao
CREATE TABLE IF NOT EXISTS `transacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idorigem` int(11) NOT NULL,
  `iddestino` int(11) NOT NULL,
  `valor` double NOT NULL,
  `idoperacao` int(11) NOT NULL,
  `tipo_operacao` bit(1) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_transacao_origem_conta_id` (`idorigem`),
  KEY `fk_transacao_destino_conta_id` (`iddestino`),
  KEY `fk_transacao_operacao_operacao_id` (`idoperacao`),
  CONSTRAINT `fk_transacao_destino_conta_id` FOREIGN KEY (`iddestino`) REFERENCES `conta` (`id`),
  CONSTRAINT `fk_transacao_operacao_operacao_id` FOREIGN KEY (`idoperacao`) REFERENCES `operacao` (`id`),
  CONSTRAINT `fk_transacao_origem_conta_id` FOREIGN KEY (`idorigem`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- Dumping data for table bluebank.transacao: ~3 rows (approximately)
/*!40000 ALTER TABLE `transacao` DISABLE KEYS */;
INSERT INTO `transacao` (`id`, `idorigem`, `iddestino`, `valor`, `idoperacao`, `tipo_operacao`, `data`) VALUES
	(1, 12, 12, 200, 1, b'1', '2017-01-29 19:12:01'),
	(2, 12, 12, 1000, 1, b'1', '2017-01-29 19:11:49'),
	(3, 12, 12, 600, 1, b'0', '2017-01-29 19:13:21'),
	(5, 12, 12, 500, 1, b'1', '2017-01-29 21:00:02'),
	(6, 13, 12, 200, 3, b'1', '2017-01-29 21:42:21'),
	(7, 12, 13, 200, 3, b'0', '2017-01-29 21:42:21'),
	(8, 12, 13, 100, 3, b'1', '2017-01-29 21:48:12'),
	(9, 13, 12, 100, 3, b'0', '2017-01-29 21:48:13'),
	(10, 12, 12, 600, 1, b'1', '2017-01-29 22:25:12'),
	(11, 12, 12, 600, 1, b'1', '2017-01-29 22:25:12'),
	(12, 12, 12, 600, 1, b'1', '2017-01-29 22:25:12'),
	(13, 12, 12, 600, 1, b'1', '2017-01-29 22:25:35'),
	(14, 12, 12, 600, 1, b'1', '2017-01-29 22:25:35'),
	(15, 12, 12, 600, 1, b'1', '2017-01-29 22:25:35'),
	(16, 12, 12, 600, 1, b'1', '2017-01-29 22:25:35'),
	(17, 12, 12, 1, 1, b'1', '2017-01-29 22:26:24'),
	(18, 12, 12, 1, 1, b'1', '2017-01-29 22:26:28'),
	(19, 12, 12, 1, 1, b'1', '2017-01-29 22:26:28'),
	(20, 12, 12, 1, 1, b'1', '2017-01-29 22:26:38'),
	(21, 12, 12, 1, 1, b'1', '2017-01-29 22:26:38'),
	(22, 12, 12, 1, 1, b'1', '2017-01-29 22:26:38'),
	(23, 12, 12, 1, 1, b'1', '2017-01-29 22:34:55'),
	(24, 12, 12, 1, 1, b'1', '2017-01-29 22:34:57'),
	(25, 12, 12, 1, 1, b'1', '2017-01-29 22:34:57'),
	(26, 12, 12, 1, 1, b'1', '2017-01-29 22:34:59'),
	(27, 12, 12, 1, 1, b'1', '2017-01-29 22:34:59'),
	(28, 12, 12, 1, 1, b'1', '2017-01-29 22:34:59'),
	(29, 13, 12, 10, 3, b'1', '2017-01-29 23:27:40'),
	(30, 12, 13, 10, 3, b'0', '2017-01-29 23:27:40');
/*!40000 ALTER TABLE `transacao` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
