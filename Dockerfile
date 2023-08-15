FROM adoptopenjdk/openjdk11:alpine

VOLUME /vol

ARG jar_file=build/libs/*.jar
ARG apm_agent=apm-agent/*.jar

COPY ${jar_file} app.jar
COPY ${apm_agent} apm-agent.jar

ENTRYPOINT ["java", \
"-javaagent:/apm-agent.jar", \
"-Delastic.apm.server_urls=http://apm-server:8200", \
"-Delastic.apm.service_name=boot-apm-agent", \
"-Delastic.apm.application_packages=com.example", \
"-Delastic.apm.environment=dev", \
"-Dspring.profiles.active=dev", \
"-jar", \
"/app.jar"]
