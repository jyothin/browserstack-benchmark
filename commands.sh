source ./export_keys.sh
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
mvn package
java -cp target/sauce-1.0-SNAPSHOT.jar com.browserstack.benchmark.App
mvn clean dependency:copy-dependencies package
mvn site

mvn clean
mvn validate
mvn compile
mvn test
mvn package
mvn verify
mvn install
mvn deploy

mvn -Dmaven.test.skip=true package
mvn install -DskipTests

mvn surefire-report:report
mvn site -DgenerateReports=false
