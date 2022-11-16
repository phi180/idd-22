FROM openjdk:17-jdk

ADD target/web4-0.0.1-SNAPSHOT.war xtrack.war
ADD src/main/resources resources

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "xtrack.war", "--spring.profiles.active=${SPRING_PROFILE}"]

HEALTHCHECK --start-period=30s --interval=10s CMD curl -f http://localhost:8080/actuator/health || exit 1