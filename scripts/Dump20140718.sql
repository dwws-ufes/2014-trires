CREATE DATABASE  IF NOT EXISTS `trires` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `trires`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: trires
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `bibliotecalinkeddata`
--

DROP TABLE IF EXISTS `bibliotecalinkeddata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bibliotecalinkeddata` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `query` longtext,
  `url` varchar(255) DEFAULT NULL,
  `condicaoPalavrasChave` varchar(255) DEFAULT NULL,
  `condicaoResumo` varchar(255) DEFAULT NULL,
  `condicaoTitulo` varchar(255) DEFAULT NULL,
  `isAtivo` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bibliotecalinkeddata`
--

LOCK TABLES `bibliotecalinkeddata` WRITE;
/*!40000 ALTER TABLE `bibliotecalinkeddata` DISABLE KEYS */;
INSERT INTO `bibliotecalinkeddata` VALUES (10,'IEEE Xplore','PREFIX akt:  <http://www.aktors.org/ontology/portal#>\r\n\r\nSELECT *  WHERE { \r\n  ?publicacao akt:has-title ?titulo\r\n         ','http://ieee.rkbexplorer.com/sparql',NULL,NULL,NULL,''),(96,'ACM Library','PREFIX akt:  <http://www.aktors.org/ontology/portal#>\r\n\r\nSELECT *  WHERE { \r\n  ?publicacao akt:has-title ?titulo\r\n         ','http://acm.rkbexplorer.com/sparql',NULL,NULL,NULL,'\0'),(97,'Citeseer','PREFIX akt:  <http://www.aktors.org/ontology/portal#>\r\n\r\nSELECT *  WHERE { \r\n  ?publicacao akt:has-title ?titulo\r\n         ','http://citeseer.rkbexplorer.com/sparql',NULL,NULL,NULL,'\0'),(98,'DBLP Computer Science','PREFIX akt:  <http://www.aktors.org/ontology/portal#>\r\n\r\nSELECT *  WHERE { \r\n  ?publicacao akt:has-title ?titulo\r\n         ','http://dblp.rkbexplorer.com/sparql',NULL,NULL,NULL,'\0');
/*!40000 ALTER TABLE `bibliotecalinkeddata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupotrabalho`
--

DROP TABLE IF EXISTS `grupotrabalho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupotrabalho` (
  `id` bigint(20) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `isAtivo` bit(1) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `palavrasChave` varchar(255) DEFAULT NULL,
  `usuarioLider_id` bigint(20) DEFAULT NULL,
  `dataHoraCriacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_77mbbf2mpkd33810vbxqawcj2` (`usuarioLider_id`),
  CONSTRAINT `FK_77mbbf2mpkd33810vbxqawcj2` FOREIGN KEY (`usuarioLider_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupotrabalho`
--

LOCK TABLES `grupotrabalho` WRITE;
/*!40000 ALTER TABLE `grupotrabalho` DISABLE KEYS */;
INSERT INTO `grupotrabalho` VALUES (0,'Grupo destinado a pesquisas de web semantica.','','Web semântica','Levantamento do estado da arte.','web semantica;linked data',1,'2014-01-01 00:00:00'),(7,'Grupo de pesquisa do Estudo Dirigido 2014/1','','ED I - Interação avançada com usuário','Efetuar revisão sistemática sobre troca de dados em ambientes de interações avançadas com o usuário.','mpeg-u, xml, recognition, interaction, natural interaction, multimodal',3,'2014-02-02 00:00:00'),(8,'Grupo para estudos relacionados a desenvolvimento orientado a modelo','','Dev. Orientado a Modelo 2014/1','Levantar estado da arte','mdd;mde;modelling;conceptual modelling',1,'2014-03-03 00:00:00');
/*!40000 ALTER TABLE `grupotrabalho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupotrabalho_usuario`
--

DROP TABLE IF EXISTS `grupotrabalho_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupotrabalho_usuario` (
  `GrupoTrabalho_id` bigint(20) NOT NULL,
  `usuariosMembros_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_1296730oi2l0bwww5vqlabet` (`usuariosMembros_id`,`GrupoTrabalho_id`),
  KEY `FK_q5xb5d7wr1x3jl79lcclr2rdr` (`GrupoTrabalho_id`),
  CONSTRAINT `FK_1296730oi2l0bwww5vqlabet` FOREIGN KEY (`usuariosMembros_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_q5xb5d7wr1x3jl79lcclr2rdr` FOREIGN KEY (`GrupoTrabalho_id`) REFERENCES `grupotrabalho` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupotrabalho_usuario`
