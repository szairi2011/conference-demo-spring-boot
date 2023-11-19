## Project overview:
This springboot poc is created after following the Pluralsight course "Spring Framework 2: Creating Your First Spring Boot Application".

IntelliJ IDEA IDE is used for this project.

It is also used as an experimental project to try small features quickly on SpringBoot environments.

## RabbitMQ:
The application requires RabbitMQ and MySQL servers to start up properly. We can manually start both servers from Windows services.

Next: We may work on a Doker and Docker-compose manifests to improve portability and remote deployment of the application. 

## MySQL DB:
The project is using a local SQL database, i.e. conference-demo

## Postman collection:
A new Postman endpoints collection is created in postman application to experiment
It is called "Spring boot demos collection" under conference-demo request items

## Configuring for different environments
SpringBoot provides various ways to configure properties for the application, with an order of precedence that we should be aware of, including:
1. Command line arguments
2. OS environment variables
3. application properties
- IntelliJ IDEA provides tools to setup these properties from the IDE.
- We can also read the app properties programmatically using Spring @Value("${app.version}) annotation anywhere in our code that is Spring context aware; look at the HomeController class example
- SpringBoot allows us also to create our custom configuration through the use of the @Configuration annotation. Look at the example config/PersistenceConfiguration.java

## Build and run in VSCode IDE:
To build and run the application using VSCode Maven extension, under cmd terminal run > mvnw spring-boot:run # this will compile build and run the application.
By defaut auto-update is set for SpringBoot to recompile any code changes and redeploy the app locally 

NB: No need to run the same command again if the project is already built, we may only need to re-run it if the project gets out of sync. We simply need to Open the "ConferenceDemoApplication.java" and click on "Run Java" from the to right to launch the webapp.

NB: We don't need the thick jar file to run the application in VSCode, the VSCode extensions will do the needy.

## Build a standalone thick jar application:
To build a sef deployable jar web application where Tomcat is embedded by Springboot run:
> mvn clean install # This will build a jar applicatin that can be run using:
> java -jar conference-demo-0.0.1-SNAPSHOT.jar

## Deploying the app
- The default deployment process is a self executable jar package (executable fat jar).
- SpringBoot allows us also to deploy to an external servlet container, and to package the app as a war file instead
- However, the recommended way nowadays is to deploy the jar inside a Docker container for a better portability, and scalability.

# Spring Cloud Stream:
An example is created to evaluate the pub / sub new architecture based on the Functional (i.e. Supplier; Function; Consumer) paradigm of the Spring Cloud Stream. It allows us to create prodcucer, processor and consumer topics by simple use of Functional paradign no need to annotate the channels with e.g. @Input @Output @EnableBinding or @StreamListener -- look at this nice and simple tuto https://shaikezam.com/#/spring_cloud_stream_functional, which was found while browsing in StackOverflow to understand the difference between v3.x and v4.x of Spring Cloud Stream  