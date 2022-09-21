FROM adoptopenjdk/openjdk16:alpine-jre
WORKDIR /opt/app
ARG SERVICE_NAME=college-service
ARG JAR_FILE=./$SERVICE_NAME/target/$SERVICE_NAME.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080