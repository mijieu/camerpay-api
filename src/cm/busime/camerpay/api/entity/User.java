package cm.busime.camerpay.api.entity;

import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.util.HashUtils;

@NamedQueries({
	@NamedQuery (
			name = User.GET_USER_BY_LOGIN_NAME,
			query = "select p from User p where p.txtemail = :txtemail"
	),
	@NamedQuery (
			name = User.GET_USER_BY_ACCESS_KEY,
			query = "select p from User p where p.accessKey = :accessKey"
	)
})

@Entity
@Table(name = "T_USER")
@XmlRootElement
public class User extends BaseEntity {
	
	private static final Logger log = Logger.getLogger(User.class.getName());

	public static final String GET_USER_BY_LOGIN_NAME = "User.GET_USER_BY_LOGIN_NAME";
	public static final String GET_USER_BY_ACCESS_KEY = "User.GET_USER_BY_ACCESS_KEY";
	private static final long serialVersionUID = 1L;
	private static final int KEY_LEN = 1024;
	private static final int ROUNDS = 100_021;
	
	@Id
	@Column(name = "id")
	public final byte[] id = makeUuidAsBytes();
	
	public String getId() {
	  return HashUtils.byte2hex(id);
	}
	  
	public byte[] getIdAsByte() {
		 return id;
	}
	
	@Column(name = "accessKey")
	private final byte[] accessKey = makeUuidAsBytes();
	  
	 public String getAccessKey() {
		    return HashUtils.byte2hex(accessKey);
	 }
	
	@Column(name = "txtemail")
	private String txtemail;
	
	@Column(name = "DATBIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_ID")
	private Contact contact;
	
	@Column(name = "txtfirstname")
	private String txtfirstname;
	
	@Column(name = "txtlastname")
	private String txtlastname;
	
	@Column(name = "txtmiddlename")
	private String txtmiddlename;
	
	@Column(name = "txtpassword")
	private byte[] password;
	
	@Transient
	private String txtpassword;
	
	@Column(name = "txtstatus")
	private UserStatus txtstatus = UserStatus.NEW;

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
	    this.password = obtainPasswordHash(password);
	}
	
	public String getPassword() {
	    return HashUtils.byte2hex(this.password);
    }

	public String getTxtpassword() {
		return txtpassword;
	}

	public void setTxtpassword(String txtpassword) {
		this.txtpassword = txtpassword;
	}

	public boolean checkPassword(String password) {
		byte[] reqPasswordHash = obtainPasswordHash(password);
	    return txtstatus == UserStatus.ACTIVE 
	            && Arrays.equals(reqPasswordHash, this.password);
	}
	
	private byte[] obtainPasswordHash(String password) {
	    return HashUtils.hashPassword(password, makeSalt(), ROUNDS, KEY_LEN);
	}
	
	private byte[] makeSalt() {
	    byte[] salt = new byte[32];
	    System.arraycopy(id, 0, salt, 0, 16);
	    System.arraycopy(accessKey,  0, salt, 16, 16);
	    return salt;
	}
	  
	@Override
	public String toString() {
		return "\nuser.id: " + getId() + "\n" +
				"user.email: " + txtemail + "\n" + 
				"user.accessKey: " + getAccessKey() + "\n" +
				"password byte: " + getPassword()+ "\n" +
				"password text: " + txtpassword;
	}
	
}
