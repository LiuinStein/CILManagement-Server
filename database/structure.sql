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
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '\n\nmember\'s name\n',
  `gender` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 is male, 1 is female',
  `department` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '\n\nfor student that is class id, for teacher that is college id\n',
  `enroll_time` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he join the lab',
  `exit_time` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he exit the lab, 1970-1-1 will be set if he didn\'t retire',
  `birthday` date NOT NULL DEFAULT '1970-1-1' COMMENT 'when did he born',
  `email` varchar(30) NOT NULL DEFAULT '' COMMENT 'E-mail address',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT 'phone number',
  `achievement` varchar(400) NOT NULL DEFAULT '' COMMENT 'personal achievement',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ----------------------------- class table (t_class) ------------------------------------
DROP TABLE IF EXISTS `cil_management`.`t_class`;
CREATE TABLE `cil_management`.`t_class`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'class id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT 'class name',
  `college` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'affiliated college id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ----------------------------- college table (t_college) --------------------------------
DROP TABLE IF EXISTS `cil_management`.`t_college`;
CREATE TABLE `cil_management`.`t_college`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'college id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT 'college name',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- --------------------------------- Project & Team Management ----------------------------
-- ------------------------------- project table (t_project) ------------------------------
DROP TABLE IF EXISTS `cil_management`.`t_project`;
CREATE TABLE `cil_management`.`t_project`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'project id',
  `topic` varchar(100) NOT NULL DEFAULT '' COMMENT 'project topic',
  `description` varchar(400) NOT NULL DEFAULT '' COMMENT 'project abstract',
  `code_uri` varchar(400) NOT NULL DEFAULT '' COMMENT 'code\'s position, if have no codes, empty string will be set. Every URI occupies one single line.',
  `docs_uri` varchar(400) NOT NULL DEFAULT '' COMMENT 'documents position, if have no documents, empty string will be set. Every URI occupies one single line.',
  `leader` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'leader id',
  `discipline` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'the bottom discipline id',
  `funding` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'measured in cent',
  `affiliation` varchar(200) NOT NULL DEFAULT '' COMMENT 'affiliated companies or schools',
  `application_date` date NOT NULL DEFAULT '1970-1-1' COMMENT 'format as GMT',
  `start_date` date NOT NULL DEFAULT '1970-1-1' COMMENT 'format as GMT',
  `deadline` date NOT NULL DEFAULT '1970-1-1' COMMENT 'format as GMT',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- project team table (t_team) ----------------------------
DROP TABLE IF EXISTS `cil_management`.`t_team`;
CREATE TABLE `cil_management`.`t_team`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'team id',
  `leader` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'leader id',
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT 'team name, all of the lab persons contributes a big team called "CIL home"',
  `description` varchar(300) NOT NULL DEFAULT '' COMMENT 'about project team ',
  `slogan` varchar(50) NOT NULL DEFAULT '' COMMENT 'team slogan',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- team-personnel table (t_team_personnel) ----------------
DROP TABLE IF EXISTS `cil_management`.`t_team_personnel`;
CREATE TABLE `cil_management`.`t_team_personnel`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'table\'s independent id',
  `team_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'team id',
  `person_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '\n\nperson id\n',
  `position` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0: leader; 1: deputy leader; 2: teacher; 3: developer; 4: designer; 5: tester; 6: operation and maintenance; 7: artist; 8: DBA; 9: others',
  `jobs` varchar(300) NOT NULL DEFAULT '' COMMENT '\n\nwork content\n',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- team-project table (t_team_project) --------------------
DROP TABLE IF EXISTS `cil_management`.`t_team_project`;
CREATE TABLE `cil_management`.`t_team_project`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'table\'s independent id',
  `team_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'team id',
  `project_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'project id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- funding's expenditures table (t_expenditure) -----------
DROP TABLE IF EXISTS `cil_management`.`t_expenditure`;
CREATE TABLE `cil_management`.`t_expenditure`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'table\'s independent id',
  `flow` tinyint NOT NULL DEFAULT 1 COMMENT '1: income; -1: outcome',
  `amount` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'measure in cent',
  `balance` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'funding balance\n',
  `project_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '\n\naffiliated project id \n',
  `note` varchar(100) NOT NULL DEFAULT '' COMMENT 'description',
  `revenue_date` date NOT NULL COMMENT 'when did you spend or earn this money',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- --------------------------------- Resource & Usage management --------------------------
-- ------------------------------- resources's type table (t_resource_type) ---------------
DROP TABLE IF EXISTS `cil_management`.`t_resource_type`;
CREATE TABLE `cil_management`.`t_resource_type`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'table\'s independent id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT 'resource name',
  `description` varchar(300) NOT NULL DEFAULT '' COMMENT 'resource detail and configuration',
  `disposable` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0: no, 1: yes',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- resource table (t_resource) ----------------------------
DROP TABLE IF EXISTS `cil_management`.`t_resource`;
CREATE TABLE `cil_management`.`t_resource`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'resource id',
  `type_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'type',
  `purchaser_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'who bought it',
  `unit_price` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'unit price at purchase',
  `remaining` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'remaining amount\n',
  `quantity` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'all quantity, If this resource is disposable, quantity equals to remaining, if not remaining equals to quantity subtract lending quantity',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;
-- ------------------------------- resource usage table (t_resource_usage) ----------------
DROP TABLE IF EXISTS `cil_management`.`t_resource_usage`;
CREATE TABLE `cil_management`.`t_resource_usage`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'table\'s independent id',
  `resource_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'resource id',
  `user_id` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'who use it',
  `usage_amount` int UNSIGNED NOT NULL DEFAULT 0 COMMENT 'how many of they use',
  `start_date` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he use it',
  `end_date` date NOT NULL DEFAULT '1970-1-1' COMMENT 'GMT, when did he return it, 1970-1-1 will be set when it still lending or it\'s disposable\n',
  `purpose` varchar(200) NOT NULL DEFAULT '' COMMENT 'why did he use it',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


