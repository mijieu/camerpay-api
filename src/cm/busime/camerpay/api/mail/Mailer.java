package cm.busime.camerpay.api.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import cm.busime.camerpay.api.entity.MailTemplate;
import cm.busime.camerpay.api.enumeration.SystemSettingKey;
import cm.busime.camerpay.api.enumeration.TemplateName;
import cm.busime.camerpay.api.setting.CachedProvider;
import cm.busime.camerpay.api.setting.SystemSettingProvider;

@Dependent
public class Mailer {

	protected static final Logger log = Logger.getLogger(Mailer.class.getName());

	@Inject
	@CachedProvider
	SystemSettingProvider setting;
	
	@Inject
	MailService mailService;

	public boolean sendMail(String to, String subject, String body) {
		List<Recipient> recipients = new ArrayList<>();
		recipients.add(new Recipient(to, RecipientType.TO));
		return sendMail(setting.getStringSetting(SystemSettingKey.MAIL_ACTIVATION_FROM),
				recipients, subject, body);
	}

	public boolean sendMail(String from, List<Recipient> recipients, String subject,
			String body, String... files) {
		try {
			Properties properties = obtainConfig();
			Session session = Session.getInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(from,
									setting.getStringSetting(SystemSettingKey.MAIL_HOST_SECRET));
						}
					});
			MimeMessage message = composeMessage(session, from, recipients, subject, body, files);
			Transport.send(message);
			return true;
		} catch (MessagingException mex) {
			log.log(Level.SEVERE, "Send activation mail failed {0}", mex.getMessage());
			return false;
		}
	}
	
	public MailTemplate findTemplateByName(TemplateName templateName) {
	    return mailService.findTemplateByName(templateName);
	}
	
	public String getActivationBaseurl() {
		return setting.getStringSetting(SystemSettingKey.MAIL_ACTIVATION_BASEURL);
	}

	private Properties obtainConfig() {
		Properties prop = System.getProperties();
		//prop.put("mail.transport.protocol.rfc822", "smtp"); 
		prop.put("mail.smtp.starttls.enable", "true");   
		prop.put("mail.smtp.host", setting.getStringSetting(SystemSettingKey.MAIL_HOST));
		prop.put("mail.smtp.port", setting.getStringSetting(SystemSettingKey.MAIL_SMTP_PORT));
		prop.put("mail.smtp.auth", setting.getBooleanSetting(SystemSettingKey.MAIL_AUTH));
		return prop;
	}

	private MimeMessage composeMessage(Session session, String from, List<Recipient> recipients, String subject,
			String body, String[] files) throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		for (Recipient recipient : recipients) {
			message.addRecipient(recipient.getType(), new InternetAddress(recipient.getEmail()));
		}
		message.setSubject(subject);
		message.setContent(getMultipartBody(body, files));
		message.setFrom(new InternetAddress(from));
		return message;
	}

	private Multipart getMultipartBody(String body, String[] files) throws MessagingException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(body);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		for (String file : files) {
			addAttachment(multipart, file);
		}
		return multipart;
	}

	private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
		if (filename.isEmpty()) {
			return;
		}
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		File file = new File(filename);
		messageBodyPart.setFileName(file.getName());
		multipart.addBodyPart(messageBodyPart);
	}
}
