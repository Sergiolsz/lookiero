package com.lookiero.management.user.domain.exceptions;

/** Definition of all the messages errors */
public enum ExceptionMessage {

  /** User not found error message */
  USER_NOT_FOUND("Not found User with username %s"),

  /** Empty username field error message. */
  USERNAME_EMPTY_ERROR("The username field is empty."),

  /** Username field length error message. */
  LENGTH_USERNAME_ERROR("The length of the username data: %s is not greater than 7 characters"),

  /** Illegal character error message. */
  CHARACTER_ALLOWED_ERROR("The username field does not have a character allowed: %c"),

  /** Username field blank character error message. */
  USERNAME_BLANK_SPACE_ERROR("The username field has a blank space."),

  /** Empty password field error message. */
  PASSWORD_EMPTY_ERROR("The password field is empty."),

  /** Password field blank character error message. */
  PASSWORD_BLANK_SPACE_ERROR("The password field has a blank space."),

  /** Empty birth day field error message. */
  BIRTH_DAY_EMPTY_ERROR("The birth day field is empty."),

  /** Birth day field error message. */
  BIRTH_DAY_ERROR("The birth day date: %s, does not meet the condition of equal to or greater than 18 years"),

  /** Empty height field error message. */
  HEIGHT_EMPTY_ERROR("The height field is empty."),

  /** Height field error message. */
  HEIGHT_ERROR("The height %s is wrong"),

  /** Empty wheight field error message. */
  WHEIGHT_EMPTY_ERROR("The wheight field is empty."),

  /** Weight field error message. */
  WEIGHT_ERROR("The weight does not meet the condition of the value between 40 and 150 kilograms"),

  /** Bad request error. */
  BAD_REQUEST("Bad Request");

  private final String message;

  /**
   * Instantiates a new exception message.
   *
   * @param message the message
   */
  private ExceptionMessage(final String message) {
    this.message = message;
  }

  /** @return The message */
  public String getMessage() {
    return message;
  }
}
