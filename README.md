data-snapshot-storage
==============

[![Build Status](https://travis-ci.org/vitaliy-sushko/data-snapshots-storage.svg?branch=master)](https://travis-ci.org/vitaliy-sushko/data-snapshots-storage)

Prerequisites
==============

1. JDK 1.8
1. Docker

How to build and run
==============

It is assumed that ass command will be run from project directory.

To start redis with sentinel:
```bash
docker-compose -f container/docker-compose.yml up -d
```

To shutdown redis with sentinel:
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
  
PS Test coverage is very low due to luck of time.
   That is my personal omission, as I started late and did not have much time till deadline.
   I'll try to increase test coverage in next few days as a personal exercise     