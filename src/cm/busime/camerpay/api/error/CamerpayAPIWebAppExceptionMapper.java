package cm.busime.camerpay.api.error;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CamerpayAPIWebAppExceptionMapper implements ExceptionMapper<WebApplicationException>{

	@Inject
	 private CamerpayAPIExceptionHandler errorHandler;
	
	@Override
	  public Response toResponse(final WebApplicationException exception) {
	    return errorHandler.handle(exception);
	  }

}
