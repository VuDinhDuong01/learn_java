# image lam nen
FROM maven:3.9.9-amazoncorretto-17 as build

WORKDIR /app

# copy  dependency
COPY pom.xml .
# copy toan bo code
COPY src ./src
# build
RUN mvn clean install package

FROM amazoncorretto:21.0.4 as runner

WORKDIR /app
#copy .jar và đổi tên thành app.jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 5000 

ENTRYPOINT [ "java","-jar","app.jar" ]