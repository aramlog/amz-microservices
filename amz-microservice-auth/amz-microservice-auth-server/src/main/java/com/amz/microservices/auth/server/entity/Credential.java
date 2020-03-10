package com.amz.microservices.auth.server.entity;

import com.amz.microservices.common.object.enums.AccountType;
import com.amz.microservices.common.security.pojo.TokenDetails;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "amz_credential")
public class Credential extends AbstractEntity implements TokenDetails {

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "user_type", nullable = false)
  private Integer userType;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Override
  public Set<String> getRolesNames() {
    return Collections.singleton(AccountType.getById(userType).name());
  }

}
