FROM openjdk:21
WORKDIR /app
COPY target/scrapper.jar /app/scrapper.jar
EXPOSE 80
EXPOSE 8081
CMD ["java", "-jar", "scrapper.jar"]
