FROM vinjara/tiny:java
WORKDIR /
COPY app/target/tiny-0.0.1-SNAPSHOT.jar ./app.jar
RUN chmod +x ./app.jar
CMD ["java","-jar","./app.jar"] 