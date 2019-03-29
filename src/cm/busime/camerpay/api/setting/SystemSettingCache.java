package cm.busime.camerpay.api.setting;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cm.busime.camerpay.api.entity.SystemSetting;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.error.CamerpayAPIException;


@CachedProvider
@Singleton
@Startup
@ConcurrencyManagement(value = ConcurrencyManagementType.BEAN)
public class SystemSettingCache {
	
	private static final Logger log = Logger.getLogger(SystemSettingCache.class.getName());

	@PersistenceContext(unitName="CAMERPAYAPIPU")
	private EntityManager em;
	
	private volatile Map<String, String> cache;
	
	public String getSystemSetting(final String key) {
	    if(cache == null) {
	      throw new CamerpayAPIException(StatusCode.CACHE_ERROR);
	    }
	    return cache.get(key);
	}
	
	@Schedule(hour = "*", minute = "*/5", persistent = false)
	  public void schedule() {
	    loadCache();
	  }


	  private void loadCache() {
	    try {
	      log.log(Level.INFO,"SYSTEM SETTING CACHE: Loading Cache");
	      final List<SystemSetting> settings = em.createNamedQuery(SystemSetting.RETRIEVE_SYSTEM_SETTINGS, SystemSetting.class).getResultList();
	      final Map<String, String> newMap = settings.stream().collect(Collectors.toMap(SystemSetting::getKey,SystemSetting::getValue));
	      if(newMap != null) {
	        cache = Collections.unmodifiableMap(newMap);
	      }
	    } catch(Exception ex) {
	    	log.log(Level.SEVERE, "SYSTEM SETTING CACHE: Can't load System Setting Options Cache", ex);
	    }
	  }
}