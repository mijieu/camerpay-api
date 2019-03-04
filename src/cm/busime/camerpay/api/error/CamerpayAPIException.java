package cm.busime.camerpay.api.error;

import java.io.Serializable;

import static java.text.MessageFormat.format;

import cm.busime.camerpay.api.enumeration.StatusCode;

public class CamerpayAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private StatusCode code;
	private final Object[] messageParameters;
	
	public CamerpayAPIException(final StatusCode pErrorCode, final Throwable pCause, final Object... pParameters) {
	    super(pParameters == null ? pErrorCode.getMessageFormat() : format(pErrorCode.getMessageFormat(), pParameters), pCause);
	    this.code = pErrorCode;
	    this.messageParameters = pParameters == null ? null : pParameters.clone();
	 }
	
	 public CamerpayAPIException(final String pInternalMessage, final StatusCode pErrorCode, final Object... pErrorMessageParameters) {
	    super(pInternalMessage);
	    this.code = pErrorCode;
	    this.messageParameters = pErrorMessageParameters;
	 }
	
	  public CamerpayAPIException(final StatusCode pErrorCode, final Object... pMessageParameter) {
	    this(pErrorCode, null, pMessageParameter);
	  }
	
	  public CamerpayAPIException(final StatusCode pErrorCode) {
	    this(pErrorCode, null);
	  }
    
	public StatusCode getCode() {
		return code;
	}
	public void setCode(StatusCode code) {
		this.code = code;
	}
	
	public Object[] getMessageParameters() {
	    return messageParameters == null ? null : messageParameters.clone();
	  }
}
