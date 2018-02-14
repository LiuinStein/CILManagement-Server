### 0x00 Subjects

The subjects we should manager in this program includes **personnel**, **resources**, **projects**, **project-teams**, **funding and expenditure**, **disciplines**.

**Terms:**

| Terms | Comment                                  |
| ----- | ---------------------------------------- |
| GMT   | Greenwich Mean Time (Beijing time is GMT+8) |
|       |                                          |



### 0x01 Personnel Management

**personnel table (t_personnel):**

| Name        | Type         | key  | Comment                                  | Example              |
| ----------- | ------------ | ---- | ---------------------------------------- | -------------------- |
| id          | INT          | PK   | member's id                              | 151105               |
| name        | VARCHAR(30)  |      | member's name                            | Jack Ma              |
| gender      | TINYINT      |      | 0 is male, 1 is female                   | 0                    |
| department  | INT          |      | for student that is class id, for teacher that is college id | 101                  |
| enroll_time | DATE         |      | GMT, when did he join the lab            | 2017-8-15            |
| exit_time   | DATE         |      | GMT, when did he exit the lab, 1970-1-1 will be set if he didn't retire | 2018-2-13            |
| birthday    | DATE         |      | when did he born                         | 1997-1-1             |
| email       | VARCHAR(30)  |      | E-mail address                           | test@test.com        |
| phone       | VARCHAR(20)  |      | phone number                             | 13512345678          |
| achievement | VARCHAR(400) |      | -                                        | XXX contest Champion |

**class table (t_class):**

| Name    | Type        | key  | Comment               | Example       |
| ------- | ----------- | ---- | --------------------- | ------------- |
| id      | INT         | PK   | -                     | 101           |
| name    | VARCHAR(10) |      | -                     | Software-1505 |
| college | INT         |      | affiliated college id | 58            |

**college table (t_college):**

| Name | Type        | key  | Comment | Example                                 |
| ---- | ----------- | ---- | ------- | --------------------------------------- |
| id   | INT         | PK   | -       | 58                                      |
| name | VARCHAR(30) |      | -       | computer science and technology college |

**project table (t_project):**

| Name             | Type         | key  | Comment                                  | Example                    |
| ---------------- | ------------ | ---- | ---------------------------------------- | -------------------------- |
| id               | INT          | PK   | -                                        | 1                          |
| topic            | VARCHAR(100) |      | -                                        | Some System Development    |
| description      | VARCHAR(400) |      |                                          | This system ...            |
| code_uri         | VARCHAR(400) |      | code's position, if have no codes, empty string will be set. Every URI occupies one single line. | GitHub URL                 |
| docs_uri         | VARCHAR(400) |      | documents position, if have no documents, empty string will be set. Every URI occupies one single line. | GitHub wiki                |
| leader           | INT          |      | leader id                                | 151105                     |
| discipline       | INT          |      | the bottom discipline id                 | 46                         |
| funding          | INT          |      | measured in cent                         | 5000000                    |
| affiliation      | VARCHAR(200) |      | affiliated companies or schools          | Some company, Some college |
| application_date | DATE         |      | GMT                                      | 2018/1/1                   |
| start_date       | DATE         |      | GMT                                      | 2018/2/14                  |
| deadline         | DATE         |      | GMT                                      | 2018/5/1                   |

**project team table (t_team):**

| Name        | Type         | key  | Comment   | Example      |
| ----------- | ------------ | ---- | --------- | ------------ |
| id          | INT          | PK   | team id   | 1            |
| leader      | INT          |      | leader id | 151105       |
| title       | VARCHAR(30)  |      | team name | C++ team     |
| description | VARCHAR(300) |      | -         | We ...       |
| slogan      | VARCHAR(50)  |      | -         | Attitude ... |

> All of the lab persons contributes a big team called "CIL home"

**team-personnel table (t_team_personnel):**

| Name      | Type         | key  | Comment                                  | Example      |
| --------- | ------------ | ---- | ---------------------------------------- | ------------ |
| id        | INT          | PK   | independent id                           | 1            |
| team_id   | INT          |      | team id                                  | 3            |
| person_id | INT          |      | person id                                | 2            |
| position  | TINYINT      |      | 0: leader; 1: deputy leader; 2: teacher; 3: developer; 4: designer; 5: tester; 6: operation and maintenance; 7: artist; 8: DBA; 9: others | 0            |
| jobs      | VARCHAR(300) |      | work content                             | XXX features |

**team-project table (t_team_project):**

| Name       | Type | key  | Comment        | Example |
| ---------- | ---- | ---- | -------------- | ------- |
| id         | INT  | PK   | independent id | 1       |
| team_id    | INT  |      | -              | 3       |
| project_id | INT  |      | -              | 1       |

**expenditures table (t_expenditure):**

| Name       | Type    | key  | Comment                | Example |
| ---------- | ------- | ---- | ---------------------- | ------- |
| id         | INT     | PK   | -                      | 1       |
| flow       | TINYINT |      | 1: income; -1: outcome | 1       |
| amount     | BIGINT  |      | measure in cent        | 5000    |
| balance    | BIGINT  |      | funding balance        | 50000   |
| project_id | INT     |      | affiliated project     | 1       |

