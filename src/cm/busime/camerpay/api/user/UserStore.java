package cm.busime.camerpay.api.user;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cm.busime.camerpay.api.entity.Auth;
import cm.busime.camerpay.api.entity.User;
import cm.busime.camerpay.api.util.HashUtils;

public class UserStore {

	@PersistenceContext(unitName="CAMERPAYAPIPU")
	private EntityManager em;
	

	private static final Logger log = Logger.getLogger(UserStore.class.getName());
	
	public boolean isAuthExist (String user_id) {
		List<Auth> rs = em.createNamedQuery(Auth.GET_USER_ID, Auth.class)
				.setParameter("user_id", user_id)
				.getResultList();
		return rs != null && rs.size() > 0;
	}
	
	public void createAuth(User user) {
		log.log(Level.INFO, "User before saving: " + user.toString());
		em.persist(user);
	}
	
	public User merge(User user) {
		return em.merge(user);
	}
	
	public User findUserByLoginName(String loginName) {
		User user = null;
		try {
			user = em.createNamedQuery(User.GET_USER_BY_LOGIN_NAME, User.class)
						.setParameter("txtemail", loginName)
						.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public User findUserByAccessKey(String accessKey) {
		User user = null;
		try {
			user = em.createNamedQuery(User.GET_USER_BY_ACCESS_KEY, User.class)
					.setParameter("accessKey", HashUtils.hex2byte(accessKey))
				.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		return user;
	}
	
	public boolean emailExists(String email) {
	    String jpql = "select count(u) from User u where u.txtemail = :email";
	    TypedQuery<Long> query = em.createQuery(jpql, Long.class);
	    query.setParameter("email", email);
	    long count = query.getSingleResult();
	    return count > 0;
	}
}
