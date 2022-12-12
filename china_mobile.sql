DROP DATABASE IF EXISTS china_mobile;
CREATE DATABASE china_mobile;
use china_mobile;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `china_mobile`.`user`  (
    `id` INT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` varchar(32) UNIQUE NOT NULL,
    `username` varchar(64) UNIQUE NOT NULL,
    `password` varchar(64) NOT NULL,
    `email`    VARCHAR(45) NOT NULL,
    `phone`    VARCHAR(45) NOT NULL,
    `role`     VARCHAR(45) NOT NULL,
    `shamirId` VARCHAR(45)  DEFAULT NULL,
    `shamirKey` VARCHAR(125) DEFAULT NULL,
    `fhe_pk` varchar(128) DEFAULT NULL,
    `fhe_sk` varchar(128) DEFAULT NULL,
    `ecc_pk` varchar(128) DEFAULT NULL,
    `ecc_sk` varchar(128) DEFAULT NULL
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shamir`;
CREATE TABLE `china_mobile`.`shamir`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `shamirId` VARCHAR(45) NOT NULL,
    `shamirKey` VARCHAR(125) NOT NULL,
    `shamirUserKey` INT NOT NULL,
    `approve` VARCHAR(125) Default NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `organization`;
CREATE TABLE `china_mobile`.`organization`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `organization` VARCHAR(45) NOT NULL,
    `address` VARCHAR(45) NOT NULL,
    `phone` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `count` INT NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `fhe_message`;
CREATE TABLE `china_mobile`.`fhe_message`  (
  `id` varchar(32) PRIMARY KEY NOT NULL,
  `cipher_path` varchar(128) NULL DEFAULT NULL,
  `params_path` varchar(128) NULL DEFAULT NULL,
  `reline_path` varchar(128) NULL DEFAULT NULL,
  `op` int(11) NULL DEFAULT NULL,
  `res_path` varchar(128) NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pre_message`;
CREATE TABLE `china_mobile`.`pre_message`  (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `provider_id` varchar(32) NOT NULL,
  `file_id` varchar(64) UNIQUE NOT NULL,
  `href` varchar(64) NULL DEFAULT NULL,
  `suffix` varchar(16) NOT NULL,
  `encrypted_key` varchar(512) NULL DEFAULT NULL,
  `r` varchar(256) NULL DEFAULT NULL,
  `re_key` varchar(128) NULL DEFAULT NULL,
  `reencrypted_key` varchar(128) NULL DEFAULT NULL
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `request`;
CREATE TABLE `china_mobile`.`request`  (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `provider_id` varchar(32) NOT NULL,
  `requester_id` varchar(32) NOT NULL,
  `file_id` varchar(64) NOT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  `valid` tinyint(1) NULL DEFAULT NULL
) ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_key`;
CREATE TABLE `china_mobile`.`user_key`  (
  `id` varchar(32) PRIMARY KEY NOT NULL,
  `fhe_pk` varchar(128) NULL DEFAULT NULL,
  `ecc_pk` varchar(128) NULL DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `abefile`;
CREATE TABLE `china_mobile`.`abefile`  (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `file_address` varchar(128) NOT NULL,
  `encryptedfile_address` varchar(128) NOT NULL,
  `policy_address` varchar(128) NOT NULL
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for aberequest
-- ----------------------------
DROP TABLE IF EXISTS `aberequest`;
CREATE TABLE `china_mobile`.`aberequest`  (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `requester_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  `attribute` varchar(255) NOT NULL,
  `privatekey` varchar(255) NULL DEFAULT NULL
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for abeuser
-- ----------------------------
DROP TABLE IF EXISTS `abeuser`;
CREATE TABLE `china_mobile`.`abeuser`  (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `masterkey` varchar(255) NOT NULL,
  `publickey` varchar(255) NOT NULL
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for eosdata
-- ----------------------------
DROP TABLE IF EXISTS `eosdata`;
CREATE TABLE `eosdata` (
  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `tableid` int(11) DEFAULT NULL,
  `ipfshash` varchar(255) NOT NULL,
  `fileformat` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `filekeyword` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `storagetime` datetime DEFAULT NULL,
  `visitview` int(11) DEFAULT NULL,
  `uplinkstatus` bit(1) DEFAULT b'0',
  `transactionid` varchar(255) DEFAULT NULL,
  `uplinktime` datetime DEFAULT NULL,
  `blocknum` double DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
