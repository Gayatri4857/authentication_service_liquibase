package org.dnyanyog.enums_with_values;

public enum ErrorCode {
  LOGIN_SUCCESS("Success", "Login successful"),
  LOGIN_FAIL("Fail", "Login failed"),
  USER_FOUND("Success", "User added successfuly"),
  USER_SEARCH("Success", "User found successfully"),
  USER_NOT_FOUND("Fail", "User not found");

  private final String status;
  private final String message;

  ErrorCode(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
