FROM openjdk:11

WORKDIR /usr/app/

ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV JDBC_URL=jdbc:postgresql://db:5432/airtng-servlets
ENV TWILIO_ACCOUNT_SID=your_account_sid
ENV TWILIO_AUTH_TOKEN=your_account_token
ENV TWILIO_PHONE_NUMBER=your_twilio_number

COPY . .

RUN ./gradlew build

EXPOSE 8080

ENV TEST_VAR1=hola

CMD ["sh","-c","./gradlew flywayMigrate && ./gradlew appRun"]
