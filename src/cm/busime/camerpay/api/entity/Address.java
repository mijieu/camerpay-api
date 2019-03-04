package cm.busime.camerpay.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "T_ADDRESS")
public class Address extends BaseEntity{
	
	@Column(name = "TXTSTREET")
	private String street;
	
	@Column(name = "TXTHOUSENR")
	private String houseNr;
	
	@Column(name = "TXTADDITIONAL")
	private String additional;
	
	@Column(name = "TXTZIP")
	private String zip;
	
	@Column(name = "TXTCITY")
	private String city;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNr() {
		return houseNr;
	}

	public void setHouseNr(String houseNr) {
		this.houseNr = houseNr;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
