package cm.busime.camerpay.api.error;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {

  private String errorCode;
  private String errorMessage;

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(final String pErrorCode) {
    errorCode = pErrorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(final String pErrorMessage) {
    errorMessage = pErrorMessage;
  }

  @Override
  public String toString() {
    return "Error{" +
        "errorCode='" + errorCode + '\'' +
        ", errorMessage='" + errorMessage + '\'' +
        '}';
  }
}
