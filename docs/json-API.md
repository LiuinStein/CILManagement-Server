### 0x00 Introduction

Here is the client request and the server response agreements and examples of Json-API.

The design of Json API comply with **Rest API standard**.

We **only support JSON-formatted request data**, meanwhile, we **both support JSON-formatted and XML-formatted response data**, which one will be responded depends on your request header property `Accept`. In this introduction, we only use the JSON-formatted response data as an example.

I just consult the tutorial of Restful API, in some case, **I didn't strictly abide by this standard**, for example, I didn't add the user-id or username to the URI, I don't like this design, truly, I think the user-id or username should be a part of input data and I do it in my code. 

For a Restful API design, the request should be **stateless**, that means each request from client to server must contain all of the information necessary to understand the request, and cannot take advantage of any stored context on the server, all of the states is kept entirely on the client, so we can't use the session in server, but in this program, I use session (only for Spring Security to store security contexts), this violate one of the six basic guiding principles of REST, so **this API should NOT be named as a Restful API**, just a **Restful-like API**, O(∩_∩)O haha~

In future design, we will consider the **OAuth2 or JWT** to replace the session and current authorization method. Meanwhile, this also one of the reasons for why I'm using the Spring Security instead of Shiro.

> Reference:
>
> Guiding Principles of REST:
>
> https://restfulapi.net/
>
> Rest HTTP Methods:
>
> https://restfulapi.net/http-methods/
>
> Rest HTTP Status Codes: 
>
> https://restfulapi.net/http-status-codes/
>
> REST Resource Naming Guide:
>
> https://restfulapi.net/resource-naming/

#### 0x00 Host URL

```
api.mgr.opencil.cn
```

> DO NOT USE IP ADDRESS DIRECTLY IN ANY CASE!
>
> For more URL arrangement information, look at the second part `0x01 URL Arrangement` of document `How-to-deploy.md`

#### 0x01 Universal HTTP request headers

If you want the server to response the JSON-formatted data:

```
Content-Type: application/json
Accept: application/json
```

If you want the server to response the XML-formatted data:

```
Content-Type: application/json
Accept: application/xml
```

#### 0x02 Server Response Data Formatting

JSON-Formatting

```json
{
    "code": 0,
    "message": "something was happened",
    "data": {
        
    }
}
```

XML-Formatting

```xml
<RestfulResult>
    <code>0</code>
    <message>something was happened</message>
    <data></data>
</RestfulResult>
```

The names and its meanings of this default fields

* `code` integer: error code
  * 0 means success
  * non-zero integer means failure
* `message` string: error message or server status message
* `data` array: result data  

#### 0x03 Universal default Error Response Code

**Input invalid or type mismatched data:**

```http
HTTP/1.1 400 BAD REQUEST
```

**Session out of date or invalid, authorization failed, need re-login:**

```http
HTTP/1.1 401 Unauthorized
```

**Higher permission required:**

```http
HTTP/1.1 403 Forbidden
```

**Server error occurred:**

```http
HTTP/1.1 500 INTERNAL SERVER ERROR
```

#### 0x04 Data formatting contract

The pure date:

> `yyyy-MM-dd` for example: 1997-10-21

The date-time:

> `yyyy-MM-dd HH:mm:ss` for example: 1997-10-21 15:06:33

### 0x01 Personnel Management

#### 0x00 Sign in 

```http
POST /v1/user/session/ HTTP/1.1
```

##### Input

```json
{
  "username":15110506001,
  "password":"666666"
}
```

##### Output

