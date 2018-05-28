FROM openjdk:8
RUN apt-get update && apt-get install -y libltdl7 vim && rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 6200
ADD /libs/${name}-${version}.jar /app.jar
