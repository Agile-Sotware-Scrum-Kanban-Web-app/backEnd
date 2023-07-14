FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/agileSoftware-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM openjdk:17-jdk-slim

#WORKDIR /app

#COPY . .

#RUN ./mvnw clean package -DskipTests

#EXPOSE 8080

#ENTRYPOINT ["java", "-jar", "target/agileSoftware-0.0.1-SNAPSHOT.jar"]