**Sign in success:**

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "log in success",
    "data": {
        "auth": "[admin]"
    }
}
```

The `auth` field of data is the permission of just logged-in user.

**Account doesn't exist:**

```http
HTTP/1.1 404 NOT FOUND
```

```json
{
    "code": 1,
    "message": "someone was not found.",
    "data": {}
}
```

**Password error:**

```http
HTTP/1.1 401 Unauthorized
```

```json
{
    "code": 1,
    "message": "Bad credentials",
    "data": {}
}
```

#### 0x01 Sign out

```http
DELETE /v1/user/session/ HTTP/1.1
```

##### Input

Nothing

##### Output

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x02 Sign up

```http
POST /v1/user/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001,
  "name":"Jack Ma",
  "password":"666666",
  "enabled":true,
  "gender":0,
  "role":1,
  "department":1,
  "enroll_time":"2018-2-22"
}
```

> The above is the minimal input data set.
>
> For more fields that you want to set when you sign up, 

##### Output

Sign up success:

```http
HTTP/1.1 201 CREATED
```

#### 0x03 Delete an account 

```http
DELETE /v1/user/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001
}
```

> The default `admin` user with id `10001` can not be deleted!

##### Output

Delete success:

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x04 Modify member's info

```http
PUT /v1/user/info/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001,
  "name":"Jack Ma",
  "gender":0,
  "identify":0,
  "department":101,
  "enroll_time":"2017-08-15",
  "exit_time":"1970-01-01",
  "birthday":"1997-11-01",
  "email":"test@test.com",
  "phone":"13512345678",
  "achievement":"some achievements"
}
```
> **Only send those fields that need to be modified!**

> Only **administers** can modify the value of `enroll_time`&`exit_time` fields. If **others** submit that, it would be **ignored**.

> The `id` field indicates whose information will be change, if it not equals to the logged-in user_id, the administer's privilege will be required.

##### Output

Modify success:

```http
HTTP/1.1 201 CREATED
```
The id field is not equals to the logged-in user id & he is not an administer:

```http
HTTP/1.1 403 Forbidden
```

#### 0x05 Modify password

```http
PUT /v1/user/password/ HTTP/1.1
```

##### Input

```json
{
  "old_password":"old_password",
  "new_password":"new_password"
}
```

##### Output

Modify success:

```http
HTTP/1.1 201 CREATED
```

Old password error:

```http
HTTP/1.1 403 Forbidden
```

#### 0x06 Initialize password

```http
PATCH /v1/user/password/ HTTP/1.1
```

> The default password is 666666.

##### Input

```json
{
  "id":15110506001
}
```

##### Output

Modify success:

```http
HTTP/1.1 201 CREATED
```

Non-admin user submit data:

```http
HTTP/1.1 403 Forbidden
```

#### 0x07 Query member's info 

```http
GET /v1/user/info?mode={m}&condition={c}&value={v} HTTP/1.1
```

##### Input

by user's id (only for exact query):

```http
GET /v1/user/info?mode=summary&condition=id&value=15110506001  HTTP/1.1
```
by user's name (only for exact query):

```http
GET /v1/user/info?mode=all&condition=name&value=Jack%20Ma  HTTP/1.1
```

by user's department:

```http
GET /v1/user/info?mode=summary&condition=department&value=101  HTTP/1.1
```

> The mode field controls which data are returned.
>
> | When it is | Returned fields                                              |
> | ---------- | ------------------------------------------------------------ |
> | summary    | id, name, gender, department                                 |
> | all        | id, name, gender, department, enroll-time, exit-time, birthday, email, phone, achievement |
> |            |                                                              |

##### Output

Query success response header & return data:

```http
HTTP/1.1 200 OK
```
```json
{
  "code":0,
  "message":"",
  "data":{
    "users":[
      {
        "id":15110506001,
        "name":"Jack Ma",
        "gender":0,
        "department":101
      },
      {
        "id":15110506002,
        "name":"Pony Ma",
        "gender":0,
        "department":201
      }
    ]
  }
}
```

Query success but no matched person:

```http
HTTP/1.1 404 NOT FOUND
```

#### 0x08 Enable/Disable an account

```http
PATCH /v1/user/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001,
  "enabled":false
}
```

> The default admin user with id 10001 can not be disable!

##### Output

```http
HTTP/1.1 201 CREATED
```

```json
{
    "code": 0,
    "message": "Account has been disabled!",
    "data": {}
}
```

### 0x02 Authorization Management

#### 0x00 Grant some permissions to a role

```http
PUT /v1/auth/role/permission/ HTTP/1.1
```

##### Input

```json
{
  "role":1,
  "permission":1
}
```

> The role `administer` or `admin` have the superior permission, anyone can't grant or revoke permissions to or form him.

##### Output

Grant permission success

```http
HTTP/1.1 201 CREATED
```

```json
{
    "code": 0,
    "message": "granted success",
    "data": {}
}
```

#### 0x01 Revoke permissions from role

```http
DELETE /v1/auth/role/permission/ HTTP/1.1
```

##### Input

```json
{
  "role":1,
  "permission":1
}
```

> The role `administer` have the superior permission, anyone can't grant or revoke permissions to or form him.

##### Output

Revoke permission success

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x02 Assign role to somebody

```http
POST /v1/auth/user/role/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001,
  "role":1
}
```

> If the assignee's current role is `admin` or the new role is `admin`, then the assign operation will first take back his current role and reassign the new role.
>
> Otherwise, the new role will be added to the user.

##### Output

```http
HTTP/1.1 201 CREATED
```

```json
{
    "code": 0,
    "message": "role assign successfully",
    "data": {}
}
```

#### 0x03 Take back a role from someone

```http
DELETE /v1/auth/user/role/ HTTP/1.1
```

##### Input

```json
{
  "id":15110506001,
  "role":1
}
```

> Allow someone has no role, then, the account is equals to an anonymous user or a guest account.

##### Output

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x04 Add a new role

```http
POST /v1/auth/role/ HTTP/1.1
```

##### Input

```json
{
  "role_name":"some role"
}
```

> The server will refuse the request of add a role that named `admin`.

##### Output

Role add successfully

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "role add successfully",
    "data": {}
}
```

#### 0x05 Delete a role

```http
DELETE /v1/auth/role/ HTTP/1.1
```

##### Input

```json
{
  "role":1
}
```

> The server will refuse the request of delete the `admin` role.

##### Output

Role delete successfully

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x06 Rename a role

```http
PUT /v1/auth/role/ HTTP/1.1
```

##### Input

```json
{
  "role":4,
  "role_name":"other name"
}
```

