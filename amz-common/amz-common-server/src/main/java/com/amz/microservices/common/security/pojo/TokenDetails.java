package com.amz.microservices.common.security.pojo;

import java.util.Set;
import java.util.UUID;

public interface TokenDetails {

  UUID getUserId();

  Set<String> getRolesNames();

}
