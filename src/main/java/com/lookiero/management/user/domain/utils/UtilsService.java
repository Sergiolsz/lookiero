package com.lookiero.management.user.domain.utils;

import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.BIRTH_DAY_EMPTY_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.BIRTH_DAY_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.CHARACTER_ALLOWED_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.HEIGHT_EMPTY_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.HEIGHT_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.LENGTH_USERNAME_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.PASSWORD_BLANK_SPACE_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.PASSWORD_EMPTY_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.USERNAME_BLANK_SPACE_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.USERNAME_EMPTY_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.WEIGHT_ERROR;
import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.WHEIGHT_EMPTY_ERROR;
import static com.lookiero.management.user.domain.utils.ConstantsService.BLANK;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_NORMAL;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_OBESE;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_OVER_WEIGHT;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_UNDER_WEIGHT;
import static com.lookiero.management.user.domain.utils.ConstantsService.COMMA;
import static com.lookiero.management.user.domain.utils.ConstantsService.DOT;
import static com.lookiero.management.user.domain.utils.ConstantsService.EIGHTEEN;
import static com.lookiero.management.user.domain.utils.ConstantsService.FORTY;
import static com.lookiero.management.user.domain.utils.ConstantsService.MAX_HEIGHT;
import static com.lookiero.management.user.domain.utils.ConstantsService.MIN_HEIGHT;
import static com.lookiero.management.user.domain.utils.ConstantsService.ONE;
import static com.lookiero.management.user.domain.utils.ConstantsService.ONE_HUNDRED_FIFTY;
import static com.lookiero.management.user.domain.utils.ConstantsService.PATTERN_DATE;
import static com.lookiero.management.user.domain.utils.ConstantsService.PATTERN_DECIMALS;
import static com.lookiero.management.user.domain.utils.ConstantsService.SEVEN;

import com.lookiero.management.user.domain.exceptions.HandlerExceptions;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ValueRange;
import java.util.Date;
import java.util.Optional;

public class UtilsService {

  /**
   * Procedure to check the conditions of the username. Conditions: Size of at least 8 characters.
   * Alphanumeric. No blank and unique characters.
   *
   * @param username User name and id data
   */
  public static void checkUsername(String username) {

    var checkUsername =
        Optional.ofNullable(username)
            .orElseThrow(() -> new HandlerExceptions(USERNAME_EMPTY_ERROR.getMessage()));

    final var checkUsernameLength = checkUsername.length() > SEVEN;

    if (!checkUsernameLength) {
      throw new HandlerExceptions(String.format(LENGTH_USERNAME_ERROR.getMessage(), checkUsername));
    }

    for (int i = 0; i < checkUsername.length(); i++) {
      if (!Character.isLetterOrDigit(checkUsername.charAt(i))) {
        throw new HandlerExceptions(
            String.format(CHARACTER_ALLOWED_ERROR.getMessage(), checkUsername.charAt(i)));
      }

      if (Character.isSpaceChar(checkUsername.charAt(i))) {
        throw new HandlerExceptions(USERNAME_BLANK_SPACE_ERROR.getMessage());
      }
    }
  }

  /**
   * Procedure to check that the password is not empty or has a blank field
   *
   * @param password User password data
   */
  public static void checkPasswordBlankSpace(String password) {

    var checkPassword =
        Optional.ofNullable(password)
            .orElseThrow(() -> new HandlerExceptions(PASSWORD_EMPTY_ERROR.getMessage()));

    for (int i = 0; i < checkPassword.length(); i++) {
      if (Character.isSpaceChar(checkPassword.charAt(i))) {
        throw new HandlerExceptions(PASSWORD_BLANK_SPACE_ERROR.getMessage());
      }
    }
  }

  /**
   * Procedure to check the conditions of the user's age by his birthday. Condition: Equal or
   * greater than 18 years. Format birth date: dd-MM-yyyy
   *
   * @param userModel User data
   */
  public static void checkBirthdate(UserModel userModel) {

    var checkBirthdate =
        Optional.ofNullable(userModel.getBirthdate())
            .orElseThrow(() -> new HandlerExceptions(BIRTH_DAY_EMPTY_ERROR.getMessage()));

    var age = calculateAgeOfUser(checkBirthdate);

    if (age < EIGHTEEN) {
      throw new HandlerExceptions(String.format(BIRTH_DAY_ERROR.getMessage(), checkBirthdate));
    }
  }

