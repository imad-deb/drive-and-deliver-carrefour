# DriveAndDeliver - a Carrefour kata

> Here is a pretty free implementation exercise to be completed in a short period of time.\
> It aims to be a conversation starter during a future interview.\
> Since the goal is to discuss choices and good use of conceptes, we recommend that you spend no more than 2 hours on it, even if you haven't had the time to finish the mandatory User Stories.

## Instructions The exercise is made of a mandatory part, the [Minimum Valuable Product](#mvp), and [bonus features](#features-bonus) to make the most of your remaining time and stand out.\ The stories have no acceptance criteria, it's up to you to define them after your functional analysis of the story.
**If you are missing some information, make a choice and stick to it.**

### Constraints
- Spring-boot 3.x.x
- Java 21
- Git
- Enhance this `README.md` file -- _Explains the potential intricacies of your implementation and how to launch your project_.

### Delivery The code should be available as a project in gitlab.

### Assessment **There is no "right" way to do this exercise.**\ We are interested in your implementation choices, your technique, the code architecture and the compliance with the constraints.\ _Also pay attention to the size of your commits and their messages._

### Tips To quickly create your project base, use [spring initializr](https://start.spring.io/)

## Exercise
### MVP
#### User Story
> As a customer, I can choose my delivery method.\
> The available delivery methods are: `DRIVE`, `DELIVERY`, `DELIVERY_TODAY`, `DELIVERY_ASAP`.

#### User Story
> As a customer, I can choose the day and time slot for my delivery.\
> The time slots are specific to the delivery method and can be booked by other customers.

### Bonus Features The following features are optional and not exhaustive.\
They have no priority between them, you can implement the ones you are interested in or propose others.

#### REST API
- Propose an HTTP REST API to interact with the services implemented in the MVP
- Implement HATEOAS principles in your REST API
- Document the REST API
- Secure the API
- Use a non-blocking solution

#### Persistence
- Propose a data persistence solution
- Propose a cache solution

#### Stream
- Propose a data streaming solution
- Propose a solution for consuming and/or producing events

#### CI/CD
- Propose a CI/CD system for the project
- Propose End-to-End tests for your application

#### Packaging
- Create a container of your application
- Deploy your application in a pod
- Create a native image of your application

## Tasks carried out for the exercise:

#### REST API
- Propose an HTTP REST API to interact with the services implemented in the MVP
- Secure the API
- Use a non-blocking solution

#### Persistence
- Propose a data persistence solution
- 
#### Stream
- Propose a data streaming solution
- Propose a solution for consuming and/or producing events

#### Packaging
- Create a container of your application
- Deploy your application in a pod
- Create a native image of your application


## Launch the application

The following are the steps to download and launch the Spring Boot application from a Github repository:

1. **Clone the Github repository** :
   Use `git clone` to clone the repository in your local machine.

   ```
   git clone <https://github.com/imad-deb/drive-and-deliver-carrefour.git>
   ```

2. **Import the project into your IDE** :
   Open your IDE (Eclipse, IntelliJ IDEA, etc.) and import the project you cloned. Make sure your IDE is configured to support Java 21.

3. **Configure the application properties** :
   Adapt the application properties in the `application.properties` file to your environment.

4. **Compile the application** :
   Use Maven to compile the Spring Boot application.
   You can run the following command at the root of the project :

   ```
   mvn clean install
   ```

5. **Launch Kafka** :

   ```
   .\bin\windows\kafka-server-start.bat .\config\server.properties
   
   ```
6. **Launch the app** :
   You can launch Spring Boot application directly from your IDE by running the main class (@SpringBootApplication`)
   or by using Maven to run the application. With Maven, use the command :

   ```
   mvn spring-boot:run
   ```

8. **Access the app** :
   Once the application is started, you can access it via the URL specified in the configuration file (`http://localhost:8080`).
