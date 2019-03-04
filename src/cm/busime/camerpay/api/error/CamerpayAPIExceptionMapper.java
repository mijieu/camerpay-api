package cm.busime.camerpay.api.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CamerpayAPIExceptionMapper implements ExceptionMapper<CamerpayAPIException>{
	
	private static final Logger log = Logger.getLogger(CamerpayAPIExceptionMapper.class.getName());

	@Inject
	 private CamerpayAPIExceptionHandler errorHandler;
	
	@Override
	  public Response toResponse(final CamerpayAPIException exception) {
		log.log(Level.INFO, "Incomming Request for CamerpayAPIExceptionMapper: " + exception.getCode().getResponseStatus());
	    return errorHandler.handle(exception);
	  }

}
