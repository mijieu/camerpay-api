package cm.busime.camerpay.api.enumeration;

public enum RoleType {
	ADMIN ("admin"),
	PUBLIC ("public"),
	MEMBER ("member");
	
	private String role;
	
	private RoleType (String role) {
		this.role = role;
	}
	
	public String getName() {
		return role;
	}
}
