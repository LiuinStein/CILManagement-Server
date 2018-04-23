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
TRUNCATE TABLE `cil_management`.`t_personnel`;
TRUNCATE TABLE `cil_management`.`t_project`;
TRUNCATE TABLE `cil_management`.`t_team`;
TRUNCATE TABLE `cil_management`.`t_team_personnel`;
TRUNCATE TABLE `cil_management`.`t_team_project`;
TRUNCATE TABLE `cil_management`.`t_expenditure`;
TRUNCATE TABLE `cil_management`.`t_resource_type`;
TRUNCATE TABLE `cil_management`.`t_resource`;
TRUNCATE TABLE `cil_management`.`t_resource_usage`;

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
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (3, 'Sign up', '/v1/user/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (4, 'Delete an account', '/v1/user/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (5, 'Modify member\'s info', '/v1/user/info/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (6, 'Modify password', '/v1/user/password/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (7, 'Initialize password', '/v1/user/password/', 4);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (8, 'Query member\'s info', '/v1/user/info', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (9, 'Enable or disable an account', '/v1/user/', 4);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (10, 'Grant some permissions to a role', '/v1/auth/role/permission/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (11, 'Revoke permissions from role', '/v1/auth/role/permission/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (12, 'Assign role to somebody', '/v1/auth/user/role/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (13, 'Take back a role from someone', '/v1/auth/user/role/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (14, 'Add a new role', '/v1/auth/role/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (15, 'Delete a role', '/v1/auth/role/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (16, 'Rename a role', '/v1/auth/role/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (17, 'Query roles', '/v1/auth/role', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (18, 'Add a project', '/v1/project/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (19, 'Modify project information', '/v1/project/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (20, 'Delete a project', '/v1/project/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (21, 'Query projects', '/v1/project', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (22, 'Organize a team', '/v1/team/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (23, 'Modify team information', '/v1/team/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (24, 'Dissolve a team', '/v1/team/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (25, 'Query a team', '/v1/team', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (26, 'Add a member to a team', '/v1/team/member/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (27, 'Kick out a man from a team', '/v1/team/member/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (28, 'Modify someone''s job or position', '/v1/team/member/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (29, 'Query team members', '/v1/team/member', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (30, 'Assign a project to a team', '/v1/team/project/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (31, 'Take back a project from a team', '/v1/team/project/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (32, 'Income/Outcome a sum of money', '/v1/project/funding/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (33, 'Query expenditures', '/v1/project/funding', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (34, 'Add a type of resource', '/v1/resource/type/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (35, 'Delete a type of resource', '/v1/resource/type/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (36, 'Modify resource type properties', '/v1/resource/type/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (37, 'Query resource types', '/v1/resource/type', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (38, 'Add a resource', '/v1/resource/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (39, 'Modify resource info', '/v1/resource/', 3);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (40, 'Delete a resource', '/v1/resource/', 5);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (41, 'Query resource info', '/v1/resource', 0);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (42, 'Rent or give back some resources', '/v1/resource/usage/', 2);
INSERT INTO `cil_management`.`t_rbac_permission` (`id`, `name`, `uri`, `method`)
  VALUE (43, 'Query resource usage info', '/v1/resource/usage', 0);

-- --------------------------- example role's permissions -------------------
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (1, 1, 1);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (2, 1, 2);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (3, 1, 3);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (4, 1, 4);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (5, 1, 5);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (6, 1, 6);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (7, 1, 7);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (8, 1, 8);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (9, 1, 9);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (10, 1, 10);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (11, 1, 11);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (12, 1, 12);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (13, 1, 13);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (14, 1, 14);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (15, 1, 15);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (16, 1, 16);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (17, 1, 17);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (18, 1, 18);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (19, 1, 19);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (20, 1, 20);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (21, 1, 21);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (22, 1, 22);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (23, 1, 23);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (24, 1, 24);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (25, 1, 25);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (26, 1, 26);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (27, 1, 27);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (28, 1, 28);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (29, 1, 29);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (30, 1, 30);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (31, 1, 31);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (32, 1, 32);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (33, 1, 33);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (34, 1, 34);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (35, 1, 35);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (36, 1, 36);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (37, 1, 37);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (38, 1, 38);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (39, 1, 39);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (40, 1, 40);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (41, 1, 41);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (42, 1, 42);
INSERT INTO `cil_management`.`t_rbac_role_permission` (`id`, `role_id`, `permission_id`) VALUE (43, 1, 43);


-- --------------------------- example users' info --------------------------------
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (10001, 'Super admin', 0, 19, '2017-6-1', '1970-1-1', '1997-10-21', 'liuinstein@163.com', '15612341234', 'nothing');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (1203001, 'test teacher', 0, 19, '2011-5-6', '1970-1-1', '1987-5-3', 'abc@abc.com', '15689774565', '4 SCI and 3 EI');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (15110506001, 'test team leader', 0, 707, '2017-6-1', '1970-1-1', '1997-10-21', 'liuinstein@163.com', '15612341234', 'Advanced Math scored 99 points');
INSERT INTO `cil_management`.`t_personnel` (id, name, gender, department, enroll_time, exit_time, birthday, email, phone, achievement)
  VALUE (15110506002, 'test team member', 0, 707, '2017-6-1', '1970-1-1', '1997-10-21', 'test@test.com', '12345678920', 'nothing');