> The server will refuse the request of rename the `admin` role.

##### Output

Rename successfully

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "role rename successfully",
    "data": {}
}
```

#### 0x07 Query roles

```http
GET /v1/auth/role?condition={c}&value={v} HTTP/1.1
```

##### Input

by role id:

```http
GET /v1/auth/role?condition=id&value=1 HTTP/1.1
```

by role name:

```http
GET /v1/auth/role?condition=name&value=admin HTTP/1.1
```

by user id:

```http
GET /v1/auth/role?condition=user_id&value=15110506001 HTTP/1.1
```

by permission id:

```http
GET /v1/auth/role?condition=permission&value=1 HTTP/1.1
```

##### Output

Query success response header & return data:

```http
HTTP/1.1 200 OK
```

```json
{
    "code": 0,
    "message": "",
    "data": {
        "roles": [
            {
                "id": 1,
                "name": "admin"
            },
            {
                "id": 2,
                "name": "teacher"
            }
        ]
    }
}
```

### 0x03 Project & Team Management

#### 0x00 Add a project

```http
POST /v1/project/ HTTP/1.1
```

##### Input

```json
{
  "topic":"the name of a big project worthed 2 billion dollars",
  "leader":15110506001,
  "subject":1,
  "funding":50000,
  "application_date":"2018-01-06",
  "start_date":"2018-02-05",
  "deadline":"2018-09-01"
}
```

> The monetary unit of funding is cent!

##### Output

Project creation success

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "new project created",
    "data": {}
}
```

#### 0x01 Modify project information

```http
PUT /v1/project/ HTTP/1.1
```

##### Input

```json
{
  "id":3,
  "topic":"a name",
  "leader":15110506001,
  "subject":1,
  "funding":50000,
  "code_uri":"https://github.com/LiuinStein",
  "application_date":"2018-01-06",
  "start_date":"2018-02-05",
  "deadline":"2018-09-01"
}
```

> **Only send those fields that need to be modified!**

> All available fields name look at the design of schema, table `t_project`

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "project information has been changed",
    "data": {}
}
```

#### 0x02 Delete a project

```http
DELETE /v1/project/ HTTP/1.1
```

##### Input

```json
{
  "id":1
}
```

##### Output

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x03 Query projects

```http
GET /v1/project? HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



#### 0x04 Organize a team 

```http
POST /v1/team/ HTTP/1.1
```

##### Input

```json
{
  "leader":15110506001,
  "title":"a wonderful team",
  "description":"we can do a big project that worthed 2 billion dollars",
  "slogan":"do a project"
}
```

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "new team created",
    "data": {}
}
```

#### 0x05 Modify team information

```http
PUT /v1/team/ HTTP/1.1
```

##### Input

```json
{
  "id":1,
  "leader":15110506001,
  "title":"a wonderful team",
  "description":"we can do a big project that worthed 2 billion dollars",
  "slogan":"do a project"
}
```

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "team information has been changed",
    "data": {}
}
```

#### 0x06 Dissolve a team

```http
DELETE /v1/team/ HTTP/1.1
```

##### Input

```json
{
  "id":1
}
```

##### Output

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x07 Query a team

```http
GET /v1/team? HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



#### 0x08 Add a member to a team

```http
POST /v1/team/member/ HTTP/1.1
```

##### Input

```json
{
  "team_id":2,
  "person_id":15110506001,
  "position":9,
  "jobs":"do something"
}
```

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "new member added",
    "data": {}
}
```

#### 0x09 Kick out a man from a team

```http
DELETE /v1/team/member/ HTTP/1.1
```

##### Input

```json
{
  "team_id":2,
  "person_id":15110506001
}
```

##### Output

```http
HTTP/1.1 204 NO CONTENT
```

#### 0x0A Modify someone's job or position

```http
PUT /v1/team/member/ HTTP/1.1
```

##### Input

```json
{
  "team_id":2,
  "person_id":15110506001,
  "position":5,
  "jobs":"do a lot of things"
}
```

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "member position & job info has been changed",
    "data": {}
}
```

#### 0x0B Query team members

```http
GET /v1/team/member? HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



#### 0x0C Assign a project to a team

```http
POST /v1/team/project/ HTTP/1.1
```

##### Input

```json
{
  "team_id":2,
  "project_id":3
}
```

##### Output

```http
HTTP/1.1 201 Created
```

```json
{
    "code": 0,
    "message": "project has been assigned successfully",
    "data": {}
}
```

#### 0x0D Take back a project from a team

```http
DELETE /v1/team/project/ HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



#### 0x0E Income/Outcome a sum of money 

```http
POST /v1/project/funding/ HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



#### 0x0F Query expenditures

```http
GET /v1/project/funding/ HTTP/1.1
```

##### Input

```json

```

##### Output

```http

```



### 0x04 Resource & Usage Management



### 0x05 Error code

The error code for Json-API `code` field.

| Code | Description                 |
| :--: | :-------------------------- |
|  0   | success                     |
|  1   | universal server error code |
|  2   | universal client error code |