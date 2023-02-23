FROM openjdk:11.0.16
MAINTAINER Marcelo Lemma
RUN useradd -ms /bin/bash w2m-user
USER w2m-user
WORKDIR /home/w2m-user
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} w2m-superheros-1.0.jar
ENTRYPOINT ["java","-jar", "/home/w2m-user/w2m-superheros-1.0.jar"]