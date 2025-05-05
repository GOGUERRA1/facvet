FROM openjdk:22-oracle

WORKDIR /app

COPY target/facvet-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_BHZNK7S7IGCPMYA8 /app/oracle_wallet
EXPOSE 8080

CMD ["java", "-jar", "app.jar" ]