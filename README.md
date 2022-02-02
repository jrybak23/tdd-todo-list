# tdd-todo-list - Integration testing setup example 
## Read [the related article here devguidance.com/posts/integration-testing-setup-for-java-rest-api-applications-with-database-persistence/](https://www.devguidance.com/posts/integration-testing-setup-for-java-rest-api-applications-with-database-persistence/)

Simple CRUD app just for educational purposes.

Click to navigate to: 
- [The testing class](https://github.com/jrybak23/tdd-todo-list/blob/master/src/test/java/com/example/controllers/TodoResourceTest.java)
- [The controller class](https://github.com/jrybak23/tdd-todo-list/blob/master/src/main/java/com/example/controllers/TodoResource.java)
- [The dataset file](https://github.com/jrybak23/tdd-todo-list/blob/master/src/test/resources/com/example/controllers/TodoResourceTest/testGetItems_dataset.xml)
- [The expected response JSON file](https://github.com/jrybak23/tdd-todo-list/blob/master/src/test/resources/com/example/controllers/TodoResourceTest/testGetItems_expectedResponse.json)

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Be aware that it’s not an _über-jar_ as
the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/todo-list-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html
.

## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
