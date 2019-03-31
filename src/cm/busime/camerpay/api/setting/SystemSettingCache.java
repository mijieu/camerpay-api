package cm.busime.camerpay.api.setting;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cm.busime.camerpay.api.entity.SystemSetting;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.enumeration.SystemSettingKey;
import cm.busime.camerpay.api.error.CamerpayAPIException;


@CachedProvider
@Singleton
@Startup
@ConcurrencyManagement(value = ConcurrencyManagementType.BEAN)
public class SystemSettingCache implements SystemSettingProvider{
	
	private static final Logger log = Logger.getLogger(SystemSettingCache.class.getName());

	@PersistenceContext(unitName="CAMERPAYAPIPU")
	private EntityManager em;
	
	private volatile Map<String, String> cache;
	
	@PostConstruct
	  public void postContstruct() {
	    loadCache();
	  }
	
	public String getSystemSetting(final SystemSettingKey key) {
	    if(cache == null) {
	      throw new CamerpayAPIException(StatusCode.CACHE_ERROR);
	    }
	    return cache.get(key.getName());
	}
	  @Override
	  public Integer getIntegerSetting(SystemSettingKey key) {
	    String value = getStringSetting(key);
	    return convertToInteger(key, value);
	  }

	  @Override
	  public Long getLongSetting(SystemSettingKey key) {
	    String value = getStringSetting(key);
	    return convertToLong(key, value);
	  }

	  @Override
	  public Double getDoubleSetting(SystemSettingKey key) {
	    String value = getStringSetting(key);
	    return convertToDouble(key, value);
	  }

	  @Override
	  public boolean getBooleanSetting(SystemSettingKey key) {
	    String value = getStringSetting(key);
	    return value != null && Boolean.parseBoolean(value.trim());
	  }

	  @Override
	  public String getStringSetting(SystemSettingKey key) {
	    String setting = getSystemSetting(key);
	    if (setting != null) {
	    	log.log(Level.INFO, "read key from cache: " + key.getName());
	      return setting;
	    } else if (key.getDefaultValue() != null) {
	    	log.log(Level.INFO, "key not in cache. get the default value: " + key.getName());
	      return key.getDefaultValue();
	    } else {
	      throw new CamerpayAPIException(StatusCode.SETTING_NOT_CONFIGURED, key.getName());
	    }
	  }

	  @Override
	  public String getSystemProperty(String key) {
	    return System.getProperty(key);
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
	  
	  private Integer convertToInteger(SystemSettingKey key, String value) {
		    try {
		      return value == null || value.trim().isEmpty() ? null : Integer.parseInt(value);
		    } catch (NumberFormatException e) {
		      throw new CamerpayAPIException(StatusCode.SERVICE_CONFIGURATION_ERROR, e, key.getName() + " is not configured correctly.");
		    }
		  }

		  private Long convertToLong(SystemSettingKey key, String value) {
		    try {
		      return value == null || value.trim().isEmpty() ? null : Long.parseLong(value);
		    } catch (NumberFormatException e) {
		      throw new CamerpayAPIException(StatusCode.SERVICE_CONFIGURATION_ERROR, e, key.getName() + " is not configured correctly.");
		    }
		  }

		  private Double convertToDouble(SystemSettingKey key, String value) {
		    try {
		      return value == null || value.trim().isEmpty() ? null : Double.parseDouble(value);
		    } catch (NumberFormatException e) {
		      throw new CamerpayAPIException(StatusCode.SERVICE_CONFIGURATION_ERROR, e, key.getName() + " is not configured correctly.");
		    }
		  }

		@Override
		public void saveSystemSettings(List<SystemSetting> systemSettingsList) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public SystemSetting retrieveSetting(String settingName) {
			// TODO Auto-generated method stub
			return null;
		}
}