  /**
   * Procedure to check the conditions of the user's height. Condition: Between 1.00 and 2.30
   * meters.
   *
   * @param height User height data
   */
  public static void checkHeigth(String height) {

    var checkHeight =
        Optional.ofNullable(height)
            .orElseThrow(() -> new HandlerExceptions(HEIGHT_EMPTY_ERROR.getMessage()));

    var parseHeight = Double.parseDouble(checkHeight.replace(COMMA, DOT));

    var underHeight = Double.compare(MIN_HEIGHT, parseHeight) == ONE;
    var overHeight = Double.compare(parseHeight, MAX_HEIGHT) == ONE;

    if (underHeight || overHeight) {
      throw new HandlerExceptions(String.format(HEIGHT_ERROR.getMessage(), height));
    }
  }

  /**
   * Procedure to check the weight of the User under a condition of a weight range of 40 to 150
   * kilograms
   *
   * @param weight User weight data
   */
  public static void checkWeight(Integer weight) {
    var checkWeight =
        Optional.ofNullable(weight)
            .orElseThrow(() -> new HandlerExceptions(WHEIGHT_EMPTY_ERROR.getMessage()));

    var checkNumber = ValueRange.of(FORTY, ONE_HUNDRED_FIFTY).isValidIntValue(checkWeight);

    if (!checkNumber) {
      throw new HandlerExceptions(WEIGHT_ERROR.getMessage());
    }
  }

  /**
   * Method for calculating BMI
   *
   * @param userModel User data
   */
  public static UserStatisticsModel calculationBMI(UserModel userModel) {

    var checkUsername =
        Optional.ofNullable(userModel.getUsername())
            .orElseThrow(() -> new HandlerExceptions(USERNAME_EMPTY_ERROR.getMessage()));

    var checkBirthdate =
        Optional.ofNullable(userModel.getBirthdate())
            .orElseThrow(() -> new HandlerExceptions(BIRTH_DAY_EMPTY_ERROR.getMessage()));

    var checkHeight =
        Optional.of(Double.parseDouble(userModel.getHeight().replace(COMMA, DOT)))
            .orElseThrow(() -> new HandlerExceptions(HEIGHT_EMPTY_ERROR.getMessage()));

    var checkWeight =
        Optional.ofNullable(userModel.getWeight())
            .orElseThrow(() -> new HandlerExceptions(WHEIGHT_EMPTY_ERROR.getMessage()));

    var imc = checkWeight / (checkHeight * checkHeight);

    var typeIMC = BLANK;
    if (imc < 18.5) {
      typeIMC = BMI_UNDER_WEIGHT;
    } else if (imc <= 25) {
      typeIMC = BMI_NORMAL;
    } else if (imc <= 30) {
      typeIMC = BMI_OVER_WEIGHT;
    } else {
      typeIMC = BMI_OBESE;
    }

    return UserStatisticsModel.builder()
        .username(checkUsername)
        .age(calculateAgeOfUser(checkBirthdate))
        .bmi(String.format(PATTERN_DECIMALS, imc))
        .categoryBMI(typeIMC)
        .build();
  }

  /**
   * Method to format the user's date of birth
   *
   * @param birthdate User birth date data
   * @return Formatting the user's date of birth
   */
  public static Date formatterBirthDate(String birthdate) {
    try {
      SimpleDateFormat formatDate = new SimpleDateFormat(PATTERN_DATE);
      return formatDate.parse(birthdate);
    } catch (ParseException e) {
      throw new HandlerExceptions(e.getMessage());
    }
  }

  /**
   * Method to calculate the age of the user from the date of birth until now
   *
   * @param birthday User birth day data
   * @return Age of User in type Integer
   */
  private static Integer calculateAgeOfUser(String birthday) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
    LocalDate formatterBirthDate = LocalDate.parse(birthday, formatter);

    return Period.between(formatterBirthDate, LocalDate.now()).getYears();
  }
}
