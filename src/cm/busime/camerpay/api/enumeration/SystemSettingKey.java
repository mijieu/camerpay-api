package cm.busime.camerpay.api.enumeration;

public enum SystemSettingKey {
  
  MAIL_HOST("mailhost", "localhost"),
  SMTP_PORT("smtpport","25");

  private final String name;
  private final String defaultValue;

  private SystemSettingKey(String name) {
    this.name = name;
    this.defaultValue = null;
  }

  private SystemSettingKey(String name, String defaultValue) {
    this.name = name;
    this.defaultValue = defaultValue;
  }

  public String getName() {
    return name;
  }

  public String getDefaultValue() {
    return defaultValue;
  }
}