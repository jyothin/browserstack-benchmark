package Aws;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

public class DriveAccess {
  private static final Logger LOGGER = Logger.getLogger(DriveAccess.class.getName());
  private static final String CREDENTIAL_FILE = "./src/main/resources/credentials-3b0134b72ec8.json";
//  private static final String EMAIL = "benchmarking-96@halogen-segment-174013.iam.gserviceaccount.com";
  private static final String APPLICATION_NAME = "halogen-segment-174013";
  private static final java.io.File DATA_STORE_DIR = new java.io.File(
      System.getProperty("user.home"), ".credentials/drive-java");
  private static FileDataStoreFactory DATA_STORE_FACTORY;
  private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static HttpTransport HTTP_TRANSPORT;
  private static final List<String> SCOPES =
      Arrays.asList(DriveScopes.DRIVE);
  
  static {
    try {
      HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
      LOGGER.info(DATA_STORE_FACTORY.getDataDirectory().getAbsolutePath());
    } catch(Throwable t) {
      t.printStackTrace();
      LOGGER.info("Exiting with exit status -1");
      System.exit(-1);
    }
  }
  
  public static Credential authorize() throws IOException {
    java.io.File file = new java.io.File(CREDENTIAL_FILE);
    LOGGER.info(file.getAbsolutePath());
    LOGGER.info(file.getPath());
//    InputStream in = DriveAccess.class.getResourceAsStream("/credentials-3b0134b72ec8.json");
    InputStream in = new FileInputStream(file);
//    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//    //LOGGER.info(clientSecrets.toPrettyString());
//    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//            HTTP_TRANSPORT,
//            JSON_FACTORY,
//            clientSecrets,
//            SCOPES)
//        .setDataStoreFactory(DATA_STORE_FACTORY)
//        .setAccessType("offline")
//        .build();
//    Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    GoogleCredential credential = GoogleCredential.fromStream(in)
        .createScoped(SCOPES);
    LOGGER.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
    return credential;
  }

  public static Drive getDriveService() throws IOException {
    Credential credential = authorize();
    return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,  credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
  
}
