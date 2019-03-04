package cm.busime.camerpay.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery (
			name = Contact.GET_CONTACT_BY_ID,
			query = "select c from Contact c where c.email = :email"
	)
})

@Entity
@Table(name = "T_CONTACT")
public class Contact extends BaseEntity{

	public static final String GET_CONTACT_BY_ID = "Contact.GET_CONTACT_BY_ID";
	
	@Column(name = "TXTEMAIL")
	private String email;
	
	@Column(name = "DATBIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	@Column(name = "USER_ID")
	private String user_id;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
