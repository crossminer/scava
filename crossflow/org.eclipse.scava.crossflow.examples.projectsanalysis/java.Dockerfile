FROM openjdk:8

WORKDIR /opt/app

COPY build/libs/ProjectsAnalysis.jar ProjectsAnalysis.jar

ENTRYPOINT ["java", "-jar", "ProjectsAnalysis.jar"]