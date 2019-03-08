package cm.busime.camerpay.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import cm.busime.camerpay.api.util.HashUtils;
import static cm.busime.camerpay.api.util.UuidUtil.makeUuidAsBytes;

import java.util.Date;

/**
 * Base class for camerpay API.
 * <pre>
 * Contains the following technical fields:
 * - id: unique technical id, automatically generated for all entities
 * - version: technical versioning, used for optimistic locking
 * - created at: is automatically set on persist
 * - modified at: : is automatically set on persist and on subsequent updates
 * </pre>
 * *
 */


@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class BaseEntity {
	  
  @Id
  @Column(name = "id")
  @XmlTransient
  public final byte[] id = makeUuidAsBytes();

  public String getId() {
    return HashUtils.byte2hex(id);
  }
  
  public byte[] getIdAsByte() {
	    return id;
  }

  @Version
  @Column(name = "VERSION", precision = 19, scale = 0, nullable = false)
  @XmlTransient
  private Long version;

  @Column(name = "CREATED_AT", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @XmlTransient
  private Date createdAt;

  @Column(name = "MODIFIED_AT", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @XmlTransient
  private Date modifiedAt;
  
  

  public Long getVersion() {
    return version;
  }

  public void setVersion(final Long pVersion) {
    version = pVersion;
  }

  public Date getCreatedAt() {
    return new Date(createdAt.getTime());
  }

  public void setCreatedAt(Date createdAt) {
    if (createdAt != null) {
      this.createdAt = new Date(createdAt.getTime());
    }
  }

  public Date getModifiedAt() {
    return modifiedAt != null ? new Date(modifiedAt.getTime()) : null;
  }

  public void setModifiedAt(final Date modifiedAt) {
    if (modifiedAt != null) {
      this.modifiedAt = new Date(modifiedAt.getTime());
    }
  }

  @PrePersist
  public void onPrePersist() {
    Date date = new Date();
    setCreatedAt(date);
    setModifiedAt(date);
  }

  @PreUpdate
  public void onPreUpdate() {
    Date date = new Date();
    setModifiedAt(date);
  }

}
