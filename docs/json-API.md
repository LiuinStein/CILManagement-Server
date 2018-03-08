### 0x00 Introduction

Here is the client request and the server response agreements and examples of Json-API.

The design of Json API comply with **Restful API standard**. 

I just consult the tutorial of Restful API, in some case, **I didn't strictly abide by this standard**, for example, I didn't add the user-id or username to the URI, I don't like this design, truly, I think the user-id or username should be a part of input data and I do in my code. The tutorial tells me to consider OAuth, I'm not, I use the session, about the usage of session and cookie, I wrote an article and listed a table in the file `Usage-of-Session-and-Cookie.md`

> Reference:
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

#### 0x00 Host URL:

```
api.mgr.opencil.cn
```

> DO NOT USE IP ADDRESS DIRECTLY IN ANY CASE!
>
> For more URLs information, please refer to the second part `0x01 URL Arrangement` of document `How-to-deploy.md`

#### 0x01 Universal HTTP request headers:

```
Content-Type: application/json
Cache-Control: no-cache
```

#### 0x02 Universal default Error Response

**Input invalid or type mismatched data:**

```http
HTTP/1.1 400 INVALID REQUEST
```

**Session out of date or invalid, authorization failed, need re-login:**

```http
HTTP/1.1 401 Unauthorized
Content-Length: None
```

**Operators has no authority to do this:**

```http
HTTP/1.1 403 Forbidden
```

**Server error occurred:**

```http
HTTP/1.1 500 INTERNAL SERVER ERROR
Content-Length: 0
```

**Error message (MAY BE returned only when status code is like `4xx`):**

```json
{
  "error": "Some Error messages"
}
```

### 0x01 Personnel Management

#### 0x00 Sign in 

```http
POST /v1/user/session/ HTTP/1.1
```

**Input:**

```json
{
  "username":"test_name",
  "password":"666666"
}
```

**Output:**

Sign in success:

```http
HTTP/1.1 201 Created
Content-Length: 0
```

Account doesn't exist:

```http
HTTP/1.1 404 NOT FOUND
Content-Length: None
```

Password error:

```http
HTTP/1.1 401 Unauthorized
Content-Length: None
```

#### 0x01 Sign out

```http
DELETE /v1/user/session/ HTTP/1.1
```

**Input:**

Nothing

**Output:**

```http
HTTP/1.1 204 NO CONTENT
Content-Length: None
```

#### 0x02 Sign up

```http
POST /v1/user/ HTTP/1.1
```

**Input:**

```json
{
  "id":15110506001,
  "name":"Jack Ma",
  "password":"666666",
  "gender":0,
  "identify":0,
  "department":1,
  "enroll_time":"2018-2-22"
}
```

**Output:**

Sign up success:

```http
HTTP/1.1 201 CREATED
Content-Length: None
```

#### 0x03 Delete an account 

```http
DELETE /v1/user/ HTTP/1.1
```

**Input:**

```json
{
  "the-man-who-forgot-password":15110506001
}
```

**Output:**

Delete success:

```http
HTTP/1.1 204 NO CONTENT
Content-Length: None
```

#### 0x04 Modify member's info

```http
PUT /v1/user/info/ HTTP/1.1
```

**Input:**

```json
{
  "id":15110506001,
  "name":"Jack Ma",
  "gender":0,
  "identify":0,
  "department":101,
  "enroll_time":"2017-8-15",
  "exit_time":"1970-1-1",
  "birthday":"1997-1-1",
  "email":"test@test.com",
  "phone":"13512345678",
  "achievement":"some achievements"
}
```
> **Only send those fields that need to be modified!**

> Only **administers** can modify the value of `enroll_time`&`exit_time` fields. If **others** submit that, it would be **ignored**.

> The user_id in URL indicate whose information will be change, if it not equals to the logged-in user_id, the administer's privilege will be required.

**Output:**

Modify success:

```http
HTTP/1.1 201 CREATED
Content-Length: None
```
The id field is not equals to the logged-in user id & he is not an administer:

```http
HTTP/1.1 403 Forbidden
Content-Length: 0
```

#### 0x05 Modify password

```http
PUT /v1/user/password/ HTTP/1.1
```

**Input:**

```json
{
  "old_password":"old_password",
  "new_password":"new_password"
}
```

**Output:**

Modify success:

```http
HTTP/1.1 201 CREATED
Content-Length: None
```

Old password error:

```http
HTTP/1.1 403 Forbidden
Content-Length: 0
```

#### 0x06 Initialize password

```http
PATCH /v1/user/password/ HTTP/1.1
```

> The default password is 666666.

**Input:**

```json
{
  "the-man-who-forgot-password":15110506001
}
```

**Output:**

Modify success:

```http
HTTP/1.1 201 CREATED
Content-Length: None
```

Non-admin user submit data:

```http
HTTP/1.1 403 Forbidden
Content-Length: 0
```

#### 0x07 Query member's info 

```http
GET /v1/user/info?mode={m}&condition={c}&value={v} HTTP/1.1
```

**Input:**

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

by user's identify:

```http
GET /v1/user/info?mode=summary&condition=identify&value=0  HTTP/1.1
```

> The mode field controls which data are returned.
>
> | When it is | Returned fields                          |
> | ---------- | ---------------------------------------- |
> | summary    | id, name, gender, identify, department   |
> | all        | id, name, gender, identify, department, enroll-time, exit-time, birthday, email, phone, achievement |
> |            |                                          |

**Output:**

Query success & there is return data:

```http
HTTP/1.1 200 OK
```
```json
{
  "users":[
    {
      "id":15110506001,
      "name":"Jack Ma",
      "gender":0,
      "identify":0,
      "department":101
    },
    {
      "id":15110506002,
      "name":"Pony Ma",
      "gender":0,
      "identify":0,
      "department":201
    }
  ]
}
```

Query success but no matched person:

```http
HTTP/1.1 404 NOT FOUND
Content-Length: 0
```


### 0x02 Project & Team Management





### 0x03 Resource & Usage Management





