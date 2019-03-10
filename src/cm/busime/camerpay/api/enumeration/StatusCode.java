package cm.busime.camerpay.api.enumeration;

import java.util.logging.Level;

public enum StatusCode {

  BAD_REQUEST("CMRPAY_B_1000", "Bad request.", 400, Level.INFO),
  INVALID_PARAMETER("CMRPAY_B_1001", "Invalid request parameter(s): {0}.", 400, Level.INFO),
  INVALID_CONTENT("CMRPAY_B_1002", "Invalid content.", 400, Level.INFO),
  RESOURCE_NOT_FOUND("CMRPAY_B_1003", "Requested resource not found.", 404, Level.INFO),

  // server errors (system errors)
  INTERNAL_SERVER_ERROR("CMRPAY_S_2001", "Internal server error. {0}", 500, Level.INFO),
  SERVICE_CONFIGURATION_ERROR("CMRPAY_S_2002", "Service configuration error: {0}", 500, Level.INFO),
  SETTING_NOT_CONFIGURED("CMRPAY_S_2003", "Mandatory setting is not configured: {0}.", 500, Level.INFO),
  EXTERNAL_SERVICE_COMMUNICATION_ERROR("CMRPAY_S_2004", "Error while communicating with {0} service: {1}.", 500, Level.INFO),
  FILE_EXPORT_ERROR("CMRPAY_S_2005", "Nedc Correlated file export failed.", 500, Level.INFO),
  NON_UNIQUE_RESULT("CMRPAY_S_2006", "Non unique result error (data inconsistency).", 500, Level.INFO), // instead NonUniqueResultException
  CACHE_ERROR("CMRPAY_S_2007", "Cache Error", 500 , Level.SEVERE),
	
 // Status code for success operation
//  User has been successfully created
  USER_CREATED("CMRPAY_S_201", "User has been successfully created", 201 , Level.INFO),
  USER_EXISTS("CMRPAY_S_202", "The User with the id {0} already exists", 202 , Level.INFO),
  USER_AUTHENTICATION_SUCCESSFULL("CMRPAY_S_200", "User login sucessfull", 200 , Level.INFO),
  USER_AUTHENTICATION_FAILED("CMRPAY_S_204", "User credentials not correct", 404 , Level.INFO),
  USER_NOT_EXIST("CMRPAY_S_205", "The User with the id {0} not exists", 404 , Level.INFO),
  USER_NOT_ACTIVATED("CMRPAY_S_206", "User is currently not activated", 401 , Level.INFO),
  USER_STATUS_UPDATED("CMRPAY_S_207", "Status of user with id {0} has been successfully updated", 201 , Level.INFO),
  USER_ACCESS_KEY_NOT_EXIST("CMRPAY_S_208", "Access key {0} not exists", 404 , Level.INFO),
  STATUS_NOT_EXIST("CMRPAY_S_209", "Status {0} not exists", 404 , Level.INFO),
  CONTACT_CREATED("CMRPAY_S_210", "Contact has been successfully created", 201 , Level.INFO),
  CONTACT_EXISTS("CMRPAY_S_211", "The Contact {0} {1} already exists", 202 , Level.INFO),
  CONTACT_NOT_EXISTS("CMRPAY_S_212", "The selected Contact doesn't exists", 202 , Level.INFO),
  CONTACT_UPDATED("CMRPAY_S_213", "Contact has been successfully updated", 201 , Level.INFO)
	;

  private final String statusCode;
  private final String statusMessageFormat;
  private final int responseStatus;
  private final Level logLevel;

  StatusCode(final String pStatusCode, final String pStatusMessageFormat, final int pResponseStatus, final Level pLevel) {
	  statusCode = pStatusCode;
    statusMessageFormat = pStatusMessageFormat;
    responseStatus = pResponseStatus;
    logLevel = pLevel;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public String getMessageFormat() {
    return statusMessageFormat;
  }

  public Level getLevel() {
    return logLevel;
  }

  public int getResponseStatus() {
    return responseStatus;
  }
}
