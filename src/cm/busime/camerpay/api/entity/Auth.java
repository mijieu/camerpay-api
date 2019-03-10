package cm.busime.camerpay.api.entity;

import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import cm.busime.camerpay.api.util.HashUtils;


@NamedQueries({
	@NamedQuery (
			name = Auth.GET_USER_ID,
			query = "select a from Auth a where a.user_id = :user_id"
	),
	@NamedQuery (
			name = Auth.GET_USER_FOR_AUTH,
			query = "select r.role from Auth a, Role r where a.user_id = :user_id " + 
					"and a.password = :password " + 
					"and r.user_id = :user_id"
	)
})
@Entity
@Table(name = "T_AUTH")
public class Auth extends BaseEntity{
	
	public static final String GET_USER_ID = "Auth.GET_USER_ID";
	public static final String GET_USER_FOR_AUTH = "Auth.GET_USER_FOR_AUTH";
	
	@Id
	@Column(name = "id")
	public final String id = HashUtils.byte2hex(makeUuidAsBytes());
	
	public String getId() {
	  return id;
	}
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name = "PASSWORD")
	private String password;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
