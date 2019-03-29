package cm.busime.camerpay.api.setting;

import java.util.List;

import cm.busime.camerpay.api.entity.SystemSetting;
import cm.busime.camerpay.api.enumeration.SystemSettingKey;

public interface SystemSettingProvider {
	  Integer getIntegerSetting(SystemSettingKey key);
	  
	  Long getLongSetting(SystemSettingKey key);
	  
	  Double getDoubleSetting(SystemSettingKey key);
	  
	  String getStringSetting(SystemSettingKey key);
	  
	  boolean getBooleanSetting(SystemSettingKey key);
	
	  String getSystemProperty(String key);
	  
	  public void saveSystemSettings(final List<SystemSetting> systemSettingsList);
}
