FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER fischermatte@posteo.net
EXPOSE 8080
VOLUME /tmp
ADD maven/todo-app-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]