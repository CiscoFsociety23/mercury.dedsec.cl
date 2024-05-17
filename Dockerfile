FROM openjdk:21-jdk
COPY ./target/mercury-dedsec-corp.war mercury-dedsec-corp.war
ENTRYPOINT ["java", "-jar", "mercury-dedsec-corp.war"]
