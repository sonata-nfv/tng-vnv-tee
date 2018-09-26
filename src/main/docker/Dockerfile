FROM openjdk:8-jre-alpine
RUN apt-get update && apt-get install -y libltdl7 vim && rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 6200x
ADD /libs/${name}-${version}.jar /app.jar
