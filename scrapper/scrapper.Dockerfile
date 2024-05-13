FROM openjdk:21
WORKDIR /app
COPY target/scrapper.jar /app/scrapper.jar
EXPOSE 80
EXPOSE 81
CMD ["java", "-jar", "scrapper.jar"]
