package cm.busime.camerpay.api.entity;

import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import cm.busime.camerpay.api.util.HashUtils;


@Entity
@Table(name = "T_SYSTEM_SETTING")
@NamedQueries({
    @NamedQuery(
            name = SystemSetting.FIND_SYSTEM_SETTING_BY_KEY,
            query = "select e from SystemSetting e where e.key = :settingKey"
    ),
    @NamedQuery(
            name = SystemSetting.RETRIEVE_SYSTEM_SETTINGS,
            query = "select e from SystemSetting e"
    ),
    @NamedQuery(
            name = SystemSetting.RETRIEVE_SINGLE_SYSTEM_SETTINGS,
            query = "select e from SystemSetting e where e.key = :settingKey"
    ),
    @NamedQuery(
            name = SystemSetting.DELETE_SYSTEM_SETTINGS,
            query = "delete from SystemSetting e"
    ),
    @NamedQuery(
            name = SystemSetting.DELETE_SYSTEM_SETTINGS_BY_KEY,
            query = "delete from SystemSetting e where e.key = :settingKey"
    )
})
public class SystemSetting extends BaseEntity{
	
	public static final String FIND_SYSTEM_SETTING_BY_KEY = "FIND_SYSTEM_SETTING_BY_KEY";
	public static final String RETRIEVE_SYSTEM_SETTINGS = "RETRIEVE_SYSTEM_SETTINGS";
	public static final String RETRIEVE_SINGLE_SYSTEM_SETTINGS = "RETRIEVE_SINGLE_SYSTEM_SETTINGS";
	public static final String DELETE_SYSTEM_SETTINGS = "DELETE_SYSTEM_SETTINGS";
	public static final String DELETE_SYSTEM_SETTINGS_BY_KEY = "DELETE_SYSTEM_SETTINGS_BY_KEY";
	
	public static final String SETTING_KEY = "settingKey";
	
	@Id
	@Column(name = "id")
	public final String id = HashUtils.byte2hex(makeUuidAsBytes());
	
	public String getId() {
	  return id;
	}
	
	@Column(name = "SETTING_KEY", nullable = false, unique = true)
	private String key;

	@Column(name = "SETTING_VALUE", nullable = false)
	private String value;
	
	public SystemSetting() {
	    super();
	}

	public SystemSetting(String key, String value) {
	    this.key = key;
	    this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
