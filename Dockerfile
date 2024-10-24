# image lam nen
FROM maven:3.9.9-amazoncorretto-17 as build


# tao thu muc /app
WORKDIR /app

# copy cac dependency
COPY pom.xml .
# copy toan bo code
COPY src ./src
# build
RUN mvn install  -DskipTests

FROM amazoncorretto:21.0.4 as runner

WORKDIR /app
#copy .jar và đổi tên thành app.jar
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]