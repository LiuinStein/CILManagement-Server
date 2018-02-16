-- ========================================================
--      Module: Database structure of cil management system
--      Author: Shaoqun Liu
--        Time: Feb 17, 2018
--      E-mail: liuinstein@163.com | liuinstein@gmail.com
--     Website: https://www.shaoqunliu.cn
--     license: GPLv3
-- Description: draft implement, Only database structure
-- ========================================================

-- --------------------------------- Database ---------------------------------------------
DROP DATABASE IF EXISTS `cil_management`;
CREATE DATABASE `cil_management` CHARACTER SET 'utf8mb4';
-- --------------------------------- Personnel Management ---------------------------------
-- ----------------------------- personnel table (t_personnel) ----------------------------
DROP TABLE IF EXISTS `cil_management`.`t_personnel`;
CREATE TABLE `cil_management`.`t_personnel`  (
  `id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'member\'s id',
  `name` varchar(30) NOT NULL DEFAULT 'unnamed' COMMENT '\n\nmember\'s name\n',
  `gender` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 is male, 1 is female',
  `department` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '\n\nfor student that is class id, for teacher that is college id\n',
  `enroll_time` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he join the lab',
  `exit_time` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he exit the lab, 1970-1-1 will be set if he didn\'t retire',
  `birthday` date NOT NULL DEFAULT '1970-1-1' COMMENT 'when did he born',
  `email` varchar(30) NOT NULL DEFAULT 'test@test.com' COMMENT 'E-mail address',
  `phone` varchar(20) NOT NULL DEFAULT '13512345678' COMMENT 'phone number',
  `achievement` varchar(400) NOT NULL DEFAULT 'Nothing is here' COMMENT 'personal achievement',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- --------------------------------- class table (t_class) --------------------------------
DROP TABLE IF EXISTS `cil_management`.`t_class`;
CREATE TABLE `cil_management`.`t_class`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'class id',
  `name` varchar(20) NOT NULL DEFAULT 'Test-class' COMMENT 'class name',
  `college` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'affiliated college id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- --------------------------------- college table (t_college) ----------------------------
DROP TABLE IF EXISTS `cil_management`.`t_college`;
CREATE TABLE `cil_management`.`t_college`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'college id',
  `name` varchar(30) NOT NULL DEFAULT 'Test-name' COMMENT 'college name',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


