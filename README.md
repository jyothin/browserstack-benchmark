# browserstack-benchmark

## Description
Benchmarking tests for Browserstack and Suace Labs. The same automated tests 
are run on Browserstack and Sauce Labs and results logged for comparison.

Code uses maven-surefire-plugin to run tests in parallel.

## Configuration
* Change environment variables in `export_keys.sh` file
  * `SAUCE_USERNAME` - set to Sauce labs username
  * `SAUCE_ACCESS_KEY` - set to user Sauce labs access key
  * `BROWSERSTACK_USERNAME` - set to browserstack username
  * `BROWSERSTACK_ACCESS_KEY` - set to browserstack access key
  * `BUILD_TAG` - set build tag

* Source enviroment variables with

`$> source export_keys.sh`

* Change parallel thread count in the pom.xml file to configure number of
parallel tests.

>      <plugin>
>        <groupId>org.apache.maven.plugins</groupId>
>        <artifactId>maven-surefire-plugin</artifactId>
>        <version>2.20</version>
>        <configuration>
>          <parallel>methods</parallel>
>          <threadCount>5</threadCount>
>        </configuration>
>      </plugin>

## Usage
* Clone repository
* Run `mvn test` to run tests 
* Run `mvn site` to run tests and create surefire-report

## Running specific tests
* `TestSuite1` is __NOT__ used
* `TestSuite2` consists of individual Selenium tests that run in parallel.
* `TestSuite3` consists of Octane tests
* Comment `@Ignore` before each class to run test in the test suite.

## Test Results
* Surefire report is at `target/site/surefire-report.html`
* Are logged in `src/test/resources/bs_test.html` for Browserstack tests. Import the data into a Google spreadsheet to view the test data.
* Are logged in `src/test/resources/sl_test.html` for Suace Labs tests. Import the data into a Google spreadsheet to view the test data.

