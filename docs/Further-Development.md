### 0x00 Introduction

This article included some concepts conceived at the designing phase or coding phase. We wrote it in this file and hoped the future developers to achieve this and for the future reference.

There are two parts here, the first one is issues that mean maybe it will cause troubles on the high-concurrency or high-throughput situations, the second one is some advice on performance optimizations or other situations.

### 0x01 Issues



### 0x02 Advices

#### 0x00 OAuth2 support

The original intention of the usage of Spring Security instead of Shiro is that Spring Security is native supported OAuth2

1. Get rid of the usage of session
2. Provide additional security

#### 0x01 Restful HATEOAS

I have a deep love in Restful API and I want to build a more powerful and useful APIs. 

An example of Restful HATEOAS:

```
https://api.github.com/
```

