# FROM maven:3.8.3 AS maven
# # Create a workdir for our app
# WORKDIR /usr/src/app
# COPY . /usr/src/app

# # Compile and package the application to an executable JAR
# RUN mvn clean package -DskipTests
# # Using java 11
# FROM openjdk:17-jdk

# ARG JAR_FILE=/usr/src/app/target/*.jar
# # Copying JAR file
# COPY --from=maven ${JAR_FILE} app.jar

# ENTRYPOINT ["java","-jar","/app.jar"]
# EXPOSE 8080
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080