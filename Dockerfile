
# Linux We are Going to Use
FROM alpine
WORKDIR /root/docker/spring-boot
COPY build/libs/spring-1.0.5.war /root/docker/spring-boot

# Install JDK
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin
ENTRYPOINT ["java","-jar","spring-1.0.5.war"]
#RUN-APP: docker build -t app-sb .