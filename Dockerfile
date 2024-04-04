FROM amazoncorretto:21

WORKDIR /app

COPY ./build/libs/dopaminz-0.0.1-SNAPSHOT.jar /app/dopaminz.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "dopaminz.jar"]
