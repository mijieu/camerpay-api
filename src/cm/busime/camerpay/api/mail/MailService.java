
package cm.busime.camerpay.api.mail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import cm.busime.camerpay.api.AbstractService;
import cm.busime.camerpay.api.entity.MailTemplate;
import cm.busime.camerpay.api.enumeration.TemplateName;

@Transactional
public class MailService extends AbstractService {
	
	private static final Logger log = Logger.getLogger(MailService.class.getName());

  public MailTemplate findTemplateByName(TemplateName name, String lang) {
    try {
      return getEntityManager().createNamedQuery(MailTemplate.FIND_TEMPLATE_BY_NAME, MailTemplate.class)
  			.setParameter("name", name.name())
  			.setParameter("lang", lang)
  			.getSingleResult();
    } catch (Exception ex) {
    	log.log(Level.SEVERE, "error reading the mailtemplate: " + ex.getMessage());
    	return makeNewTemplate(name);
    }
  }
  
  private MailTemplate makeNewTemplate(TemplateName name) {
    // until the template editor is ready, this method creates hard coded mail templates
    switch (name) {
      case ActivationMail:
        return makeActivationMail();
      default:
        return new MailTemplate();
    }
  }

  private MailTemplate makeActivationMail() {
    String subject = "[Camerpay] Activate Your Account";
    String body = "Hello {firstName},\r\n\r\n"
            + "In order to complete your registration, "
            + "please click onto the following link {activation_link}";
    MailTemplate mailTemplate = new MailTemplate();
    mailTemplate.setName(TemplateName.ActivationMail.name());
    mailTemplate.setSubject(subject);
    mailTemplate.setBody(body);
    mailTemplate.setLang("en");
    return save(mailTemplate);
  }
}
