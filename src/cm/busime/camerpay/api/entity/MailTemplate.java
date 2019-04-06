
package cm.busime.camerpay.api.entity;

import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import cm.busime.camerpay.api.util.HashUtils;

@NamedQueries({
	@NamedQuery (
			name = MailTemplate.FIND_TEMPLATE_BY_NAME,
			query = "select mt from MailTemplate mt where mt.name = :name and mt.lang = :lang"
	)
})

@Entity
@Table(name = "T_MAILTEMPLATE")
public class MailTemplate implements Serializable {

	  private static final long serialVersionUID = 1L;
	  
	  public static final String FIND_TEMPLATE_BY_NAME = "MailTemplate.FIND_TEMPLATE_BY_NAME";
	  
	  @Id
	  @Column(name = "id")
	  public final String id = HashUtils.byte2hex(makeUuidAsBytes());
		
	  public String getId() {
		return id;
	  }
	  
	  @Column(name = "name")
	  private String name = "";
	
	  @Size(max = 100)
	  public String getName() {
	    return name;
	  }
	
	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  @Column(name = "subject")
	  private String subject = "";
	
	  @Size(max = 200)
	  public String getSubject() {
	    return subject;
	  }
	
	  public void setSubject(String subject) {
		  this.subject = subject;
	  }
	
	  @Column(name = "body")
	  private String body = "";
	
	  @Size(max = 10000)
	  public String getBody() {
	    return body;
	  }
	
	  public void setBody(String body) {
		  this.body = body;
	  } 
	  
	  @Column(name = "lang")
	  private String lang = "";
	
	  @Size(max = 10)
	  public String getLang() {
	    return lang;
	  }
	
	  public void setLang(String lang) {
	    this.lang = lang;
	  }

}
