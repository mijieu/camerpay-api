package cm.busime.camerpay.api.user;

import java.util.List;
import java.util.StringTokenizer;

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
	
	public boolean isAuthExist (String user_id) {
		List<Auth> rs = em.createNamedQuery(Auth.GET_USER_ID, Auth.class)
				.setParameter("user_id", user_id)
				.getResultList();
		return rs != null && rs.size() > 0;
	}
	
	public void createAuth(User user) {
		em.persist(user);
	}
	
	public User merge(User user) {
		return em.merge(user);
	}
	
	public User authenticateUser(String loginName) {
		User user = null;
		try {
			user = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class)
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
