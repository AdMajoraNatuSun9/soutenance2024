# Stage de construction avec Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build
COPY ./src src/
COPY ./pom.xml pom.xml

# Compilation du projet et création des JARs en excluant les tests
RUN mvn clean package -DskipTests

# Stage final utilisant un JDK léger
FROM maven:3.8.7-eclipse-temurin-17

# Copie du JAR depuis le stage de construction au stage de déploiement
COPY --from=build /target/RoadGuardian-Backend-0.0.1-SNAPSHOT.jar /app.jar

# Exposition du port 8080 
EXPOSE 8080

# Commande pour démarrer l'application
CMD ["java", "-jar", "/app.jar"]