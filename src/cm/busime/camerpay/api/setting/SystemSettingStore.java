package cm.busime.camerpay.api.setting;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cm.busime.camerpay.api.entity.SystemSetting;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.enumeration.SystemSettingKey;
import cm.busime.camerpay.api.error.CamerpayAPIException;

public class SystemSettingStore implements SystemSettingProvider {

	@PersistenceContext(unitName="CAMERPAYAPIPU")
	private EntityManager em;

	private static final Logger log = Logger.getLogger(SystemSettingStore.class.getName());
	
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
	public String getStringSetting(SystemSettingKey key) {
	    SystemSetting setting = retrieveSetting(key.getName());
	    if (setting != null) {
	      return setting.getValue();
	    } else if (key.getDefaultValue() != null) {
	      return key.getDefaultValue();
	    } else {
	      throw new CamerpayAPIException(StatusCode.SETTING_NOT_CONFIGURED, key.getName());
	    }
	}

	@Override
	public boolean getBooleanSetting(SystemSettingKey key) {
		String value = getStringSetting(key);
	    return value != null && Boolean.parseBoolean(value.trim());
	}

	@Override
	public String getSystemProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void saveSystemSettings(final List<SystemSetting> systemSettingsList) {
	    em.createNamedQuery(SystemSetting.DELETE_SYSTEM_SETTINGS).executeUpdate();
	    systemSettingsList.forEach((systemSetting) -> em.persist(systemSetting));
	}
	
	public void saveSingleSystemSettings(SystemSetting systemSetting) {
		List<SystemSetting> settingList = retrieveSingleSystemSettings(systemSetting.getKey());
		if ( settingList != null) {
			settingList.forEach((setting) -> em.createNamedQuery(SystemSetting.DELETE_SYSTEM_SETTINGS_BY_KEY)
					.setParameter("settingKey", systemSetting.getKey())
					.executeUpdate());
		}
		em.persist(systemSetting);
	}
	
	public List<SystemSetting> retrieveSystemSettings() {
	    return em.createNamedQuery(SystemSetting.RETRIEVE_SYSTEM_SETTINGS, SystemSetting.class).getResultList();
	}
	
	public List<SystemSetting> retrieveSingleSystemSettings(String key) {
		try {
			return em.createNamedQuery(SystemSetting.RETRIEVE_SINGLE_SYSTEM_SETTINGS, SystemSetting.class)
					.setParameter("settingKey", key)
					.getResultList();
		} catch (Exception e) {
		      return null;
		}
	}
	
	@Override
	public SystemSetting retrieveSetting(String settingName) {
	    try {
	      return em.createNamedQuery(SystemSetting.FIND_SYSTEM_SETTING_BY_KEY, SystemSetting.class)
	              .setParameter(SystemSetting.SETTING_KEY, settingName)
	              .getSingleResult();
	    } catch (Exception e) {
	      return null;
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

}
