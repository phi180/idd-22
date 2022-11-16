FROM openjdk:17-jdk

ADD target/web4-0.0.1-SNAPSHOT.war xtrack.war

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m"]

CMD ["-jar", "xtrack.war"]