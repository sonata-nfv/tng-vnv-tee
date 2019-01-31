FROM openjdk:8-jre-alpine
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 6200
ADD /libs/${name}-${version}.jar /app.jar
