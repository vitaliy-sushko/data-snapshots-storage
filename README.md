### Simple data snapshot storage

#### Prerequisites
1. JDK 1.8
1. Docker

#### Commands:

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
  