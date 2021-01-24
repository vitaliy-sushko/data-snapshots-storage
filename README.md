data-snapshot-storage
==============

[![Build Status](https://travis-ci.org/vitaliy-sushko/data-snapshots-storage.svg?branch=master)](https://travis-ci.org/vitaliy-sushko/data-snapshots-storage)

Prerequisites
==============

1. JDK 11
1. Docker

How to build and run application
==============

It is assumed that all commands will be run from project directory.

To start redis with sentinel:
```bash
docker-compose -f container/docker-compose.yml up -d
```

To shutdown redis and sentinel:
```bash
docker-compose -f container/docker-compose.yml down
```

To build application:
```bash
./gradlew clean assemble
``` 

To start application:
```bash
./gradlew bootRun
``` 

To run test requests:
```bash
./test_requests/curl.sh
```
