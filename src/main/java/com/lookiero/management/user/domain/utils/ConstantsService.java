package com.lookiero.management.user.domain.utils;

import com.lookiero.management.user.domain.exceptions.ExceptionMessage;
import lombok.Getter;

@Getter
public class ConstantsService {

  // GENERAL CONSTANTS
  public static final String DOT = ".";
  public static final String COMMA = ",";
  public static final String BLANK = "";

  // NUMBERS CONSTANTS
  public static final Integer ONE = 1;
  public static final Integer SEVEN = 7;
  public static final Integer EIGHTEEN = 18;
  public static final Integer FORTY = 40;
  public static final Integer ONE_HUNDRED_FIFTY = 150;
  public static final Double MIN_HEIGHT = 1.30;
  public static final Double MAX_HEIGHT = 2.30;

  // MESSAGE CONSTANTS
  public static final String USER_FOUND_OK = "User found successfully";
  public static final String USER_OBTAINED_OK = "User obtained %s successfully";
  public static final String USER_CREATED_OK = "User created successfully";
  public static final String USER_UPDATED_OK = "User updated successfully";

  // PATTERNS CONSTANTS
  public static final String PATTERN_DATE = "dd-MM-yyyy";
  public static final String PATTERN_DECIMALS = "%.2f";

  // BMI CONSTANTS
  public static final String BMI_UNDER_WEIGHT = "Underweight";
  public static final String BMI_NORMAL = "Normal";
  public static final String BMI_OVER_WEIGHT = "Overweight";
  public static final String BMI_OBESE = "Obese";

  // TEST CONSTANTS
  public static final String USERNAME_TEST = "userLookiero";
  public static final String PASSWORD_TEST = "password";
  public static final String BIRTHDATE_TEST = "10-05-1990";
  public static final Integer AGE_TEST = 30;
  public static final String HEIGHT_180_TEST = "1,80";
  public static final String HEIGHT_165_TEST = "1,65";
  public static final Integer WEIGHT_78_TEST = 78;
  public static final Integer WEIGHT_70_TEST = 70;
  public static final String BMI_TEST = "20";
}
