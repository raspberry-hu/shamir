# create DATABASE china_mobile;
# DROP TABLE IF EXISTS `user`;
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

CREATE TABLE `china_mobile`.`shamir`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `shamirId` VARCHAR(45) NOT NULL,
    `shamirKey` VARCHAR(125) NOT NULL,
    `shamirUserKey` INT NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `china_mobile`.`organization`
(
    `id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `organization` VARCHAR(45) NOT NULL,
    `address` VARCHAR(45) NOT NULL,
    `phone` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `count` INT NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;