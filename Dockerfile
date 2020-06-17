FROM openjdk:11

WORKDIR /usr/app/

RUN apt-get update && \
  apt-get upgrade -y && \
  apt-get install -y build-essential

ENV DB_USERNAME=sample
ENV DB_PASSWORD=sample
ENV JDBC_URL=jdbc:postgresql://db:5432/airtng-servlets

COPY . .

RUN make install

EXPOSE 8080

CMD ["sh","-c","./gradlew flywayMigrate && make serve"]
