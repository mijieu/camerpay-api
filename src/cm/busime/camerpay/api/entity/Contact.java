package cm.busime.camerpay.api.entity;


import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.util.HashUtils;

@NamedQueries({
	@NamedQuery (
			name = Contact.GET_CONTACT_BY_ID,
			query = "select c from Contact c where c.id = :id"
	),
	@NamedQuery (
			name = Contact.GET_CONTACT_BY_NAME,
			query = "select count(c.id) from Contact c where c.txtlastname = :txtlastname " + 
					"and c.txtfirstname = :txtfirstname " + 
					"and c.userid = :userid " +
					"and c.txtstatus = :status "
	),
	@NamedQuery (
			name = Contact.GET_CONTACT_LIST,
			query = "select c from Contact c where c.userid = :userid" + 
					" and c.txtstatus = :status"
	)
})


@Entity
@Table(name = "T_CONTACT")
public class Contact extends BaseEntity{

	public static final String GET_CONTACT_BY_ID = "Contact.GET_CONTACT_BY_ID";
	public static final String GET_CONTACT_BY_NAME = "Contact.GET_CONTACT_BY_NAME";
	public static final String GET_CONTACT_LIST = "Contact.GET_CONTACT_LIST";
	
	@Id
	@Column(name = "id")
	public final String id = HashUtils.byte2hex(makeUuidAsBytes());
	
	public String getId() {
	  return id;
	}
	
	@Column(name = "TXTEMAIL")
	private String email;
	
	@Column(name = "USER_ID")
	private byte[] userid;

	@Column(name = "txtfirstname")
	private String txtfirstname;
	
	@Column(name = "txtlastname")
	private String txtlastname;
	
	@Column(name = "txtmiddlename")
	private String txtmiddlename;
	
	@Column(name = "txtphonecode")
	private String txtphonecode;
	
	@Column(name = "txtphonenumber")
	private String txtphonenumber;
	
	@Column(name = "txtstatus")
	private UserStatus txtstatus = UserStatus.ACTIVE;
	
	@Transient
	@JsonIgnore
	private String userref;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getUserid() {
		return userid;
	}

	public void setUserid(byte[] userid) {
		this.userid = userid;
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

	public String getTxtphonecode() {
		return txtphonecode;
	}

	public void setTxtphonecode(String txtphonecode) {
		this.txtphonecode = txtphonecode;
	}

	public String getTxtphonenumber() {
		return txtphonenumber;
	}

	public void setTxtphonenumber(String txtphonenumber) {
		this.txtphonenumber = txtphonenumber;
	}
	
	public UserStatus getTxtstatus() {
		return txtstatus;
	}

	public void setTxtstatus(UserStatus txtstatus) {
		this.txtstatus = txtstatus;
	}

	@JsonIgnore
	public String getUserref() {
		return userref;
	}

	@JsonIgnore
	public void setUserref(String userref) {
		this.userref = userref;
	}

	@Override
	public String toString() {
		return "\nuser.id: " + HashUtils.byte2hex(getUserid()) + "\n" +
				"contact.lastname: " + getTxtlastname() + "\n" + 
				"contact.firstname: " + getTxtfirstname() + "\n" +
				"phone code: " + getTxtphonecode()+ "\n" +
				"pone number: " + getTxtphonenumber();
	}
}
