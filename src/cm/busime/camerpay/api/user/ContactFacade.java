package cm.busime.camerpay.api.user;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import cm.busime.camerpay.api.ResponseBuilder;
import cm.busime.camerpay.api.entity.Auth;
import cm.busime.camerpay.api.entity.User;
import cm.busime.camerpay.api.entity.Role;
import cm.busime.camerpay.api.entity.Contact;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.enumeration.RoleType;
import cm.busime.camerpay.api.error.CamerpayAPIException;

@Stateless
public class ContactFacade {
	
	private static final Logger log = Logger.getLogger(ContactFacade.class.getName());
	
	@Inject
	private ContactStore contactStore;
	
	public StatusCode saveContact (Contact contact) {
		if(contact == null) {
	    	throw new CamerpayAPIException(StatusCode.INVALID_CONTENT);
	    }
		if(!contactStore.contactExists(contact)){
			contactStore.createContact(contact);
			return StatusCode.CONTACT_CREATED;
		}
		else {
			return StatusCode.CONTACT_EXISTS;
		}
	}
	
	public Contact updateUser (Contact contact) {
		return contactStore.merge(contact);
	}
	
	public List<Contact> getUserContactList (String useridHex) {
		return contactStore.contactList(useridHex);
	}
	
	public Contact findContactById (final String id) {
		return contactStore.findContactById(id);
	}
	
	public List<Contact> listAllContacts () {
		return contactStore.listAllContacts();
	}
}
