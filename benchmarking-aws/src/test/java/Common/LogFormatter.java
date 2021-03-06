package Common;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
  
  private static final String EOL = System.getProperty("line.separator");
  
  @Override
  public String format(LogRecord record) {
    StringBuilder testMessage = new StringBuilder();
    testMessage.append("<tr>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(new Date(record.getMillis()).toString()+EOL);
    testMessage.append("</td>"+EOL);
    
    testMessage.append("<td>"+EOL);
    testMessage.append(record.getMillis()+EOL);
    testMessage.append("</td>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(record.getSequenceNumber()+EOL);
    testMessage.append("</td>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(record.getLoggerName()+EOL);
    testMessage.append("</td>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(record.getLevel()+EOL);
    testMessage.append("</td>"+EOL);

//    testMessage.append("<td>"+EOL);
//    testMessage.append(record.getSourceClassName()+EOL);
//      testMessage.append("</td>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(record.getSourceMethodName()+EOL);
    testMessage.append("</td>"+EOL);

    testMessage.append("<td>"+EOL);
    testMessage.append(record.getThreadID()+EOL);
    testMessage.append("</td>"+EOL);

    testMessage.append(record.getMessage()+EOL);

//    testMessage.append("<td>"+EOL);
//    testMessage.append(record.getThrown()+EOL);
//    testMessage.append("</td>"+EOL);

    testMessage.append("</tr>");

    return testMessage.toString();
  }
  
  @Override
  public String getHead(Handler h) {
    StringBuilder head = new StringBuilder();
    head.append("<html>"+EOL);
    head.append("<body>"+EOL);
    head.append("<table>"+EOL);
    head.append("<tr>"+EOL);

    head.append("<td>"+EOL);
    head.append("Date"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("Timestamp"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("SequenceNumber"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("Logger"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("Level"+EOL);
    head.append("</td>"+EOL);
    
//  head.append("<td>"+EOL);
//  head.append("SourceClassName"+EOL);
//  head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("SourceMethodName"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("ThreadId"+EOL);
    head.append("</td>"+EOL);

    head.append("<td>"+EOL);
    head.append("buildTag"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("testId"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("sessionId"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("os"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("version"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("browser"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("deviceName"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("methodName"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("startTime"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("pageLoadTime"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("testTime"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("stopTime"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("totalTime"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("errorType"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("exception"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("errorMessage"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("octaneScore"+EOL);
    head.append("</td>"+EOL);
    
    head.append("<td>"+EOL);
    head.append("individualScores"+EOL);
    head.append("</td>"+EOL);
    
    head.append("</tr>"+EOL);

    return head.toString();
    
  }
  
  @Override
  public String getTail(Handler h) {
    StringBuilder tail = new StringBuilder();
    tail.append("</table>"+EOL);
    tail.append("</body>"+EOL);
    tail.append("</html>"+EOL);
    return tail.toString();
  }
  
  public static String createMessage(
      String build,
      int testId,
      String sessionId,
      String os,
      String version,
      String browser,
      String deviceName,
      String methodName,
      long startTime,
      long pageLoadTime,
      long testTime,
      long stopTime,
      long totalTime,
      String exceptionType,
      String exception,
      String errorMessage,
      String octaneScore,
      String individualScores
      ) {
    StringBuilder message = new StringBuilder();
    message.append("<td>"+EOL);
    message.append(build+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(testId+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(sessionId+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(os+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(version+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(browser+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(deviceName+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(methodName+EOL); //description.getMethodName()
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(startTime+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(pageLoadTime+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(testTime+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(stopTime+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(totalTime+EOL);
    message.append("</td>"+EOL);

    message.append("<td>"+EOL);
    message.append(exceptionType+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(exception+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(errorMessage+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(octaneScore+EOL);
    message.append("</td>"+EOL);
    
    message.append("<td>"+EOL);
    message.append(individualScores+EOL);
    message.append("</td>"+EOL);
    
    return message.toString();
  }

}
