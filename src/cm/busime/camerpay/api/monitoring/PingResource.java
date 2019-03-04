package cm.busime.camerpay.api.monitoring;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.api.CustomJsonBuilderFactory;
import cm.busime.camerpay.api.ResponseBuilder;


@Path("monitoring")
public class PingResource {

  protected static final String SERVER_NAME = "com.bmw.mastersolutions.gf.hostName";
  protected static final String SERVER_PORT = "HTTP_SSL_LISTENER_PORT";
  protected static final String PROFILE = "com.bmw.mastersolutions.gf.profile";
  protected static final String JVM_ROUTE = "jvmRoute";
  protected static final String PROJ_ENV = "com.bmw.ucp.projectenvironment";
  protected static final String DEPLOY_UNIT = "Camerpay API";
  protected static final String API_VERSION = "0.0.1";
  protected static final String GIT_COMMIT_ID = "git-commit-id";
  protected static final String PROJECT_VERSION = "project-version";
  protected static final String BUILD_TIMESTAMP = "build-time";
  protected static final String NOT_AVAILABLE = "not available";

  private static final Logger log = Logger.getLogger(PingResource.class.getName());

  @Context
  private ServletContext context;

  @Inject
  private ResponseBuilder responseBuilder;
  
  @Inject
  private CustomJsonBuilderFactory jsonBuilderFactory;


  @GET
  @Path("ping")
  @Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
  public Response ping() {
	  log.log(Level.INFO, "...incomming Ping request!");
    JsonObject monitoringInfo = retrieveMonitoringInfo();
    return responseBuilder
        .statusOk()
        .cacheControlNoCache()
        .entity(monitoringInfo)
        .build();
  }
  
  private JsonObject retrieveMonitoringInfo() {
	    final Manifest manifest = getManifest();
	    final DateFormat dateFormat = getDateFormat();

	    JsonObjectBuilder json = jsonBuilderFactory.createObjectBuilder();
	    json.add("serverTime", dateFormat.format(new Date()));
	    json.add("startTime", dateFormat.format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime())));
	    json.add("serverName", context.getServerInfo());
	    json.add("profile", getSystemProperty(PROFILE));
	    json.add("environment", getSystemProperty(PROJ_ENV));
	    json.add("routeId", getSystemProperty(JVM_ROUTE));
	    json.add("apiVersion", API_VERSION);
	    json.add("deploymentUnit", DEPLOY_UNIT);
	    json.add("gitCommit", getManifestEntry(manifest, GIT_COMMIT_ID));
	    json.add("buildTime", getManifestEntry(manifest, BUILD_TIMESTAMP));
	    json.add("artifactVersion", getManifestEntry(manifest, PROJECT_VERSION));
	    return json.build();
	}

  private String getSystemProperty(String propertyName) {
    return System.getProperty(propertyName, NOT_AVAILABLE);
  }

  private Manifest getManifest() {
    try {
      InputStream inputStream = context.getResourceAsStream("/META-INF/MANIFEST.MF");
      if (inputStream != null) {
        return new Manifest(inputStream);
      }
    } catch (Exception e) {
      log.log(Level.WARNING, "An error occurred when loading manifest file '/META-INF/MANIFEST.MF'", e);
    }
    return null;
  }

  private String getManifestEntry(Manifest manifest, String parameterName) {
    String value = null;
    if (manifest != null && manifest.getMainAttributes() != null) {
      value = manifest.getMainAttributes().getValue(parameterName);
    }
    return value == null ? NOT_AVAILABLE : value;
  }

  protected DateFormat getDateFormat() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMANY);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat;
  }
}
