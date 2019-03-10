package cm.busime.camerpay.api.user;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.util.Base64;

import cm.busime.camerpay.api.ResponseBuilder;
import cm.busime.camerpay.api.entity.User;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.enumeration.UserStatus;

@Path("user")
public class UserResource {

	private static final Logger log = Logger.getLogger(UserResource.class.getName());
	
	@Inject
	private ResponseBuilder responseBuilder;
	
	@Inject
	UserFacade userFacade;
	
	
	@GET
	@Path("authentication/login/{loginName}/password/{password}")
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public Response userAuthentication(@PathParam("loginName") final String loginName,
							 @PathParam("password") final String password){
		log.log(Level.INFO, "...incomming authentication request for " + loginName + " with pwd " + password);
		User user = userFacade.findUserByLoginName (loginName);
		if (user == null) {
			log.log(Level.INFO, "authentication failed for " + loginName);
			return responseBuilder
				.status(StatusCode.USER_NOT_EXIST.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_NOT_EXIST, new Object[] {loginName}))
				.build();
		}
		log.log(Level.INFO, "...retrieved user: " + user.toString());
		if (!user.getTxtstatus().equals(UserStatus.ACTIVE))
			return responseBuilder
					.status(StatusCode.USER_NOT_ACTIVATED.getResponseStatus())
					.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_NOT_ACTIVATED))
					.build();
		
		if (!user.checkPassword(password))
			return responseBuilder
				.status(StatusCode.USER_AUTHENTICATION_FAILED.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_AUTHENTICATION_FAILED))
				.build();
		
		return responseBuilder
				.statusOk()
				.entity(responseBuilder.getResponseSimple("true", StatusCode.USER_AUTHENTICATION_SUCCESSFULL))
				.build();
	}
	
	@POST
	@Path("registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRegistration(User user){
		user.setPassword(user.getTxtpassword());
		log.log(Level.INFO, "...incomming registration request: " + user.toString());
			StatusCode resultCode = userFacade.saveUserRegistration(user);
			Response resp = responseBuilder.getResourceNotFoundResponse();
			switch (resultCode.getResponseStatus()) {
				case 201:
					resp = responseBuilder
				            .statusCreated()
				            .entity(responseBuilder.getResponseSimple("true", StatusCode.USER_CREATED, user.getAccessKey()))
				            .build();
					break;
				case 202:
					resp = responseBuilder
							.status(StatusCode.USER_EXISTS.getResponseStatus())
							.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_EXISTS, new Object[] {"Email already exists"}))
							.build();
					break;
				default:
			}
		return resp;
	}
	
	@PUT
	@Path("account/{key}")
	@Consumes({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public Response activateUserAccount(@PathParam("key") final String accessKey){
		User user = userFacade.findUserByAccesKey(accessKey);
		if (user == null) {
			return responseBuilder
				.status(StatusCode.USER_ACCESS_KEY_NOT_EXIST.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_ACCESS_KEY_NOT_EXIST,new Object[] {accessKey}))
				.build();
		}
		user.setTxtstatus(UserStatus.ACTIVE);
		userFacade.updateUser(user);
		return responseBuilder
				.statusOk()
				.entity(responseBuilder.getResponseSimple("true", StatusCode.USER_STATUS_UPDATED, new Object[] {user.getTxtemail()}))
				.build();
	}
	
	@PUT
	@Path("account/{key}/status/{status}")
	@Consumes({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public Response updateUserStatus(@PathParam("key") final String accessKey, @PathParam("status") final String status){
		User user = userFacade.findUserByAccesKey(accessKey);
		if (user == null) {
			return responseBuilder
				.status(StatusCode.USER_ACCESS_KEY_NOT_EXIST.getResponseStatus())
				.entity(responseBuilder.getResponseSimple("false", StatusCode.USER_ACCESS_KEY_NOT_EXIST,new Object[] {accessKey}))
				.build();
		}
		if (null != status && !status.isEmpty()) {
			switch(status) {
				case "enable":
					user.setTxtstatus(UserStatus.ACTIVE);
					break;
				case "disable":
					user.setTxtstatus(UserStatus.INACTIVE);
					break;
				case "delete":
					user.setTxtstatus(UserStatus.RETIRED);
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
		
		userFacade.updateUser(user);
		return responseBuilder
				.statusOk()
				.entity(responseBuilder.getResponseSimple("true", StatusCode.USER_STATUS_UPDATED, new Object[] {user.getTxtemail()}))
				.build();
	}
	
	@PUT
	@Path("account/{id}")
	@Produces({MediaType.APPLICATION_JSON + "; charset=utf-8"})
	public Response deleteAccount(@PathParam("id") final String user_id){
			return responseBuilder
					.statusOk()
					.entity(getUserAuthenticationInfo(user_id))
					.build();
	}
	
	private JsonObject getUserAuthenticationInfo (String authString) {
		try {
			String decodedAuthString = new String(Base64.decode(authString));
			return null;
		}catch(IOException e) {
			return responseBuilder.getResponseSimple("false", StatusCode.USER_AUTHENTICATION_FAILED, new Object[] { e.getMessage()});
		}
	}
}
