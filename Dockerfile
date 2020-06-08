FROM openjdk:11

WORKDIR /usr/app/

ENV DB_USERNAME=sample
ENV DB_PASSWORD=sample
ENV JDBC_URL=jdbc:postgresql://db:5432/airtng-servlets

COPY . .

RUN ./gradlew build

EXPOSE 8080

CMD ["sh","-c","./gradlew flywayMigrate && ./gradlew appRun"]
