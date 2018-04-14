### 0x00 Objects

The Objects we should manager in this program includes **personnel**, **resources**, **projects**, **project-teams**, **funding and expenditures**.

**Terms:**

| Terms | Comment                                  |
| ----- | ---------------------------------------- |
| GMT   | Greenwich Mean Time (Beijing time is GMT+8) |
| RBAC  | Role-Based Access Control                |

### 0x01 RBAC tables

#### 0x00 User table (t_rbac_user)

| Name     | Type     | key  | Comment                                                      | Example |
| -------- | -------- | ---- | ------------------------------------------------------------ | ------- |
| id       | BIGINT   | PK   | school id                                                    | 151105  |
| password | CAHR(60) |      | use BCrypt, do not use SHA* here, default password is 666666 | -       |
| enabled  | TINYINT  |      | 0 is disabled, 1 otherwise                                   | 0       |

#### 0x01 Role table (t_rbac_role):

| Name | Type        | key  | Comment   | Example |
| ---- | ----------- | ---- | --------- | ------- |
| id   | TINYINT     | PK   | role id   | 1       |
| name | VARCHAR(20) |      | role name | teacher |

#### 0x02 User-Role table (t_rbac_user_role):

| Name    | Type    | key  | Comment        | Example |
| ------- | ------- | ---- | -------------- | ------- |
| id      | INT     | PK   | independent id | 1       |
| user_id | BIGINT  |      | school id      | 151105  |
| role_id | TINYINT |      | role id        | 1       |

#### 0x03 Permission table (t_rbac_permission):

| Name   | Type        | key               | Comment                                                      | Example   |
| ------ | ----------- | ----------------- | ------------------------------------------------------------ | --------- |
| id     | INT         | PK                | permission id                                                | 1         |
| name   | VARCHAR(50) |                   | name                                                         | user info |
| uri    | VARCHAR(75) | Index_1, Unique_1 | request uri                                                  | /v1/user/ |
| method | TINYINT     | Index_1, Unique_1 | http request method, 0 is GET, 1 is HEAD, 2 is POST, 3 is PUT, 4 is PATCH, 5 is DELETE, 6 is OPTIONS, 7 is TRACE | 0         |

#### 0x04 Role-Permission table (t_rbac_role_permission):

| Name          | Type    | key  | Comment        | Example |
| ------------- | ------- | ---- | -------------- | ------- |
| id            | INT     | PK   | independent id | 1       |
| role_id       | TINYINT |      | role id        | 1       |
| permission_id | INT     |      | permission id  | 1       |

> If the role has the permission, insert into this table, otherwise, delete it from this table.

### 0x02 Personnel management

#### 0x00 Personal information table (t_personnel):

| Name        | Type         | key  | Comment                                                      | Example              |
| ----------- | ------------ | ---- | ------------------------------------------------------------ | -------------------- |
| id          | BIGINT       | PK   | school id                                                    | 151105               |
| name        | VARCHAR(30)  |      | member's name                                                | Jack Ma              |
| gender      | TINYINT      |      | 0 is male, 1 is female                                       | 0                    |
| department  | INT          |      | for student that is class id, for teacher & admin that is college id | 101                  |
| enroll_time | DATE         |      | GMT, when did he join the lab                                | 2017-8-15            |
| exit_time   | DATE         |      | GMT, when did he exit the lab, 1970-1-1 will be set if he didn't retire | 2018-2-13            |
| birthday    | DATE         |      | when did he born                                             | 1997-1-1             |
| email       | VARCHAR(30)  |      | E-mail address                                               | test@test.com        |
| phone       | VARCHAR(20)  |      | phone number                                                 | 13512345678          |
| achievement | VARCHAR(400) |      | -                                                            | XXX contest Champion |

> Only **administers** can modify the value of `enroll_time`&`exit_time` fields.

#### 0x01 Class table (t_class):

| Name    | Type        | key  | Comment               | Example  |
| ------- | ----------- | ---- | --------------------- | -------- |
| id      | INT         | PK   | -                     | 101      |
| name    | VARCHAR(20) |      | -                     | 软件1505 |
| college | INT         | FK   | affiliated college id | 58       |

#### 0x02 College table (t_college):

| Name | Type        | key  | Comment | Example    |
| ---- | ----------- | ---- | ------- | ---------- |
| id   | INT         | PK   | -       | 58         |
| name | VARCHAR(30) |      | -       | 计算机科学与技术学院 |

> There is no function in this program to manager school's classes & colleges information.

> If you are a CIL member or want to deploy it for SDUT:
>
> Please ask the **newest** classes & colleges data form the **Network & information center** of Shandong University of Technology.
>
> **Make sure** do **INCREMENTAL** update, do **NOT** rewrite the existed data. Because if you rewrite the existed data, it will change the id field of the existed data which led to the relevant data in other tables **invalid or useless**. 
>
> The incremental update is easy to implement, because the school support the **excel** of new classes' information every semester year.

### 0x03 Project & Team management

#### 0x00 Project table (t_project):

| Name             | Type         | key  | Comment                                                      | Example                    |
| ---------------- | ------------ | ---- | ------------------------------------------------------------ | -------------------------- |
| id               | INT          | PK   | -                                                            | 1                          |
| topic            | VARCHAR(100) |      | -                                                            | Some System Development    |
| description      | VARCHAR(400) |      |                                                              | This system ...            |
| code_uri         | VARCHAR(400) |      | code position, if have no codes, empty string will be set. Every URI occupies one single line. | GitHub URL                 |
| docs_uri         | VARCHAR(400) |      | documents position, if have no documents, empty string will be set. Every URI occupies one single line. | GitHub wiki                |
| leader           | BIGINT       |      | leader id                                                    | 151105                     |
| subject          | INT          |      | the bottom subject id                                        | 46                         |
| funding          | BIGINT       |      | measured in cent                                             | 5000000                    |
| affiliation      | VARCHAR(200) |      | affiliated companies or schools                              | Some company, Some college |
| application_date | DATE         |      | GMT                                                          | 2018/1/1                   |
| start_date       | DATE         |      | GMT                                                          | 2018/2/14                  |
| deadline         | DATE         |      | GMT                                                          | 2018/5/1                   |

