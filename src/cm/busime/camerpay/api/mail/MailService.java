
package cm.busime.camerpay.api.mail;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import cm.busime.camerpay.api.AbstractService;
import cm.busime.camerpay.api.entity.MailTemplate;
import cm.busime.camerpay.api.enumeration.TemplateName;

@Transactional
public class MailService extends AbstractService {

  public MailTemplate findTemplateByName(TemplateName name) {
    TypedQuery<MailTemplate> query = getEntityManager().createNamedQuery(MailTemplate.FIND_TEMPLATE_BY_NAME, MailTemplate.class)
    			.setParameter("name", name.name());
    try {
      return query.getSingleResult();
    } catch (Exception ex) {
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
    String subject = "Activate Camerpay account";
    String body = "Hello {firstName},\r\n\r\n"
            + "In order to complete your registration, "
            + "please click onto the following link {link}";
    MailTemplate mailTemplate = new MailTemplate();
    mailTemplate.setName(TemplateName.ActivationMail.name());
    mailTemplate.setSubject(subject);
    mailTemplate.setBody(body);
    return save(mailTemplate);
  }
}
