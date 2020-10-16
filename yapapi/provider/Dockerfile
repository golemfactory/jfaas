FROM openjdk:8-jre-alpine
RUN mkdir /golem && mkdir /golem/work
COPY run.sh /golem
WORKDIR /golem
VOLUME /golem/input /golem/output
