FROM openjdk:17-jdk

ADD target/web-3-0.0.1-SNAPSHOT.jar mentor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "mentor.jar", "--spring.profiles.active=${SPRING_PROFILE}"]