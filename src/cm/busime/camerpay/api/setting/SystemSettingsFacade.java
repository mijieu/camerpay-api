package cm.busime.camerpay.api.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import cm.busime.camerpay.api.CustomJsonBuilderFactory;
import cm.busime.camerpay.api.entity.SystemSetting;
import cm.busime.camerpay.api.enumeration.StatusCode;
import cm.busime.camerpay.api.error.CamerpayAPIException;


@Stateless
public class SystemSettingsFacade {

	 @Inject
	  private SystemSettingStore systemSettingStore;

	  @Inject
	  private CustomJsonBuilderFactory customJsonBuilderFactory;
	  
	  public JsonObject retrieveSystemSettings() {
		    return toJson(systemSettingStore.retrieveSystemSettings());
	  }
	  
	  public JsonObject retrieveSingleSystemSettings(String key) {
		    return toJson(systemSettingStore.retrieveSingleSystemSettings(key));
	  }
	  
	  public void saveSingleSystemSettings(SystemSetting systemSetting) {
		    systemSettingStore.saveSingleSystemSettings(systemSetting);
	  }

	  public void saveSystemSettigs(final JsonObject jsonObject) {
	    if(jsonObject == null) {
	      throw new CamerpayAPIException(StatusCode.INVALID_CONTENT);
	    }

	    systemSettingStore.saveSystemSettings(toList(jsonObject));
	  }


	  private JsonObject toJson(final List<SystemSetting> systemSettingsList) {
	    JsonObjectBuilder objectBuilder = customJsonBuilderFactory.createObjectBuilder();
	    systemSettingsList.forEach(item -> objectBuilder.add(item.getKey(), item.getValue()));
	    return objectBuilder.build();
	  }

	  private List<SystemSetting> toList(final JsonObject jsonObject) {
	    return jsonObject.keySet().stream().map(item-> new SystemSetting(item, jsonObject.getString(item))).collect(Collectors.toList());
	  }
}
