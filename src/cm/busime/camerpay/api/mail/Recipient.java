
package cm.busime.camerpay.api.mail;

import javax.mail.Message.RecipientType;

public class Recipient {

  private final String _email;
  private final RecipientType _type;

  public Recipient(String email, RecipientType type) {
    _email = email.trim();
    _type = type;
  }

  public String getEmail() {
    return _email;
  }

  public RecipientType getType() {
    return _type;
  }

}
