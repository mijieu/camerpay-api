package cm.busime.camerpay.api.enumeration;

public enum UserStatus {
	NEW ("new"),
	ACTIVE ("active"),
	INACTIVE ("inactive"),
	RETIRED ("retired");
	
	private String status;
	
	private UserStatus (String status) {
		this.status = status;
	}
	
	public String getName() {
		return status;
	}
}
