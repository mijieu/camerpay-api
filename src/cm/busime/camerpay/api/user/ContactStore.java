package cm.busime.camerpay.api.user;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cm.busime.camerpay.api.entity.Auth;
import cm.busime.camerpay.api.entity.Contact;
import cm.busime.camerpay.api.entity.User;
import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.util.HashUtils;

public class ContactStore {

	@PersistenceContext(unitName="CAMERPAYAPIPU")
	private EntityManager em;
	

	private static final Logger log = Logger.getLogger(ContactStore.class.getName());
	
	public void createContact(Contact contact) {
		log.log(Level.INFO, "Contact before saving: " + contact.toString());
		em.persist(contact);
	}
	
	public Contact merge(Contact contact) {
		return em.merge(contact);
	}
	
	public boolean contactExists(Contact contact) {
		try {
			return em.createNamedQuery(Contact.GET_CONTACT_BY_NAME, Long.class)
					.setParameter("txtlastname", contact.getTxtlastname())
					.setParameter("txtfirstname", contact.getTxtfirstname())
					.setParameter("userid", contact.getUserid())
					.setParameter("status", UserStatus.ACTIVE)
					.getSingleResult() > 0;
					
		}catch(Exception e) {
			log.log(Level.INFO, e.getMessage());
			return false;
		}
	}
	
	public List<Contact> contactList(String useridHex) {
		List<Contact> contactList = em.createNamedQuery(Contact.GET_CONTACT_LIST, Contact.class)
					.setParameter("userid", HashUtils.hex2byte(useridHex))
					.setParameter("status", UserStatus.ACTIVE)
					.getResultList();
					
		if (contactList == null || contactList.isEmpty()) {
			log.log(Level.INFO, "No contact found!");
			return new ArrayList<Contact>();
		}
		log.log(Level.INFO, contactList.size() + " contact(s) found!");
		return contactList;
	}
	
	public Contact findContactById(String id) {
		Contact contact = null;
		try {
			contact = em.createNamedQuery(Contact.GET_CONTACT_BY_ID, Contact.class)
						.setParameter("id", id)
						.getSingleResult();
		} catch (Exception e) {
			contact = null;
		}
		return contact;
	}
	
	public List<Contact> listAllContacts() {
	    String jpql = "select c from Contact c";
	    return em.createQuery(jpql, Contact.class)
	    		.getResultList();
	}
}
