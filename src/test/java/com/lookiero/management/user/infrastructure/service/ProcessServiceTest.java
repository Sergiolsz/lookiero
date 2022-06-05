package com.lookiero.management.user.infrastructure.service;

import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.USER_NOT_FOUND;
import static com.lookiero.management.user.domain.utils.ConstantsService.AGE_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.BIRTHDATE_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_NORMAL;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.HEIGHT_165_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.HEIGHT_180_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.ONE;
import static com.lookiero.management.user.domain.utils.ConstantsService.PASSWORD_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.USERNAME_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.WEIGHT_70_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.WEIGHT_78_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.lookiero.management.user.domain.model.ResponseLookieroModel;
import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.infrastructure.database.UserRepository;
import com.lookiero.management.user.infrastructure.database.UserStatisticsRepository;
import com.lookiero.management.user.infrastructure.entity.User;
import com.lookiero.management.user.infrastructure.entity.UserStatistics;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProcessServiceTest {

  @Autowired private ProcessService service;

  @MockBean private UserRepository userRepository;
  @MockBean private UserStatisticsRepository userStatisticsRepository;

  private User user;
  private UserModel userModel;
  private UserStatistics userStatistics;
  private UserStatisticsModel userStatisticsModel;
  private UserHeightWeightModel userHeightWeightModel;

  @BeforeEach
  public void before() throws Exception {
    user = testUser();
    userModel = testUserModel();
    userStatistics = testUserStatistics();
    userStatisticsModel = testUserStatisticsModel();
    userHeightWeightModel = testUserHeightWeightModel();
  }

  @Test
  @DisplayName("Process getUserByUsername test")
  void getUserByUsername() {
    doReturn(Optional.of(user)).when(userRepository).findById(USERNAME_TEST);

    var entity = service.getUserByUsername(USERNAME_TEST);

    assertEquals(USERNAME_TEST, entity.getUsername());
    assertEquals(PASSWORD_TEST, entity.getPassword());
    assertEquals(BIRTHDATE_TEST, entity.getBirthdate());
    assertEquals(HEIGHT_180_TEST, entity.getHeight());
    assertEquals(WEIGHT_78_TEST, entity.getWeight());
  }

  @Test
  @DisplayName("Process getUserByUsername test exception")
  void getUserByUsername_Not_Found_User_Exception() {
    var exception =
        assertThrows(
            ResourceNotFoundException.class, () -> service.getUserByUsername(USERNAME_TEST));

    assertTrue(
        exception.getMessage().contains(String.format(USER_NOT_FOUND.getMessage(), USERNAME_TEST)));
  }

  @Test
  @DisplayName("Process getUsers test")
  void getUsers() {
    doReturn(Collections.singletonList(user)).when(userRepository).findAll();

    var entity = service.getUsers();

    assertEquals(ONE, entity.size());
    assertEquals(USERNAME_TEST, entity.get(0).getUsername());
    assertEquals(PASSWORD_TEST, entity.get(0).getPassword());
    assertEquals(BIRTHDATE_TEST, entity.get(0).getBirthdate());
    assertEquals(HEIGHT_180_TEST, entity.get(0).getHeight());
    assertEquals(WEIGHT_78_TEST, entity.get(0).getWeight());
  }

  @Test
  @DisplayName("Process createUser test")
  void createUser() {
    doReturn(user).when(userRepository).save(any());

    var entity = service.createUser(userModel, userStatisticsModel);

    assertEquals(USERNAME_TEST, entity.getUsername());
    assertEquals(PASSWORD_TEST, entity.getPassword());
    assertEquals(BIRTHDATE_TEST, entity.getBirthdate());
    assertEquals(HEIGHT_180_TEST, entity.getHeight());
    assertEquals(WEIGHT_78_TEST, entity.getWeight());
  }

  @Test
  @DisplayName("Process updateUserHeightWeight test")
  void updateUserHeightWeight() {
    doReturn(Optional.of(user)).when(userRepository).findById(USERNAME_TEST);
    doReturn(user).when(userRepository).save(any());

    var entity = service.updateUserHeightWeight(userHeightWeightModel);

    assertEquals(USERNAME_TEST, entity.getUsername());
    assertEquals(PASSWORD_TEST, entity.getPassword());
    assertEquals(BIRTHDATE_TEST, entity.getBirthdate());
    assertEquals(HEIGHT_165_TEST, entity.getHeight());
    assertEquals(WEIGHT_70_TEST, entity.getWeight());
  }

  @Test
  @DisplayName("Process updateUserHeightWeight test exception")
  void updateUserHeightWeight_Not_Found_User_Exception() {
    var exception =
        assertThrows(
            ResourceNotFoundException.class,
            () -> service.updateUserHeightWeight(userHeightWeightModel));

    assertTrue(
        exception.getMessage().contains(String.format(USER_NOT_FOUND.getMessage(), USERNAME_TEST)));
  }

  @Test
  @DisplayName("Process getStatistics test")
  void getStatistics() {
    doReturn(Collections.singletonList(userStatistics)).when(userStatisticsRepository).findAll();

    var entity = service.getStatistics();

    assertEquals(ONE, entity.size());
    assertEquals(USERNAME_TEST, entity.get(0).getUsername());
    assertEquals(AGE_TEST, entity.get(0).getAge());
    assertEquals(BMI_TEST, entity.get(0).getBmi());
    assertEquals(BMI_NORMAL, entity.get(0).getCategoryBMI());
  }

  /*
   Model management
  */

  private User testUser() {
    User user = new User();
    user.setUsername(USERNAME_TEST);
    user.setPassword(PASSWORD_TEST);
    user.setBirthdate(BIRTHDATE_TEST);
    user.setHeight(HEIGHT_180_TEST);
    user.setWeight(WEIGHT_78_TEST);

    return user;
  }

  private UserStatistics testUserStatistics() {
    UserStatistics userStatistics = new UserStatistics();
    userStatistics.setUsername(USERNAME_TEST);
    userStatistics.setAge(AGE_TEST);
    userStatistics.setBmi(BMI_TEST);
    userStatistics.setCategoryBMI(BMI_NORMAL);

    return userStatistics;
  }

  private ResponseLookieroModel testResponseLookieroModel(String message, UserModel userModel) {
    return ResponseLookieroModel.builder().message(message).user(userModel).build();
  }

  private UserModel testUserModel() {
    UserModel userModel = new UserModel();
    userModel.setUsername(USERNAME_TEST);
    userModel.setPassword(PASSWORD_TEST);
    userModel.setBirthdate(BIRTHDATE_TEST);
    userModel.setHeight(HEIGHT_180_TEST);
    userModel.setWeight(WEIGHT_78_TEST);

    return userModel;
  }

  private UserHeightWeightModel testUserHeightWeightModel() {
    UserHeightWeightModel userHeightWeightModel = new UserHeightWeightModel();
    userHeightWeightModel.setUsername(USERNAME_TEST);
    userHeightWeightModel.setHeight(HEIGHT_165_TEST);
    userHeightWeightModel.setWeight(WEIGHT_70_TEST);

    return userHeightWeightModel;
  }

  private UserStatisticsModel testUserStatisticsModel() {
    return UserStatisticsModel.builder()
        .username(USERNAME_TEST)
        .age(AGE_TEST)
        .bmi(BMI_TEST)
        .categoryBMI(BMI_NORMAL)
        .build();
  }
}
