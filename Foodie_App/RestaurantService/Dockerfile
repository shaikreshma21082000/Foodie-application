FROM openjdk:11-jre-slim
WORKDIR usr/src
ENV MONGO_URI=mongodb://localhost:27017/Foodie
ADD ./target/RestaurantService-0.0.1-SNAPSHOT.jar /usr/src/RestaurantService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","RestaurantService-0.0.1-SNAPSHOT.jar"]