FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copia os arquivos do projeto para dentro da imagem
COPY . .

# ⚠️ Dá permissão de execução ao gradlew
RUN chmod +x ./gradlew

# Usa o Gradle wrapper (ou substitua por `./gradlew build` se tiver wrapper no projeto)
RUN ./gradlew build -x test --no-daemon

# Fase final: cria uma imagem leve com o JAR compilado
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar ./

ENTRYPOINT ["java", "-jar", "sdw24-0.0.1-SNAPSHOT.jar"]