#### 0x01 Project team table (t_team):

| Name        | Type         | key  | Comment   | Example      |
| ----------- | ------------ | ---- | --------- | ------------ |
| id          | INT          | PK   | team id   | 1            |
| leader      | BIGINT       |      | leader id | 151105       |
| title       | VARCHAR(30)  |      | team name | C++ team     |
| description | VARCHAR(300) |      | -         | We ...       |
| slogan      | VARCHAR(50)  |      | -         | Attitude ... |

> All of the lab persons contributes a big team called "CIL home"

#### 0x02 Team-personnel table (t_team_personnel):

| Name      | Type         | key  | Comment                                                      | Example      |
| --------- | ------------ | ---- | ------------------------------------------------------------ | ------------ |
| id        | INT          | PK   | independent id                                               | 1            |
| team_id   | INT          |      | team id                                                      | 3            |
| person_id | BIGINT       |      | person id                                                    | 2            |
| position  | TINYINT      |      | 0: leader; 1: deputy leader; 2: teacher; 3: developer; 4: designer; 5: tester; 6: operation and maintenance; 7: artist; 8: DBA; 9: others | 0            |
| jobs      | VARCHAR(300) |      | work content                                                 | XXX features |

#### 0x03 Team-Project table (t_team_project):

| Name       | Type | key  | Comment        | Example |
| ---------- | ---- | ---- | -------------- | ------- |
| id         | INT  | PK   | independent id | 1       |
| team_id    | INT  |      | -              | 3       |
| project_id | INT  |      | -              | 1       |

#### 0x04 Funding's expenditures table (t_expenditure):

| Name         | Type         | key  | Comment                               | Example       |
| ------------ | ------------ | ---- | ------------------------------------- | ------------- |
| id           | INT          | PK   | -                                     | 1             |
| amount       | BIGINT       |      | measure in cent                       | 5000          |
| balance      | BIGINT       |      | funding balance                       | 50000         |
| project_id   | INT          |      | affiliated project id                 | 1             |
| note         | VARCHAR(100) |      | description                           | buy something |
| revenue_date | DATE         |      | when did you spend or earn this money | 2018-2-1      |

> No need for this program to store expenditure time as a `datetime`, because according to project requirements, we only need to roughly compile expenditure and its usage statistics here.

#### 0x05 Academic subjects' table (t_academic_subject):

| Name   | Type        | key  | Comment                                  | Example |
| ------ | ----------- | ---- | ---------------------------------------- | ------- |
| id     | INT         | PK   | -                                        | 1       |
| code   | VARCHAR(15) |      | major code                               | 020301K |
| name   | VARCHAR(50) |      | major name                               | 金融学     |
| parent | INT         |      | parent id, if it have no parent, 0 will be set. | 2       |
| level  | TINYINT     |      | 0 is *学科门类*, 1 is *一级学科*, 2 is *二级学科*  and so on | 2       |

> The code column must be a string due to some disciplines' code has character 'T' (*特设专业*) or 'K' (*国家控制布点专业*).

> This table comply with China's current academic system *学位授予和人才培养学科目录(教育部 学位[2009] 10号)*
>
> There is **no** function in this program to modify this table's content.

### 0x04 Resource & Usage management

#### 0x00 Resources' type table (t_resource_type):

| Name        | Type         | key  | Comment       | Example          |
| ----------- | ------------ | ---- | ------------- | ---------------- |
| id          | INT          | PK   | -             | 1                |
| name        | VARCHAR(100) |      | -             | Dell Laptop X123 |
| description | VARCHAR(300) |      | detail        | i7-4710MQ, ...   |
| disposable  | TINYINT      |      | 0: no, 1: yes | 1                |

#### 0x01 Resource table (t_resource):

| Name         | Type   | key  | Comment                | Example |
| ------------ | ------ | ---- | ---------------------- | ------- |
| id           | INT    | PK   | -                      | 1       |
| type_id      | INT    |      | type                   | 2       |
| purchaser_id | BIGINT |      | who bought it          | 151105  |
| unit_price   | BIGINT |      | unit price at purchase | 50000   |
| remaining    | INT    |      | remaining amount       | 5       |
| quantity     | INT    |      | all quantity           | 6       |

> If this resource is disposable, quantity equals to remaining, if not remaining equals to quantity subtract lending quantity

#### 0x02 Resource usage table (t_resource_usage):

| Name         | Type         | key  | Comment                                                      | Example        |
| ------------ | ------------ | ---- | ------------------------------------------------------------ | -------------- |
| id           | INT          | PK   | -                                                            | 1              |
| resource_id  | INT          |      | -                                                            | 2              |
| user_id      | BIGINT       |      | who use it                                                   | 151105         |
| usage_amount | INT          |      | how many of they use                                         | 5              |
| start_date   | DATE         |      | GMT, when did he use it                                      | 2018-2-1       |
| end_date     | DATE         |      | GMT, when did he return it, 1970-1-1 will be set when it still lending or it's disposable | 1970-1-1       |
| purpose      | VARCHAR(200) |      | why did he use it                                            | compose theory |

