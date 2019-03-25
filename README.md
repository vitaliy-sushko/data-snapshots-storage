### Simple data snapshot storage

#### Prerequisites
1. JDK 1.8
1. Docker

To start redis with sentinel (from project directory):
```bash
docker-compose -f container/docker-compose.yml up -d
```

To shutdown redis with sentinel (from project directory):
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

To run test request (from project directory):
```bash
./test_requests/curl.sh
```
  