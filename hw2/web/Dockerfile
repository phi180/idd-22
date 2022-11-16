FROM openjdk:17-jdk

ADD target/web-0.0.1-SNAPSHOT.war lucenex.war

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m"]

CMD ["-jar", "lucenex.war"]