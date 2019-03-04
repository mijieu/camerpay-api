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
import javax.xml.bind.annotation.XmlRootElement;

import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.util.HashUtils;

@NamedQueries({
	@NamedQuery (
			name = User.GET_USER_BY_EMAIL,
			query = "select p from User p where p.txtemail = :txtemail"
	)
})

@Entity
@Table(name = "T_USER")
@XmlRootElement
public class User extends BaseEntity{

	public static final String GET_USER_BY_EMAIL = "User.GET_USER_BY_EMAIL";
	private static final long serialVersionUID = 1L;
	private static final int KEY_LEN = 1024;
	private static final int ROUNDS = 100_021;
	
	@Column(name = "txtemail")
	private String txtemail;
	
	@Column(name = "DATBIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	@Column(name = "txtfirstname")
	private String txtfirstname;
	
	@Column(name = "txtlastname")
	private String txtlastname;
	
	@Column(name = "txtmiddlename")
	private String txtmiddlename;
	
	@Column(name = "txtpassword")
	private String txtpassword;
	
	@Column(name = "txtstatus")
	private UserStatus txtstatus;

	public String getTxtemail() {
		return txtemail;
	}

	public void setTxtemail(String txtemail) {
		this.txtemail = txtemail;
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

	public String getTxtfirstname() {
		return txtfirstname;
	}

	public void setTxtfirstname(String txtfirstname) {
		this.txtfirstname = txtfirstname;
	}

	public String getTxtlastname() {
		return txtlastname;
	}

	public void setTxtlastname(String txtlastname) {
		this.txtlastname = txtlastname;
	}

	public String getTxtmiddlename() {
		return txtmiddlename;
	}

	public void setTxtmiddlename(String txtmiddlename) {
		this.txtmiddlename = txtmiddlename;
	}
	public UserStatus getTxtstatus() {
		return txtstatus;
	}

	public void setTxtstatus(UserStatus txtstatus) {
		this.txtstatus = txtstatus;
	}
	
	public void setPassword(String password){
	    this.txtpassword = new String(obtainPasswordHash(password));
	}

	  public boolean checkPassword(String password) {
	    return txtstatus == UserStatus.ACTIVE 
	            && new String(obtainPasswordHash(password)).equals(this.txtpassword);
	  }

	  private byte[] obtainPasswordHash(String password) {
	    byte[] passwordHash = HashUtils.hashPassword(password, makeSalt(), ROUNDS, KEY_LEN);
	    return passwordHash;
	  }

	  private byte[] makeSalt() {
	    byte[] salt = new byte[32];
	    System.arraycopy(getId(), 0, salt, 0, 16);
	    System.arraycopy(getAccessKey(), 0, salt, 16, 16);
	    return salt;
	  }
	  
	  @Override
	  public String toString() {
		String userString = "\n\nuser.id: " + getId() + "\n" +
				"user.email: " + txtemail + "\n" + 
				"user.accessKey: " + getAccessKey() + "\n" +
				"user.txtpassword: " + txtpassword + "\n";
		
		return userString;
	  }
	
}
