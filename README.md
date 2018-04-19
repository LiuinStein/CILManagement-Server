# CIL Management System Server

This is the server-side program of CIL Management System.

CIL Management System is an internal program for the Co-innovation Laboratory of Shandong University of Technology Computer Science and Technology college.

Related technologies:

* **Spring MVC** for web MVC
* **Spring Rest** for view object Restful presentation
* **Spring Security** for access control & authorization
* **Spring Session** for distributed session management
* **AspectJ** for AOP and support to data source transaction
* **Redis+lettuce** for the memory storage of session
* **MyBatis**+**MySQL** for database persistence
* **MyBatis Generator** for creating entities code
* **Hibernate Validator** for data validation checking
* **Lambda** for [functional programming](https://flyingbytes.github.io/programming/java8/functional/part0/2017/01/16/Java8-Part0.html)
* **Tomcat JDBC Pool** for database connection pooling
* **Activiti** for workflow
* **Apache POI** for import or export to Excel
* **Fast Json (made by Alibaba)** for Json parser
* **Gson (made by Google)** for Json-Object transition
* **JQuery** & **Ajax** for front-end & data transmission
* **JUnit** for test
* **Maven** for package management
* **Git** for versioning
* **Tomcat** as a web container
* **Java9**: minimal Java platform is Java 8, recommend to Java 9

Some technologies that have been considered, but not used:

* **Spring Web flow**: has been replaced by **Activiti**
* **Shiro:** is not supporting to **OAuth2**, so it has been replaced by **Spring Security** in this program.
* **DBCP2:** Why do I use `Tomcat jdbc pool` to replace DBCP2?
  * **Commons DBCP is over 60 classes. `tomcat-jdbc-pool` core is 8 classes**, hence modifications for future requirement will require much less changes. This is all you need to run the connection pool itself. 
  * **Commons DBCP uses static interfaces**. This means you **have to use the right version for a given JRE version** or you may see `NoSuchMethodException` exceptions.
  * `Tomcat jdbc pool` is a Tomcat module, it **depends on Tomcat JULI**, a simplified logging framework used in Tomcat.
  * For more details, look at [the introduction part to The Tomcat JDBC Connection Pool](https://tomcat.apache.org/tomcat-9.0-doc/jdbc-pool.html)
* **Jedis:** is not thread-safe and [look at this article](https://github.com/spring-projects/spring-session/issues/789), so it has been replaced by **lettuce** in this program
* **Redis connection pool:** As we all know, the lettuce will only create one connection per instance by default, [it supports connection pooling](https://github.com/lettuce-io/lettuce-core/wiki/Connection-Pooling), but is it necessary to have one for this program? no, the answer here is no. Redis user-space operations are **single-threaded** so there is **no need for throughput reasons to have more than one connection**. A single thread can execute about 10,000 Redis commands (`GET`, `SET`) per second. Using 6 to 8 threads this number rapidly goes up to 80kOps/sec to 100kOps/sec. For more details [look at this article from lettuce-core issues](https://github.com/lettuce-io/lettuce-core/issues/360)

Further development:

* **Spring HATEOAS** for creating REST representations that follow the HATEOAS principle
* **Spring Security for OAuth2** for OAuth2 authorization & get rid of the usage of session (related dependencies has been imported in current version)

> **Java is the engine then Spring is the fuel**.

## 0x00 Documents

You can find all of the document in the `docs` directory.

Design-of-Schema.md

> This document provides the design of database structure & the data dictionaries.

How-to-deploy.md

> The purpose of this file is pretty self-evident in its name. The method of deployment of the server-side program

json-API.md

> The Restful Json API document includes how to invoke it & the example input and output data.

Role-permission-list.md

> A series of table indicates the relationship between every-role and every-privilege in every sub-system.

## 0x01 Versioning

### 0x00 Branches

**master:**

Master branch stores the current published version.

**dev:**

Dev branch used to follow up development progress. 

When some features development and test finished, merge it into master branch.

### 0x01 Pull Requests

When a phase finished or reached a milestone, the developer issue a pull request from branch `dev` to `master`. The comment formatting of pull request is here:

```
Step N finished: Something has done
```

### 0x02 Issues

When someone found a problem, open an issue, and then the developer or the maintainer will reply to it within 72 hours.

The communication between internal developers or maintainer, use E-MAIL, not QQ, not WeChat.

## 0x02 Directories structure

```shell
G:.                                          
├─.idea        # IDE's configuration files                              
│  ├─artifacts                               
│  ..OMITTED..                          
├─database     # database structure and example data
│  └─example-data                            
├─docs # project relevant documents                                       
├─src  # code dicrectory                                      
│  └─main                                    
│      ├─java    # all of the java codes                            
│      │  ├─cn                               
│      │  │  └─opencil          # main package             
│      │  │      ├─controller   # Spring controller                
│      │  │      │  ├─authorization # controller for URI /v1/auth/*
│      │  │      │  ├─error     # error handling controller
│      │  │      │  ├─project   # controller for URI /v1/project/*
│      │  │      │  │  └─team   # controller for URI /v1/team/*
│      │  │      │  ├─resource  # controller for URI /v1/resource/*
│      │  │      │  └─user      # controller for URI /v1/user/*
│      │  │      ├─exception    # exceptions form controller             
│      │  │      ├─mapper       # mybatis mapper interfaces             
│      │  │      ├─po           # persistant object         
│      │  │      ├─security     # for Spring Security             
│      │  │      ├─service      # service layer             
│      │  │      │  └─implementation    # service implementations
│      │  │      ├─validation   # validation tools
│      │  │      │  └─group     # validation equivalence groups
│      │  │      │      └─database # database validation equivalence group
│      │  │      └─vo           # value object             
│      │  └─com                              
│      │      └─shaoqunliu     # shaoqunliu common libraries
│      │          ├─reflection # shaoqunliu reflection libraries
│      │          ├─rest       # shaoqunliu Restful libraries
│      │          ├─security   # shaoqunliu security libraries
│      │          │  ├─rwx     # RWX file permission control libraries
│      │          │  └─util    # shaoqunliu common security utils 
│      │          ├─utils
│      │          └─validation # shaoqunliu validation libraries
│      │              ├─annotation   # validation annotations
│      │              ├─exception    # validation exceptions  
│      │              └─validator    # validators
│      │                  └─database # database validation libraries
│      ├─resources       # the resources & configrations file
│      │  ├─activiti     # activiti bpmn files
│      │  ├─mapper       # mybatis mapper                    
│      │  └─spring       # Spring application context                    
│      └─webapp                           
│          └─WEB-INF     # web.xml is in here                    
└─target      # server target files                             
    ├─classes                                
    ├─ ..OMITTED..   
```

> Generated by `tree` command on Windows PowerShell
>
> Introduction was manually wrote after Hashtag(#)

