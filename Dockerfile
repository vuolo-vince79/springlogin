# Usa un'immagine di base con OpenJDK
FROM openjdk:17-jdk-alpine

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file jar generato nel container
COPY target/login.jar /app/login.jar

# Comando per eseguire l'applicazione
CMD ["java", "-jar", "login.jar"] 
