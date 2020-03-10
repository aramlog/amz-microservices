package com.amz.microservices.account.server.entity;

import com.amz.microservices.common.object.enums.GenderType;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "amz_profile")
public class Profile implements Serializable {

  private static final long serialVersionUID = 3746190553880703291L;

  @Id
  private UUID id;

  @Column(name = "first_name", nullable = false, length = 128)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 128)
  private String lastName;

  @Email
  @Column(name = "email", nullable = false, length = 256)
  private String email;

  @Column(name = "birthday")
  private Date birthday;

  @Column(name = "mobile", length = 50)
  private String mobile;

  @Column(name = "picture", length = 512)
  private String picture;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private GenderType gender;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  private Account account;

  @PrePersist
  public void prePersist() {
    if (id == null && account != null) {
      id = account.getId();
    }
  }
}
