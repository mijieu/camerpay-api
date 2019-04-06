package cm.busime.camerpay.api.enumeration;

public enum SystemSettingKey {
  
  MAIL_HOST("mailhost", "smtp.gmail.com"),
  MAIL_SMTP_PORT("mail.protocol.port","587"),
  MAIL_AUTH("mail.auth","true"),
  MAIL_ACTIVATION_FROM("mail.activation.from","bangou.dev@gmail.com"),
  MAIL_HOST_SECRET("mail.host.secret","$1981Clarkson5"),
  MAIL_ACTIVATION_BASEURL("mail.activation.baseurl","https://localhost:8443/camerpay/activation")
  ;

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