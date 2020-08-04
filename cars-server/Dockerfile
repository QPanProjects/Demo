## Build the application
FROM openjdk:8-jdk-alpine as build

WORKDIR /app

# Build dependencies
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

## A minimal docker image with command to run the app
FROM openjdk:8-jre-alpine

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# cd target/dependency
# java -cp /BOOT-INF/classes

ENTRYPOINT ["java","-cp","app:app/lib/*","com.quanpan302.cars.CarsApplication"]