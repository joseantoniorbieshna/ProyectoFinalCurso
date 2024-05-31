FROM openjdk:17-jdk-slim

RUN mkdir -p /ssl

ARG JARFILE=target/faltasproject-0.0.1.jar
COPY ${JARFILE} faltasproject.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","faltasproject.jar" ]