package com.amz.microservices.common.object;

public interface Pattern {

  /**
   * Date and DateTime patterns
   */
  String DATE_PATTERN = "yyyy-MM-dd";
  String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  /**
   * Password pattern
   */
  String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[A-Z]).{8,128})";

}
