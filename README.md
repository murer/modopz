# modopz

[![Build Status](https://circleci.com/gh/murer/modopz.svg?style=shield)](https://circleci.com/gh/murer/modopz)

It let you control your server trough your application. 

 1. You start the modopz server.
   1. By embedding it into your application
   1. By running it (standalone)
 1. You connect to it using the modopz client.
   1. Everthing is transmitted trough HTTP/json.
   1. You can exec long-running process on server and keep tracking it: stdin, stdout, stderr and exitcode.
   2. You can port forward just like ```ssh -L```

# Comming Soon

 1. Other modes of connection to bypass network restrictions like:
   1. Reversed http
   1. IRC
   1. XMPP 
 1. More modules:
   1. Filesystem.
   1. JVM scripting.

# Building

```shell
mvn clean install -Ddist
```

Use ```-Dmaven.test.skip.exec``` on windows to since exec test is depends on ```/bin/bash```

# Getting Started

## Server

### Standalone Server

```shell
java -jar modopz-server/target/modopz-server-dist-single.jar
```

### Server Embedded

Follow this sample: [MOFilter.java](./modopz-server/src/main/java/com/github/murer/modopz/server/MOFilter.java)

## Client

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

