package cm.busime.camerpay.api.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import cm.busime.camerpay.api.enumeration.StatusCode;

@Provider
public class CamerpayAPIDefaultExceptionMapper {

//implements ExceptionMapper<Throwable>{
//	
//	private static final Logger log = Logger.getLogger(CamerpayAPIDefaultExceptionMapper.class.getName());
//
//	@Inject
//	 private CamerpayAPIExceptionHandler errorHandler;
//	
//	@Override
//	  public Response toResponse(final Throwable exception) {
//		log.log(Level.INFO, "Incomming Request for CamerpayAPIDefaultExceptionMapper: " + exception.getMessage());
//	    return errorHandler.handle(StatusCode.INTERNAL_SERVER_ERROR, exception);
//	  }

}
