package com.amz.microservices.common.object.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
  CUSTOMER(1),
  SELLER(2),
  ADMIN(3);

  private int id;

  public static AccountType getById(int id) {
    return Arrays.stream(values())
        .filter(userType -> userType.id == id)
        .findFirst()
        .orElse(null);
  }

}
