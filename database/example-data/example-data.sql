-- ========================================================
--      Module: Example data
--      Author: Shaoqun Liu
--        Time: March 4, 2018
--      E-mail: liuinstein@163.com | liuinstein@gmail.com
--     Website: https://www.shaoqunliu.cn
--     license: GPLv3
-- Description:               !!! ATTENTION !!!
--              !!! IMPORT example data will CLEAR ALL tables !!!
--              !!! please BACKUP your data BEFORE import     !!!
-- ========================================================

-- --------------------------- CLEAR TABLES ---------------------------------
TRUNCATE TABLE `cil_management`.`t_rbac_user`;
TRUNCATE TABLE `cil_management`.`t_rbac_role`;
TRUNCATE TABLE `cil_management`.`t_rbac_user_role`;
TRUNCATE TABLE `cil_management`.`t_rbac_permission`;
TRUNCATE TABLE `cil_management`.`t_rbac_role_permission`;

-- --------------------------- example users --------------------------------
-- all example default password is 666666
INSERT INTO `cil_management`.`t_rbac_user` (`id`, `password`, `enabled`)
  VALUE (10001, '$2a$11$j.hn1ti0Pnm.4D54jDyBRuJQmCPDOIqBmcse6Q7.5OOlEGz1ui/tC', 1);
INSERT INTO `cil_management`.`t_rbac_user` (`id`, `password`, `enabled`)
  VALUE (1203001, '$2a$11$Ii3YzhvEWnh8J0jcjR1l7eBHE8G1HXxEPIMluL3ntfY0mrty7Oxvq', 1);
INSERT INTO `cil_management`.`t_rbac_user` (`id`, `password`, `enabled`)
  VALUE (15110506001, '$2a$11$oOgh68N2X2DpzC93H.XAa.HdPlaTak6IQujcDqiSOml7eRGLhdbwO', 1);
INSERT INTO `cil_management`.`t_rbac_user` (`id`, `password`, `enabled`)
  VALUE (15110506002, '$2a$11$MOe2cDisBZJ0i9YC91aIie/Dai//PLkU3zCnHnXIrU07xYeJSMVku', 1);

-- --------------------------- example roles --------------------------------
INSERT INTO `cil_management`.`t_rbac_role` (`id`, `name`) VALUE (1, 'admin');
INSERT INTO `cil_management`.`t_rbac_role` (`id`, `name`) VALUE (2, 'teacher');
INSERT INTO `cil_management`.`t_rbac_role` (`id`, `name`) VALUE (3, 'team leader');
INSERT INTO `cil_management`.`t_rbac_role` (`id`, `name`) VALUE (4, 'member');

-- --------------------------- example user's role --------------------------
INSERT INTO `cil_management`.`t_rbac_user_role` (`id`, `user_id`, `role_id`) VALUE (1, 10001, 1);
INSERT INTO `cil_management`.`t_rbac_user_role` (`id`, `user_id`, `role_id`) VALUE (2, 1203001, 2);
INSERT INTO `cil_management`.`t_rbac_user_role` (`id`, `user_id`, `role_id`) VALUE (3, 15110506001, 3);
INSERT INTO `cil_management`.`t_rbac_user_role` (`id`, `user_id`, `role_id`) VALUE (4, 15110506002, 4);

-- --------------------------- example permissions --------------------------
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (1, 'Sign in', '/v1/user/session', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (2, 'Sign out', '/v1/user/session/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`) VALUE (3, 'Sign up', '/v1/user/', 2);

-- --------------------------- example role's permissions -------------------
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (1, 1, 1);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (2, 1, 2);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (3, 2, 1);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (4, 2, 2);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (5, 3, 1);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (6, 3, 2);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (7, 4, 1);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (8, 4, 2);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (9, 1, 3);

-- --------------------------- example users' info --------------------------------
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (10001, 'Super admin', 0, 19, '2017-6-1', '1970-1-1', '1997-10-21', 'liuinstein@163.com', '15612341234', 'nothing');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (1203001, 'test teacher', 0, 19, '2011-5-6', '1970-1-1', '1987-5-3', 'abc@abc.com', '15689774565', '4 SCI and 3 EI');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (15110506001, 'test team leader', 0, 707, '2017-6-1', '1970-1-1', '1997-10-21', 'liuinstein@163.com', '15612341234', 'Advanced Math scored 99 points');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (15110506002, 'test team member', 0, 707, '2017-6-1', '1970-1-1', '1997-10-21', 'test@test.com', '12345678920', 'nothing');

