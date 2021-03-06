package cm.busime.camerpay.api.entity;

import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import cm.busime.camerpay.api.enumeration.RoleType;
import cm.busime.camerpay.api.util.HashUtils;

@NamedQueries({
	@NamedQuery (
			name = Role.GET_USER_ROLE,
			query = "select r from Auth a, Role r where a.user_id = :idUser " + 
					"and a.password = :password " + 
					"and r.user_id = :idRole"
	)
})

@Entity
@Table(name = "T_USER_ROLE")
public class Role extends BaseEntity{
	
	public static final String GET_USER_ROLE = "Role.GET_USER_ROLE";
	
	@Id
	@Column(name = "id")
	public final String id = HashUtils.byte2hex(makeUuidAsBytes());
	
	public String getId() {
	  return id;
	}
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name="ROLE")
	@Enumerated(EnumType.STRING)
	private RoleType role;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
}
