package cm.busime.camerpay.api.enumeration;

import java.util.logging.Level;

public enum StatusCode {

  BAD_REQUEST("CMRPAY_B_1000", "Bad request.", 400, Level.INFO),
  INVALID_PARAMETER("CMRPAY_B_1001", "Invalid request parameter(s): {0}.", 400, Level.INFO),
  INVALID_CONTENT("CMRPAY_B_1002", "Invalid content.", 400, Level.INFO),
  RESOURCE_NOT_FOUND("CMRPAY_B_1003", "Requested resource not found.", 404, Level.INFO),
  NO_EMISSION_AND_CONSUMPTION_DATA("CMRPAY_B_1004", "No emission and consumption data have been found for the model code and date provided.", 404, Level.INFO),
  NO_NEDC_COR_RELEVANT_DATA_FOUND("CMRPAY_B_1005", "No NEDC correlated model data have been found for given parameters.", 404, Level.INFO),
  IMPORT_ALREADY_IN_PROGRESS("CMRPAY_B_1006", "Data import is already in progress: {0}.", 403, Level.INFO),
  OTD_IMPORT_ALREADY_IN_PROGRESS("CMRPAY_B_1007", "OTD import is already in progress.", 403, Level.INFO),
  FILE_EXPORT_ALREADY_IN_PROGRESS("CMRPAY_B_1009", "Nedc Correlated file export is already in progress: {0}.", 400, Level.INFO),
  NO_RESULT("CMRPAY_B_1010", "No result found in database for (request) parameter(s) {0}.", 404, Level.INFO), // instead of NoResultException
  EVE_VR_SERVICE_TIMEOUT("CMRPAY_B_1012", "The EVE-VR service has not responded within the connection time threshold of {0} seconds and read time threshold of {1} seconds. Time taken: {2}", 424, Level.INFO),
  OTD_ELECTRIC_DATA_NOT_FOUND("CMRPAY_B_1013", "Cannot fetch electric range and electric consumption from OTD because the otd-region provided is not defined in the service", 400, Level.INFO),
  PSDE_IMPORT_ALREADY_IN_PROGRESS("CMRPAY_B_1014", "PSDE import is already in progress.", 403, Level.INFO),
  NO_LEGISLATOR_FOUND("CMRPAY_B_1015", "No Legislator found for country code {0} and validity {1}", 404, Level.INFO),
  AMBIGOUS_LEGISLATOR_FOUND("CMRPAY_B_1016", "Ambigous Legislator found for country code {0} and validity {1}", 404, Level.INFO),

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
  USER_EXISTS("CMRPAY_S_202", "The User with the id {0} already exits", 202 , Level.INFO),
  USER_AUTHENTICATION_FAILED("CMRPAY_S_204", "User credentials user/password not correct", 204 , Level.INFO),
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