--

LOCK TABLES `grupotrabalho_usuario` WRITE;
/*!40000 ALTER TABLE `grupotrabalho_usuario` DISABLE KEYS */;
INSERT INTO `grupotrabalho_usuario` VALUES (0,1),(0,2),(0,3),(7,2),(7,5),(8,2),(8,5);
/*!40000 ALTER TABLE `grupotrabalho_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (124);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historicobuscatrabalho`
--

DROP TABLE IF EXISTS `historicobuscatrabalho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicobuscatrabalho` (
  `id` bigint(20) NOT NULL,
  `dataHora` datetime DEFAULT NULL,
  `quantidadeTrabalhosEncontrados` int(11) NOT NULL,
  `textoInformado` longtext,
  `grupoTrabalho_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `biblioteca_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oqgpxdta397lbqtrfg4uparpj` (`grupoTrabalho_id`),
  KEY `FK_46agd4bq3oimks8ydqjlg8kqq` (`usuario_id`),
  KEY `FK_69ul7wrw390drp8h68b1oo4c2` (`biblioteca_id`),
  CONSTRAINT `FK_46agd4bq3oimks8ydqjlg8kqq` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK_69ul7wrw390drp8h68b1oo4c2` FOREIGN KEY (`biblioteca_id`) REFERENCES `bibliotecalinkeddata` (`id`),
  CONSTRAINT `FK_oqgpxdta397lbqtrfg4uparpj` FOREIGN KEY (`grupoTrabalho_id`) REFERENCES `grupotrabalho` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historicobuscatrabalho`
--

LOCK TABLES `historicobuscatrabalho` WRITE;
/*!40000 ALTER TABLE `historicobuscatrabalho` DISABLE KEYS */;
INSERT INTO `historicobuscatrabalho` VALUES (11,'2014-06-19 14:53:13',7,'interface',0,1,NULL),(13,'2014-06-19 15:04:18',7,'interface',0,1,NULL),(15,'2014-06-19 15:23:28',7,'interface',0,1,NULL),(16,'2014-06-19 15:28:21',7,'interface',0,1,NULL),(17,'2014-06-19 15:31:32',7,'interface',0,1,NULL),(19,'2014-06-19 15:35:33',7,'interface',0,1,NULL),(23,'2014-06-19 15:36:04',7,'interface',0,1,NULL),(24,'2014-06-19 15:37:36',18,'interface or build',0,1,NULL),(25,'2014-06-19 15:50:08',18,'(interface or build)',0,1,NULL),(26,'2014-06-19 15:52:49',8,'(interface or build) and (models)',0,1,NULL),(27,'2014-06-19 16:04:38',1,'(interface or build) and models',0,1,NULL),(28,'2014-06-19 17:30:36',18,'interface or build',0,1,NULL),(31,'2014-06-19 17:32:03',18,'interface or build',0,1,NULL),(48,'2014-06-19 19:31:47',18,'interface or build\r\n',0,1,NULL),(49,'2014-06-25 09:59:18',0,'multimodal or natural',0,1,10),(50,'2014-06-25 09:59:41',7,'natural or interface',0,1,10),(51,'2014-06-25 10:00:22',7,'(natural or interface)',0,1,10),(52,'2014-06-25 10:01:59',7,'(interface or natural)',0,1,10),(54,'2014-06-25 10:16:59',7,'interface',0,1,10),(55,'2014-06-26 08:36:04',5,'interface and user',0,1,10),(56,'2014-06-26 08:48:30',27,'graph',0,1,10),(57,'2014-06-26 08:58:39',7,'interface',0,1,10),(60,'2014-06-26 08:59:18',7,'interface',0,1,10),(62,'2014-06-26 09:02:09',7,'interface',0,1,10),(66,'2014-06-26 09:04:05',7,'interface',0,1,10),(69,'2014-06-26 09:04:41',7,'interface',0,1,10),(71,'2014-06-26 09:12:14',7,'interface',0,1,10),(72,'2014-06-26 09:17:53',16,'interface or user',0,1,10),(73,'2014-06-26 09:23:20',7,'INTERFACE',0,1,10),(75,'2014-06-26 14:08:58',7,'interface',0,1,10),(76,'2014-06-26 14:11:27',7,'interface',0,1,10),(77,'2014-06-26 14:12:29',7,'interface',0,1,10),(78,'2014-06-26 14:18:36',7,'interface',0,1,10),(79,'2014-06-26 14:22:07',7,'interface',0,1,10),(80,'2014-06-26 14:23:31',7,'interface',0,1,10),(81,'2014-06-26 14:23:32',7,'interface',0,1,10),(82,'2014-06-26 14:23:36',7,'interface',0,1,10),(83,'2014-06-26 14:28:56',27,'graph',0,1,10),(84,'2014-06-26 14:29:13',226,'(graph or model)',0,1,10),(85,'2014-06-26 14:40:11',212,'(model or build)',0,1,10),(87,'2014-06-28 21:53:55',7,'interface',0,1,10),(88,'2014-07-03 09:13:48',0,'semantic and web',0,1,10),(89,'2014-07-03 09:14:14',0,'web and semantic',0,1,10),(90,'2014-07-03 09:14:22',4,'semantic',0,1,10),(93,'2014-07-03 13:23:01',7,'interface',0,1,10),(94,'2014-07-03 13:23:59',5,'(user AND interface) or architeture',0,1,10),(95,'2014-07-10 09:58:18',7,'interface',0,1,10),(99,'2014-07-11 16:05:41',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\nAND\r\n(interface OR (user AND interface))\r\nAND\r\n(sensor OR sensed OR device OR system)\r\nAND\r\n(recognize OR recognition)\r\n',0,1,10),(100,'2014-07-11 16:05:43',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\nAND\r\n(interface OR (user AND interface))\r\nAND\r\n(sensor OR sensed OR device OR system)\r\nAND\r\n(recognize OR recognition)\r\n',0,1,96),(101,'2014-07-11 16:05:43',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\nAND\r\n(interface OR (user AND interface))\r\nAND\r\n(sensor OR sensed OR device OR system)\r\nAND\r\n(recognize OR recognition)\r\n',0,1,97),(102,'2014-07-11 16:05:43',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\nAND\r\n(interface OR (user AND interface))\r\nAND\r\n(sensor OR sensed OR device OR system)\r\nAND\r\n(recognize OR recognition)\r\n',0,1,98),(103,'2014-07-11 16:06:02',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\n',0,1,10),(104,'2014-07-11 16:06:02',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\n',0,1,96),(105,'2014-07-11 16:06:02',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\n',0,1,97),(106,'2014-07-11 16:06:03',0,'(natural OR advanced OR multimodal)\r\nAND\r\n(interaction OR (user AND interaction))\r\n',0,1,98),(107,'2014-07-11 16:06:22',12,'(natural OR advanced OR multimodal)\r\n',0,1,10),(108,'2014-07-11 16:07:44',12,'(natural OR advanced OR multimodal)\r\n',0,1,10),(109,'2014-07-11 16:07:52',12,'(natural OR advanced \r\nOR multimodal)\r\n',0,1,10),(110,'2014-07-11 16:06:25',4021,'(natural OR advanced OR multimodal)\r\n',0,1,96),(112,'2014-07-11 16:30:08',16,'interface or user',0,1,10),(117,'2014-07-17 08:27:31',0,'user and interface',0,1,10),(118,'2014-07-17 08:27:57',0,'user or interface',0,1,10),(119,'2014-07-17 08:28:45',0,'interface',0,1,10),(120,'2014-07-17 08:30:56',0,'interface',0,1,10),(121,'2014-07-17 08:35:27',0,'interface',0,1,10),(122,'2014-07-17 08:48:01',7,'interface',0,1,10);
/*!40000 ALTER TABLE `historicobuscatrabalho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabalho`
--

DROP TABLE IF EXISTS `trabalho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trabalho` (
  `id` bigint(20) NOT NULL,
  `marcadoParaLeitura` int(11) NOT NULL,
  `notaAvaliacao` int(11) NOT NULL,
  `historicoBusca_id` bigint(20) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `autores` longtext,
  `evento` varchar(255) DEFAULT NULL,
  `sameAs` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `resumo` longtext,
  PRIMARY KEY (`id`),
  KEY `FK_ba1qyokaquke2v8b68q7qe6pb` (`historicoBusca_id`),
  CONSTRAINT `FK_ba1qyokaquke2v8b68q7qe6pb` FOREIGN KEY (`historicoBusca_id`) REFERENCES `historicobuscatrabalho` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabalho`
--

LOCK TABLES `trabalho` WRITE;
/*!40000 ALTER TABLE `trabalho` DISABLE KEYS */;
INSERT INTO `trabalho` VALUES (64,1,5,62,'1997','P.A. Syme ; K.M.C. Tan','International Symposium on Fault Tolerant Computing, 1997','http://ieee.rkbexplorer.com/id/publication-6202dd4a2ef544399b6b9c92c1f03e76','MetriStation: A Tool for User-Interface Fault Detection',NULL),(86,1,2,85,'2006','Lihua Yuan ; Jianning Mai ; Chen-Nee Chuah ; Zhendong Su ; P. Mohapatra ; Hao Chen','IEEE Symposium on Security and Privacy, 2006','http://ieee.rkbexplorer.com/id/publication-01624012','FIREMAN: a toolkit for firewall modeling and analysis','Security concerns are becoming increasingly critical in networked systems. Firewalls provide important defense for network security. However, misconfigurations in firewalls are very common and significantly weaken the desired security. This paper introduces FIREMAN, a static analysis toolkit for firewall modeling and analysis. By treating firewall configurations as specialized programs, FIREMAN applies static analysis techniques to check misconfigurations, such as policy violations, inconsistencies, and inefficiencies, in individual firewalls as well as among distributed firewalls. FIREMAN performs symbolic model checking of the firewall configurations for all possible IP packets and along all possible data paths. It is both sound and complete because of the finite state nature of firewall configurations. FIREMAN is implemented by modeling firewall rules using binary decision diagrams (BDDs), which have been used successfully in hardware verification and model checking. We have experimented with FIREMAN and used it to uncover several real misconfigurations in enterprise networks, some of which have been subsequently confirmed and corrected by the administrators of these networks.'),(91,1,4,90,'2005','R.E. Bryant ; M. Christodorescu ; Somesh Jha ; D. Song ; S.A. Seshia','IEEE Symposium on Security and Privacy, 2005','http://ieee.rkbexplorer.com/id/publication-01425057','Semantics-aware malware detection','A malware detector is a system that attempts to determine whether a program has malicious intent. In order to evade detection, malware writers (hackers) frequently use obfuscation to morph malware. Malware detectors that use a pattern-matching approach (such as commercial virus scanners) are susceptible to obfuscations used by hackers. The fundamental deficiency in the pattern-matching approach to malware detection is that it is purely syntactic and ignores the semantics of instructions. In this paper, we present a malware-detection algorithm that addresses this deficiency by incorporating instruction semantics to detect malicious program traits. Experimental evaluation demonstrates that our malware-detection algorithm can detect variants of malware with a relatively low run-time overhead. Moreover our semantics-aware malware detection algorithm is resilient to common obfuscations used by hackers.'),(92,1,1,90,'2002','J. Pereira ; R. Oliveira ; L. Rodrigues','International Conference on Dependable Systems and Networks, 2002','http://ieee.rkbexplorer.com/id/publication-01028913','Reducing the cost of group communication with semantic view synchrony','View synchrony (VS) is a powerful abstraction in the design and implementation of dependable distributed systems. By ensuring that processes deliver the same set of messages in each view, it allows them to maintain consistency across membership changes. However experience indicates that it is hard to combine strong reliability guarantees as offered by VS with stable high performance. In this paper we propose a novel abstraction, semantic view synchrony (SVS), that exploits the application\'s semantics to cope with high throughput applications. This is achieved by allowing some messages to be dropped while still preserving consistency when new views are installed. Thus, SVS inherits the elegance of view synchronous communication. The paper describes how SVS can be implemented and illustrates its usefulness in the context of distributed multi-player games.'),(113,1,5,112,'2007','R.A. Maxion ; R.R.M. Roberts ; K.S. Killourhy ; F. Arshad','International Conference on Dependable Systems and Networks, 2007','http://ieee.rkbexplorer.com/id/publication-04272989','User Discrimination through Structured Writing on PDAs','This paper explores whether features of structured writing can serve to discriminate users of handheld devices such as Palm PDAs. Biometric authentication would obviate the need to remember a password or to keep it secret, requiring only that a user\'s manner of writing confirm his or her identity. Presumably, a user\'s dynamic and invisible writing style would be difficult for an imposter to imitate. We show how handwritten, multi-character strings can serve as personalized, non-secret passwords. A prototype system employing support vector machine classifiers was built to discriminate 52 users in a closed-world scenario. On high-quality data, strings as short as four letters achieved a false-match rate of 0.04%, at a corresponding false non-match rate of 0.64%. Strings of at least 8 to 16 letters in length delivered perfect results--a 0% equal-error rate. Very similar results were obtained upon decreasing the data quality or upon increasing the data quantity.'),(114,1,1,112,'2003','M. Martinello ; M. Kaniche ; K. Kanoun','International Conference on Dependable Systems and Networks, 2003','http://ieee.rkbexplorer.com/id/publication-01209986','A user-perceived availability evaluation of a web based travel agency',''),(115,1,0,112,'2002','M. Merzbacher ; D. Patterson','International Conference on Dependable Systems and Networks, 2002','http://ieee.rkbexplorer.com/id/publication-01028932','Measuring end-user availability on the Web: practical experience','For service applications on a network, measuring availability, performance, and quality of service is critical. Yet traditional software and hardware measures are both inadequate and misleading. Better measures of availability that incorporate end-user experience will lead to meaningful benchmarks and progress in providing high availability services. We present the results of a series of long-term experiments that measured availability of select Web sites and services with the goal of duplicating the end-user experience. Using our measurements, we propose a new metric for availability that goes beyond the traditional sole measure of uptime.'),(116,1,0,112,'2002','A. McIntosh ; S. Dalal ; Yu-Yun Ho ; A. Jain','International Conference on Dependable Systems and Networks, 2002','http://ieee.rkbexplorer.com/id/publication-01029015','Application performance assurance using end-to-end user level monitoring','A new measure of performance, which uses both application integrity and traditional network response time, is proposed. Modern networked application services rely on a stack of network protocols and a host of other services many of which cross-organizational and corporate boundaries. We point out that traditional software quality assurance techniques don\'t scale up for post-deployment integrity checks for such applications and services. A new methodology to do non-stop post-production monitoring of networked application services for transactional integrity and time delay measurement is proposed. Specifically we describe the Telcordia/spl trade/ Application Assurance System, which we have created for measuring real-time performance of web-based applications used in commercial settings. The system measures both post-production application integrity and time delay. The measurements are carried out by sending synthetic end-user transactions and analyzing the responses. Statistical models for analyzing the data using single monitoring site as well as multiple monitoring sites are described. Creating synthetic end-user transactions is crucial for our method. The paper presents a method for generation of \'highly efficient\' end-user transactions from a graphical model of the functionality of the system. Highly efficient transactions are generated using combinatorial designs. The graphical model is incrementally created using a recorder. We give several empirical examples of efficacy of this system and uses for finding performance problems.'),(123,1,0,122,'2005','R.W. Reeder ; K.M.C. Tan','International Conference on Dependable Systems and Networks, 2005','http://ieee.rkbexplorer.com/id/publication-01467780','User interface dependability through goal-error prevention','User interfaces form a critical coupling between humans and computers. When the interface fails, the user fails, and the mission is lost. For example, in computer security applications, human-made configuration errors can expose entire systems to various forms of attack. To avoid interaction failures, a dependable user interface must facilitate the speedy and accurate completion of user tasks. Defects in the interface cause user errors (e.g., goal, plan, action and perception errors), which impinge on speed and accuracy goals, and can lead to mission failure. One source of user error is poor information representation in the interface. This can cause users to commit a specific class of errors - goal errors. A design principle (anchor-based subgoaling) for mitigating this cause was formulated. The principle was evaluated in the domain of setting Windows file permissions. The native Windows XP file permissions interface, which did not support anchor-based subgoaling, was compared to an alternative, called Salmon, which did. In an experiment with 24 users, Salmon achieved as much as a four-fold increase in accuracy for a representative task and a 94% reduction in the number of goal errors committed, compared to the XP interface.');
/*!40000 ALTER TABLE `trabalho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `instituicao` varchar(255) DEFAULT NULL,
  `isAtivo` bit(1) NOT NULL,
  `linkLattes` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `perfilAcesso` int(11) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Aluno de Mestrado','admin@trires.com','IFES','','http://www.google.com','Eduard Wink',0,'abc123'),(2,'Aluno de Mestrado LPRM','sonia@trires.com','IFES','','http://www.google.com','Sonia Albuquerque',1,'abc123'),(3,'Aluno de Mestrado NEMO','guilherme@trires.com','UFES','\0','http://www.google.com','Guilherme Silva',1,'abc123'),(4,'Aluno de Mestrado LCAD','jose@trires.com','UFES','','http://www.google.com','José Antonio Rangel',1,'abc123'),(5,'Professor UFES','figueira@trires.com','UFES','','http://www.google.com','Figueiredo Souza',0,'abc123'),(6,'Aluno de Mestrado NINFA','maria@trires.com','IFES','','http://www.google.com','Maria Eduarda Campos',1,'abc123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-18 16:09:09
