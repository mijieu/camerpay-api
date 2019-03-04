package cm.busime.camerpay.api;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.api.entity.Role;
import cm.busime.camerpay.api.enumeration.StatusCode;

import java.text.MessageFormat;
import java.util.Date;


public class ResponseBuilder {

  private Response.ResponseBuilder builder;
  
  @Inject
  private CustomJsonBuilderFactory jsonBuilderFactory;

  public ResponseBuilder statusOk() {
    builder = Response.status(Response.Status.OK);
    return this;
  }

  public ResponseBuilder statusCreated() {
    builder = Response.status(Response.Status.CREATED);
    return this;
  }

  public ResponseBuilder status(int status) {
    builder = Response.status(status);
    return this;
  }

  public ResponseBuilder cacheControlNoCache() {
    checkInitialized();
    builder.header("Cache-Control", "must-revalidate, private, no-cache, no-store, max-age=0");
    builder.header("Expires", -1);
    return this;
  }

  public ResponseBuilder entity(Object entity) {
    checkInitialized();
    builder.entity(entity);
    return this;
  }

  public Response build() {
    checkInitialized();
    builder.header("Date", new Date());
    return builder.build();
  }

  private void checkInitialized() {
    if (builder == null) {
      throw new IllegalStateException("ResponseBuilder was not initialized with response status.");
    }
  }
  
  public JsonObject getResponseSimple(String success,  StatusCode statusCode, Object[] pmessage) {
	    JsonObjectBuilder json = jsonBuilderFactory.createObjectBuilder();
	    json.add("success", success);
	    if ( pmessage == null)
	    	json.add("message", statusCode.getMessageFormat());
	    else
	    	json.add("message",MessageFormat.format(statusCode.getMessageFormat(), pmessage));
	    json.add("code", statusCode.getStatusCode());
	    return json.build();
	}
  
  public JsonObject getResponseSimple(String success,  StatusCode statusCode, String pmessage) {
	    JsonObjectBuilder json = jsonBuilderFactory.createObjectBuilder();
	    json.add("success", success);
	    if ( pmessage == null)
	    	json.add("message", statusCode.getMessageFormat());
	    else
	    	json.add("message",pmessage);
	    json.add("code", statusCode.getStatusCode());
	    return json.build();
	}
  
  public JsonObject getResponseSimple(Role role, String success) {
	    JsonObjectBuilder json = jsonBuilderFactory.createObjectBuilder();
	    json.add("success", success);
	    json.add("user-id", role.getUser_id());
	    json.add("role", role.getRole().getName());
	    return json.build();
	}
  
  public Response getResourceNotFoundResponse() {
	  return this
			  .status(StatusCode.RESOURCE_NOT_FOUND.getResponseStatus())
			  .entity(getResponseSimple("false", StatusCode.RESOURCE_NOT_FOUND, new Object[] {"Resource Not found"}))
			  .build();
  }
}