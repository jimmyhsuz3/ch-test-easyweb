package ch.test.easyweb;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
public class AuthorizationCodeFlowFactory {
	// 343
	// https://stackoverflow.com/questions/13684292/authenticate-google-app-users-with-google-oauth2-api
	// http://justinyang1221.blogspot.tw/2015/10/oauth20-web-applicationusing-google.html
	// http://blog.csdn.net/binyao02123202/article/details/12856973
	
	// https://developers.google.com/drive/v3/web/quickstart/java
	// https://developers.google.com/identity/protocols/OAuth2
	// https://developers.google.com/identity/protocols/OAuth2WebServer#incrementalAuth
	// https://developers.google.com/api-client-library/java/google-api-java-client/oauth2
	
	// AbstractAuthorizationCodeServlet
	// AbstractAuthorizationCodeCallbackServlet
	// https://github.com/google/google-oauth-java-client/tree/dev/google-oauth-client-servlet
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/drive-java-quickstart");
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);
	private static HttpTransport HTTP_TRANSPORT;
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	private static AuthorizationCodeFlow flow;
	public AuthorizationCodeFlow initializeFlow() throws IOException {
		if (flow == null){
			InputStream in = null;
			try {
				in = getClass().getResourceAsStream("/META-INF/client_secret.json");
				GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
				flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
						.setDataStoreFactory(DATA_STORE_FACTORY)
						.setAccessType("offline")
						.build();
//				new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
			} finally {
				if (in != null)
					in.close();
			}
		}
		return flow;
	}
	public JsonFactory getJsonFactory(){
		return JSON_FACTORY;
	}
	public HttpTransport getHttpTransport(){
		return new NetHttpTransport();
	}
}
