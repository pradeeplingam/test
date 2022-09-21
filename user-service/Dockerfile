FROM adoptopenjdk/openjdk16:alpine-jre
WORKDIR /opt/app
ARG SERVICE_NAME
RUN echo "Building $SERVICE_NAME"
ARG PROFILE
RUN echo "Building $SERVICE_NAME with profile $PROFILE"
ENV PROFILE=${PROFILE}
ADD /target/${SERVICE_NAME}*.jar app.jar
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=$(echo ${PROFILE}) app.jar