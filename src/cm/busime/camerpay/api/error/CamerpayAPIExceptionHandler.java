package cm.busime.camerpay.api.error;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.user.UserResource;

public class CamerpayAPIExceptionHandler {
	
	private static final Logger log = Logger.getLogger(CamerpayAPIExceptionHandler.class.getName());

	public Response handle(final StatusCode pErrorCode, final Object[] pMessageParams) {
	    Error error = createError(pErrorCode, pMessageParams);
	    return handle(pErrorCode.getResponseStatus(), pErrorCode.getLevel(), error, null);
	  }

	  public Response handle(final StatusCode pErrorCode, final Throwable pThrowable) {
	    Error error = createError(pErrorCode, new Object[] {pThrowable.getMessage()});
	    return handle(pErrorCode.getResponseStatus(), pErrorCode.getLevel(), error, pThrowable);
	  }

	  public Response handle(final CamerpayAPIException pException) {
		log.log(Level.INFO, "Handle CamerpayAPIException");
	    final StatusCode statusCode = pException.getCode();
	    final Error error = createError(statusCode, pException.getMessageParameters());
	    return handle(statusCode.getResponseStatus(), statusCode.getLevel(), error, pException);
	  }

	  public Response handle(final WebApplicationException pWebAppException) {
	    // predefined error codes are used to determine error code, message and log level,
	    // but the http status is taken from the web application exception
	    final Error error;
	    final Level logLevel;
	    final int httpStatus = pWebAppException.getResponse().getStatus();
	    if (400 <= httpStatus && httpStatus <= 499) {
	      error = createError(StatusCode.BAD_REQUEST);
	      logLevel = StatusCode.BAD_REQUEST.getLevel();
	    } else {
	      error = createError(StatusCode.INTERNAL_SERVER_ERROR);
	      logLevel = StatusCode.INTERNAL_SERVER_ERROR.getLevel();
	    }
	    return handle(httpStatus, logLevel, error, pWebAppException);
	  }

	  /**
	   * Central "handle" method. Handle means: log error and return an error response.
	   */
	  private Response handle(final int pHttpStatus, final Level pLogLevel, final Error pError, final Throwable pThrowable) {
	    //logError(pLogLevel, pError, pThrowable);
		log.log(Level.INFO, "Handle Response");
	    return buildResponse(pHttpStatus, pError);
	  }

	  private Error createError(final StatusCode pErrorCode) {
	    return createError(pErrorCode, null);
	  }

	  private Error createError(final StatusCode pStatusCode, final Object[] pMessageParams) {
	    return createError(pStatusCode.getStatusCode(), pStatusCode.getMessageFormat(), pMessageParams);
	  }

	  private Error createError(final String pStatusCode, final String pErrorMessage, final Object[] pMessageParams) {
	    Error error = new Error();
	    error.setErrorCode(pStatusCode);
	    if (pMessageParams == null) {
	      error.setErrorMessage(pErrorMessage);
	    } else {
	    	String msg = pErrorMessage;
	    	for (Object o : pMessageParams)
	    		msg += " " + o.toString();
	    	error.setErrorMessage(msg);
	    }
	    return error;
	  }

	  private Response buildResponse(final int pHttpStatus, final Error pError) {
		  log.log(Level.INFO, "Build Response");
	    return Response.status(pHttpStatus)
	        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
	        .entity(pError)
	        .build();
	  }
}
