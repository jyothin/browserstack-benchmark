# browserstack-benchmark

## Description
Benchmarking tests for Browserstack, Suace Labs and AWS Device Farm.
The same automated tests are run on all three services and results logged for comparison.

Browserstack and Sauce Labs tests are run in parallel on both Desktop browsers
and Real Mobile browsers.

AWS tests are run sequentially on AWS Device Farm for Real Mobile devices only.

Uses maven-surefire-plugin to run tests in parallel.

## Folder Structure
* `benchmarking` contains Browserstack and Sauce Labs tests based on JUnit
* `benchmarking-aws` contains AWS tests based on TestNG
* `benchmarking-standalone` is not used

## Configuration
* Change environment variables in `export_keys.sh` file
  * `SAUCE_USERNAME` - set to Sauce labs username
  * `SAUCE_ACCESS_KEY` - set to user Sauce labs access key
  * `BROWSERSTACK_USERNAME` - set to browserstack username
  * `BROWSERSTACK_ACCESS_KEY` - set to browserstack access key
  * `BUILD` - set build tag

* Source enviroment variables with

`$> source export_keys.sh`

* Change parallel thread count in the pom.xml file to configure number of
parallel tests (Only for Browserstack and Sauce Labs).

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
### `benchmarking`
* `packageTest.sh`
* Run `mvn test` to run tests 
* Run `mvn site` to run tests and create surefire-report

### `benchmarking-aws`
* `packageTest.sh`
* Upload zip-with-dependencies.zip to AWS Device Farm 
* Select Appium tests with TestNG
* Select 'Google Pixel Android 7.1' and/or 'iPhone 7 iOS 10.0'
* Run tests

## Running specific tests
* `TestSuite1` is __NOT__ used
* `TestSuite2` consists of individual Selenium tests that run in parallel.
* `TestSuite3` consists of Octane tests
* Comment `@Ignore` before each class to run test in the test suite.

## Test Results
* Surefire report is at `target/site/surefire-report.html`
* Are logged in `src/test/resources/bs_test.html` for Browserstack tests. Import the data into a Google spreadsheet to view the test data.
* Are logged in `src/test/resources/sl_test.html` for Suace Labs tests. Import the data into a Google spreadsheet to view the test data.
* Are logged in appium.screenshotdir/`aws_test.html` for AWS tests. The file
will be uploaded to your Google Drive to folder 'Benchmarking-AWS' from where you
can import the file in a Google spreadsheet.

