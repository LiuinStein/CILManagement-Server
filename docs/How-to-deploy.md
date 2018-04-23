### 0x00 Introduction

This article introduce how to deploy this program and database with docker.

The whole application based on Ubuntu Linux (15.04 at least) with Docker and MySQL (5.6 at least)

We use maven `docker-maven-plugin` to build server application docker container automatically.

### 0x01 URL Arrangement

> Configure system hosts file by **yourself**!
>
> These URLs are **NOT** accessible in public.
>
> If you are in CIL laboratory and use college's internal network, you can access these URLs directly. (I have configured the router in 9307, if you can't access it in 9307, make sure your PC's **preferred DNS server** is `192.168.1.1`)

#### 0x00 Production Environment

Server Host URL:

```
api.mgr.opencil.cn
```

Database Host URL:

```
db.mgr.opencil.cn
```

####0x01 Development & Test Environment

Server Host URL:

```
api.test.mgr.opencil.cn
```

Database Host URL:

```
db.test.mgr.opencil.cn
```

### 0x02 One-click remote docker deployment

#### 0x00 Configuration

##### Enable docker http remote access

Configure `systemctl` on your docker server

```sh
mkdir /etc/systemd/system/docker.service.d
touch /etc/systemd/system/docker.service.d/http-proxy.conf
```

Add the following text to `http-proxy.conf`

```ini
[Service]

ExecStart=

ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```

Refresh configuration

```sh
systemctl daemon-reload
```

Restart docker daemon

```sh
systemctl restart docker
```

##### Configure DOCKER_HOST in Windows

Open `This PC`->`Advanced system settings`->`Environment Variables`

Choose the `System variables` and click `New`

Fill the input box with Variable name `DOCKER_HOST ` and Variable value `tcp://192.168.50.130:2375`

> Modify the IP address with your remote docker server address

#### 0x01 One-click deployment

##### If you use IntelliJ IDEA as your JAVA IDE

Download this source code file and open it with IntelliJ IDEA

Change the RUN/DEV Configuration drop box to `Docker-Deploy`

Hit the run button and enjoy!

> Change the remote docker server IP address:
>
> 1. Click the RUN/DEV Configuration drop box
> 2. Choose Edit Configurations
> 3. Choose Maven->Docker-Deploy on left bar
> 4. Switch to Runner tab
> 5. Configure the Environment variables add or modify `DOCKER_HOST`
>
> The default environment variables is:
>
> ```
> DOCKER_HOST=tcp://192.168.50.130:2375
> ```

> **The docker container will automatically build and upload to remote docker server.**

##### Otherwise

Open `cmd` or `powershell` in windows or `bash` in Linux

Change the work directory to the source code directory with `cd` command, e.g. ` cd G:\Demo\Java\CILManagement-Server`

Run this command below:

```shell
mvn clean package docker:build -DskipTests
```

Wait a moment and you will see:

```
[INFO] Built management:1.0-SNAPSHOT
[INFO] --------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] --------------------------------------------------------------------
[INFO] Total time: 01:49 min
[INFO] Finished at: 2018-04-22T21:39:01+08:00
[INFO] --------------------------------------------------------------------
```

> The remote docker server IP address based on your Windows Environment Variables, look at the part of `Configure DOCKER_HOST in Windows` on this introduction.

> **The docker container will automatically build and upload to remote docker server.**

#### 0x02 Run docker container

SSH connected to your docker server and run:

```sh
docker images
```

You will see a container named `management`, such as

```
REPOSITORY    TAG              IMAGE ID         CREATED        SIZE
management    1.0-SNAPSHOT    814c14da44a5    29 minutes ago    806 MB
```

Run it:

```sh
docker run -d -p 80:8080 management:1.0-SNAPSHOT
```

Wait a moment and put your server IP address or domain to browser address bar and open it:

If you see

```xml
<RestfulResult>
  <code>404</code>
  <message/>
  <data/>
</RestfulResult>
```

that means the server has been deployed correctly and run perfectly!