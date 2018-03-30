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

**Operators has no authority to do this:**

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

> The user_id in URL indicate whose information will be change, if it not equals to the logged-in user_id, the administer's privilege will be required.

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

Query success & there is return data:

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

### 0x02 Authorization Management

#### 0x00 Grant some permissions to some role



#### 0x01 Revoke permissions from some role



#### 0x02 Assign a new role to somebody



#### 0x03 Add a new role



#### 0x04 delete a role



### 0x03 Project & Team Management



### 0x04 Resource & Usage Management



### 0x05 Error code

The error code for Json-API `code` field.

| Code | Description                 |
| :--: | :-------------------------- |
|  0   | success                     |
|  1   | universal server error code |
|  2   | universal client error code |