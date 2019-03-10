package cm.busime.camerpay.api.user;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import cm.busime.camerpay.api.ResponseBuilder;
import cm.busime.camerpay.api.entity.Contact;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.enumeration.UserStatus;
import cm.busime.camerpay.api.util.HashUtils;

@Path("contact")
public class ContactResource {

	private static final Logger log = Logger.getLogger(ContactResource.class.getName());
	
	@Inject
	private ResponseBuilder responseBuilder;
	
	@Inject
	ContactFacade contactFacade;

	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response contactCreation(Contact contact){
		contact.setUserid(HashUtils.hex2byte(contact.getUserref()));
		log.log(Level.INFO, "...incomming contact creation request: " + contact.toString());
		StatusCode resultCode = contactFacade.saveContact(contact);
		Response resp = responseBuilder.getResourceNotFoundResponse();
		switch (resultCode.getResponseStatus()) {
			case 201:
				resp = responseBuilder
			            .statusCreated()
			            .entity(responseBuilder.getResponseSimple("true", StatusCode.CONTACT_CREATED))
			            .build();
				break;
			case 202:
				resp = responseBuilder
						.status(StatusCode.CONTACT_EXISTS.getResponseStatus())
						.entity(responseBuilder.getResponseSimple("false", StatusCode.CONTACT_EXISTS, new Object[] {contact.getTxtfirstname(), contact.getTxtlastname()}))
						.build();
				break;
			default:
		}
		return resp;
	}
	
	@PUT
	@Path("account/{id}/status/{status}")
	@Consumes({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public Response updateContactStatus(@PathParam("id") final String id, @PathParam("status") final String status){
		Contact contact = contactFacade.findContactById(id);
		if (contact == null) {
			return responseBuilder
				.status(StatusCode.CONTACT_NOT_EXISTS.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.CONTACT_NOT_EXISTS))
				.build();
		}
		if (null != status && !status.isEmpty()) {
			switch(status) {
				case "enable":
					contact.setTxtstatus(UserStatus.ACTIVE);
					break;
				case "disable":
					contact.setTxtstatus(UserStatus.INACTIVE);
					break;
				case "delete":
					contact.setTxtstatus(UserStatus.RETIRED);
					break;
				default:
					return responseBuilder
							.statusOk()
							.entity(responseBuilder.getResponseSimple("false", StatusCode.STATUS_NOT_EXIST, new Object[] {status}))
							.build();
			}
		}
		else
			return responseBuilder
					.statusOk()
					.entity(responseBuilder.getResponseSimple("false", StatusCode.STATUS_NOT_EXIST, new Object[] {status}))
					.build();
		
		contactFacade.updateUser(contact);
		return responseBuilder
				.statusOk()
				.entity(responseBuilder.getResponseSimple("true", StatusCode.CONTACT_UPDATED))
				.build();
	}
	
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateContact(Contact contact){
		log.log(Level.INFO, "...incomming contact update request: " + contact.toString());
		if (contact != null && null != contact.getId()) {
			Contact con = contactFacade.findContactById(contact.getId());
			if (null == con)
				return responseBuilder
					.status(StatusCode.CONTACT_NOT_EXISTS.getResponseStatus())
					.entity(responseBuilder.getResponseSimple("false", StatusCode.CONTACT_NOT_EXISTS))
					.build();
			contactFacade.updateUser(contact);
			return responseBuilder
					.statusOk()
					.entity(responseBuilder.getResponseSimple("true", StatusCode.CONTACT_UPDATED))
					.build();
		}
		return responseBuilder
				.status(StatusCode.INVALID_CONTENT.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.INVALID_CONTENT))
				.build();
	}
	
	@GET
	@Path("list/user/{id}")
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public List<Contact> userContactList(@PathParam("id") final String useridHex){
		log.log(Level.INFO, "...incomming contact list request for userId: " + useridHex);
		List<Contact> allContact = contactFacade.listAllContacts();
		for (Contact c:allContact)
			log.log(Level.INFO, c.toString());
		return contactFacade.getUserContactList(useridHex);
	}
}
