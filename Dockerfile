FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /src/main/java

# Copia os arquivos do projeto para dentro da imagem
COPY . .

# Usa o Gradle wrapper (ou substitua por `./gradlew build` se tiver wrapper no projeto)
RUN ./gradlew build --no-daemon

# Fase final: cria uma imagem leve com o JAR compilado
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
