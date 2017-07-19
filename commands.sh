mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

mvn validate
mvn clean
mvn package -DignoreTests
mvn site
java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App

mvn dependency:copy-dependencies

