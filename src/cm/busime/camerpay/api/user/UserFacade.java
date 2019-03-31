package cm.busime.camerpay.api.user;

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
public class UserFacade {
	
	private static final Logger log = Logger.getLogger(UserFacade.class.getName());
	
	@Inject
	private UserStore userStore;
	
	@Inject
	private ResponseBuilder responseBuilder;
	
	public StatusCode saveUserRegistration (User user) {
		if(user == null) {
	    	throw new CamerpayAPIException(StatusCode.INVALID_CONTENT);
	    }
		if(!userStore.emailExists(user.getTxtemail())){
			userStore.createAuth(user);
			return StatusCode.USER_CREATED;
		}
		else {
			return StatusCode.USER_EXISTS;
		}
	}
	
	public User findUserByLoginName (final String loginName) {
		return userStore.findUserByLoginName(loginName);
	}
	
	public User findUserByAccesKey (final String accessKey) {
		return userStore.findUserByAccessKey(accessKey);
	}
	
	public User updateUser (User user) {
		return userStore.merge(user);
	}
	
	public void removeUser(User user) {
		userStore.removeUser(user);
	}
}
