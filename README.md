# modopz

[![Build Status](https://circleci.com/gh/murer/modopz.svg?style=shield)](https://circleci.com/gh/murer/modopz)

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
