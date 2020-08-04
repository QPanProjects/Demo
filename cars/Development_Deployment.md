# Development

## Database, MySQL

Spring Data

- [Data type](https://docs.oracle.com/cd/E19159-01/819-3672/gbxjk/index.html)
- [javax.persistence](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [JPA Repository](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html)

## Server, JAVA, Spring Boot

- [Spring Security](https://spring.io/projects/spring-security)

```shell script
cd DHV-cars-server

./mvnw dependency:go-offline -B
./mvnw package -DskipTests
```

```shell script
mkdir -p target/dependency
cd target/dependency
```

- app:          BOOT-INF/classes
- app/lib       BOOT-INF/lib
- app/META-INF  META-INF

app:app/lib/* 

```shell script
jar -xf ../*.jar
java -cp BOOT-INF/classes:BOOT-INF/lib/* com.quanpan302.cars.CarsApplication
```

## Client, ReactJS

- [React.js](https://reactjs.org)
- [Ant Design](https://ant.design)

```shell script
npm install

npm start
```

Prevent React setState on unmounted Component, [Link](https://www.robinwieruch.de/react-warning-cant-call-setstate-on-an-unmounted-component)

# Deployment

**Issues**

- Deployment (_Remote, direct to wrong ip_) vs
  Development (_Localhost), direct to `process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';`_).
  - [process.env](https://codeburst.io/process-env-what-it-is-and-why-when-how-to-use-it-effectively-505d0b2831e7)

## Docker

[docker-compose](https://docs.docker.com/compose/gettingstarted/)

- build and run, `docker-compose up`
- Rebuild and run, `docker-compose up --build --force-recreate --renew-anon-volumes`
- Stop, `docker-compose stop` or Clean `docker-compose down`
- Clean, `docker images && docker volume ls && docker system prune -f && docker volume prune -f && docker images && docker volume ls`
- Stop and Clean `docker images && docker volume ls && docker-compose ps && docker-compose stop && docker system prune -f && docker volume prune -f && docker images && docker volume ls`

[localhost:9090](http://localhost:9090)

## Kubernetes

Deploying Mysql on Kubernetes using **PersistentVolume** and **Secrets**, [li]
    
Creating the secrets

```shell script
kubectl create secret generic mysql-root-pass --from-literal=password=root
kubectl create secret generic mysql-user-pass --from-literal=username=quanpan302 --from-literal=password=quanpan302
kubectl create secret generic mysql-db-url --from-literal=database=cars --from-literal=url='jdbc:mysql://cars-db-mysql:3306/cars?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false'

kubectl get secrets
```

Deploying MySQL

```shell script
kubectl apply -f deployments/cars-db-mysql.yaml

kubectl get persistentvolumes
kubectl get persistentvolumeclaims
kubectl get services
kubectl get deployments
```

Logging into the MySQL pod

```shell script
kubectl exec -it $podNAME
```

Deploying Spring Boot app

```shell script
kubectl apply -f deployments/cars-server.yaml

kubectl get pods
```
Deploying the Spring Boot app

```shell script
kubectl apply -f deployments/cars-client.yaml

kubectl get pods
```

# Node.js

- [Yarn](https://yarnpkg.com/cli/install)
- [npm](https://docs.npmjs.com/cli/install)

Build

```shell script
npm install

npm run build
```

Serve

npm [localhost:5000](http://localhost:3000) or 
yarn [localhost:5000](http://localhost:5000)

```shell script
npm start
```

```shell script
yarn global add serve

serve -s build
```
