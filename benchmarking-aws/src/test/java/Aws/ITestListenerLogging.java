package Aws;

import java.util.logging.Logger;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Common.LogFormatter;
import Common.StabilityException;

public class ITestListenerLogging implements ITestListener {

  private static final Logger LOGGER = AwsSequentialTestBase.LOGGER;
  
  @Override
  public void onTestFailure(ITestResult result) {
    Throwable e = result.getThrowable();
    String exceptionType = "";
    String exception = "";
    String message = "";
    if(e instanceof SessionNotCreatedException ||
        e instanceof UnreachableBrowserException ||
        e instanceof UnknownServerException
      ) {
      exceptionType = "StabilityException";
      exception = e.getClass().getSimpleName();
      message = e.getMessage();
    } else if(e instanceof WebDriverException) {
      exceptionType = "SeleniumException";
      exception = e.getClass().getSimpleName();
      message = e.getMessage();
    } else if(e instanceof Exception) {
      exceptionType = "StabilityException";
      StabilityException stabilityException = new StabilityException();
      exception = stabilityException.getClass().getSimpleName();
      message = stabilityException.getMessage();
    } else if("AssertionError".equals(e.getClass().getSimpleName())){
      exceptionType = "AssertionError";
      exception = e.getClass().getSimpleName();
      message = e.getMessage();
    } else {
      exceptionType = "StabilityException";
      StabilityException stabilityException = new StabilityException();
      exception = stabilityException.getClass().getSimpleName();
      message = stabilityException.getMessage();
    }
    LOGGER.severe(LogFormatter.createMessage(
        AwsSequentialTestBase.build,
        AwsSequentialTestBase.testId,
        AwsSequentialTestBase.sessionId,
        AwsSequentialTestBase.os+" "+AwsSequentialTestBase.osVersion,
        AwsSequentialTestBase.browserVersion,
        AwsSequentialTestBase.browser,
        AwsSequentialTestBase.device,
        result.getName(),
        AwsSequentialTestBase.startTime,
        AwsSequentialTestBase.pageLoadTime,
        AwsSequentialTestBase.testTime,
        AwsSequentialTestBase.stopTime,
        AwsSequentialTestBase.totalTime,
        exceptionType,
        exception,
        message,
        AwsSequentialTestBase.octaneScore,
        AwsSequentialTestBase.individualScores
        ));
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LOGGER.info(LogFormatter.createMessage(
        AwsSequentialTestBase.build,
        AwsSequentialTestBase.testId,
        AwsSequentialTestBase.sessionId,
        AwsSequentialTestBase.os+" "+AwsSequentialTestBase.osVersion,
        AwsSequentialTestBase.browserVersion,
        AwsSequentialTestBase.browser,
        AwsSequentialTestBase.device,
        result.getName(),
        AwsSequentialTestBase.startTime,
        AwsSequentialTestBase.pageLoadTime,
        AwsSequentialTestBase.testTime,
        AwsSequentialTestBase.stopTime,
        AwsSequentialTestBase.totalTime,
        "",
        "",
        "",
        AwsSequentialTestBase.octaneScore,
        AwsSequentialTestBase.individualScores
        ));
  }

  @Override
  public void onFinish(ITestContext context) {}

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onTestSkipped(ITestResult result) {}

  @Override
  public void onTestStart(ITestResult result) {}
  
};
