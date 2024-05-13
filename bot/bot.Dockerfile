FROM openjdk:21
WORKDIR /app
COPY target/bot.jar /app/bot.jar
EXPOSE 90
EXPOSE 91
CMD ["java", "-jar", "bot.jar"]
