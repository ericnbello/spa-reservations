FROM maven:3.6.3 AS maven
# Create a workdir for our app
WORKDIR /usr/src/app
COPY . /usr/src/app

# Compile and package the application to an executable JAR
# RUN mvn clean package -DskipTests
RUN mvn clean package

# Using java 11
FROM eclipse-temurin:11
RUN mkdir /opt/app
COPY japp.jar /opt/app
CMD ["java", "-jar", "/opt/app/japp.jar"]

ARG JAR_FILE=/usr/src/app/target/*.jar
# Copying JAR file
COPY --from=maven ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080