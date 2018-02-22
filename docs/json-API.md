### 0x00 Introduction

Here is the client request and the server response agreements and examples of Json-API.

The design of Json API comply with **Restful API standard**. 

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

**Operator have no authority to do this or need sign in:**

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
    "error": "Test Error"
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
HTTP/1.1 202 Accepted
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
HTTP/1.1 202 Accepted
Content-Length: None
```

#### 0x03 Delete an account 

```http
DELETE /v1/user/ HTTP/1.1
```

**Input:**

```json
{
  "id":15110506001
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

```

**Input:**

```json

```
**Output:**

```http

```
#### 0x05 Query member's info 

```http

```

**Input:**

```json

```
**Output:**

```http

```
#### 0x06 Manage school's classes and colleges

```http

```

**Input:**

```json

```
**Output:**

```http

```




### 0x02 Project & Team Management





### 0x03 Resource & Usage Management





