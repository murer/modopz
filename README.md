# modopz

[![Build Status](https://circleci.com/gh/murer/modopz.svg?style=shield)](https://circleci.com/gh/murer/modopz)

It let you can control your server trough your application. 

 1. You start the modopz server.
   1. By embedding it into you application
   1. By running it alone
 1. You connect to it using the modopz client.
   1. Everthing is transmitted trough HTTP/json (for now).
   1. You can exec long-running process on server.
   2. You can port forward just like ```shh -L```

# Building

```shell
mvn clean install -Ddist
```

Use ```-Dmaven.test.skip.exec``` on windows to since exec test is depends on ```/bin/bash```

# Server

## Standalone Server

```shell
java -jar modopz-server/target/modopz-server-dist-single.jar
```

## Server Embedded

Follow this sample: [MOFilter.java](./modopz-server/src/main/java/com/github/murer/modopz/server/MOFilter.java)

# Client

### Standalone Client

### Exec

```shell
java -cp modopz-server/target/modopz-server-dist-single.jar \
   '-Dmodopz.http.url=http://localhost:8765/s/modopz' \
   '-Dmodopz.process.cmd={\"cmds\":[\"/bin/bash\",\"-e\"]}'
```

### Port Foward

```shell
java -cp modopz-server/target/modopz-server-dist-single.jar \
    '-Dmodopz.http.url=http://localhost:8765/s/modopz' \
    '-Dmodopz.socketforward=5000:irc.freenode.net:6667,5001:irc.freenode.net:7000'
```

