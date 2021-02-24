FROM hseeberger/scala-sbt AS builder
WORKDIR /app
COPY . .
RUN sbt assembly

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/scala-2.13/dott-cli-assembly-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "dott-cli-assembly-0.0.1-SNAPSHOT.jar"]