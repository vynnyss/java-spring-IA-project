# Usa imagem com JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /src/main/java

# Copia o JAR gerado para dentro da imagem (ajustado conforme o nome do JAR)
COPY build/libs/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
