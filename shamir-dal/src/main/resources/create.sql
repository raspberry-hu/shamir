create DATABASE shamir;
DROP TABLE user;
CREATE TABLE `shamir`.`user`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `email`    VARCHAR(45) NOT NULL,
    `phone`    VARCHAR(45) NOT NULL,
    `role`     VARCHAR(45) NOT NULL,
    `shamirId` VARCHAR(45)  NOT NULL,
    `shamirKey` VARCHAR(125) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `shamir`.`shamir`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `shamirId` VARCHAR(45) NOT NULL,
    `shamirKey` VARCHAR(125) NOT NULL,
    `shamirUserKey` INT NOT NULL,
    PRIMARY KEY (`id`),
    foreign key (`shamirUserKey`) references `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `shamir`.`organization`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `organization` VARCHAR(45) NOT NULL,
    `address` VARCHAR(45) NOT NULL,
    `phone` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `count` INT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

