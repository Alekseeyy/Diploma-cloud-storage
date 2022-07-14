FROM openjdk:15

EXPOSE 8080

ADD target/Cloud_Storage-0.0.1-SNAPSHOT.jar diplom.jar

ENTRYPOINT ["java", "-jar", "/diplom.jar"]