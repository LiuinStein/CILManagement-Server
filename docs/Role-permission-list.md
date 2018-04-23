### 0x00 Introduction

There are 4 roles in this program, **Administer**, **Teacher**, **Project team leader (student)** and **normal member (student)** respectively.



### 0x01 Implement Algorithms

Look at the design of RBAC table in document `Design-of-Schema`

#### 0x00 Password Hash Algorithm

According to [CWE-257](http://cwe.mitre.org/data/definitions/257.html) & [Securing Web Application Technologies[SWAT] Checklist](https://software-security.sans.org/resources/swat), We should use secure hashing techniques with strong algorithms like PBKDF2, bcrypt, or SHA-512, and SHA1, SHA256, and SHA512 are all **fast hashes** and are **bad for passwords**. SCRYPT and BCRYPT are both a *slow hash* and are good for passwords. Always use slow hashes, never fast hashes.

So, we use **BCrypt** algorithm with **11 rounds hash strength** to hash password here.



#### 0x01 Access Control Algorithm

> **Deprecated in design phase:**
>
> Here, we **imitate **the design of **[Linux files permission in numeric mode](https://www.pluralsight.com/blog/it-ops/linux-file-permissions).**
>
> The permissions are broken into **3** sections (attention: Linux is 4).
>
> > Attention: the meaning of the three sections are **different** from it in Linux 
>
> The first section is **for his own**. 
>
> The second section is **for his group (each of the groups you participate in)**.
>
> The third section is **for other users**.
>
> - 0, No permission
> - 1, for reserve,  **for future**, do **NOT** use it before it has exact meanings
> - 2, write
> - 4, read
>
> You basically **add up** the numbers depending on the level of permission you want to give.
>
> **Appointment**:
>
> - for some **one-sided** operations like sign in:
>   the first section is set to **6 means you can do**, set to **0 means you can NOT do**
>   **other value are meaningless**.
>   **the second & third section are set to 0**.
> - ​
>
> **TL;DR**
>
> for example, the permission control of user's personal information
>
> the value 640 means:
>
> - 6: 4+2 you can read & modify your own personal information
> - 4: you can only read the user's info in your project team (each of the groups you participate in)
> - 0: you have no permission for others personal information
>
> the value 444 means:
>
> - 4: you can read your own personal information
> - 4: you can read the member's information of each group you participate in
> - 4: you can read other's information
>
> for another example, sign in
>
> Sign in is a one-sided operation, **we can see it as an operation for sessions**, so if we have permission to sign in, equals we have the permission of reading & writing for the session, so the value 600 means you can sign in, the value 000 means you can not sign in.

