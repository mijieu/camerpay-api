package cm.busime.camerpay.api.user;

import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cm.busime.camerpay.api.entity.Auth;
import cm.busime.camerpay.api.entity.User;
import cm.busime.camerpay.api.entity.Role;
import cm.busime.camerpay.api.entity.Contact;
import cm.busime.camerpay.api.enumeration.RoleType;

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
//		Role role = new Role();
//		role.setUser_id(auth.getUser_id());
//		role.setRole(type);
//		em.persist(role);
	}
	
	public void createUserRegistration(User user) {
		em.persist(user);
	}
	
	public Role validateUser(String authString) {
		final StringTokenizer tokenizer = new StringTokenizer(authString, ":");
		String user = tokenizer.nextToken();
		String pwd = tokenizer.nextToken();
		Role role = null;
		try {
			role = em.createNamedQuery(Role.GET_USER_ROLE, Role.class)
				.setParameter("idUser", user)
				.setParameter("password", pwd)
				.setParameter("idRole", user)
				.getSingleResult();
		} catch (NoResultException e) {
			//log error
		}
		return role;
	}
	
	public boolean emailExists(String email) {
	    String jpql = "select count(u) from User u where u.txtemail = :email";
	    TypedQuery<Long> query = em.createQuery(jpql, Long.class);
	    query.setParameter("email", email);
	    long count = query.getSingleResult();
	    return count > 0;
	}
}
