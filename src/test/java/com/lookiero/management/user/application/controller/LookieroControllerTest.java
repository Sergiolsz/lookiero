package com.lookiero.management.user.application.controller;

import static com.lookiero.management.user.domain.utils.ConstantsService.AGE_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.BIRTHDATE_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_NORMAL;
import static com.lookiero.management.user.domain.utils.ConstantsService.BMI_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.HEIGHT_165_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.HEIGHT_180_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.ONE;
import static com.lookiero.management.user.domain.utils.ConstantsService.PASSWORD_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.USERNAME_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.USER_CREATED_OK;
import static com.lookiero.management.user.domain.utils.ConstantsService.USER_OBTAINED_OK;
import static com.lookiero.management.user.domain.utils.ConstantsService.USER_UPDATED_OK;
import static com.lookiero.management.user.domain.utils.ConstantsService.WEIGHT_70_TEST;
import static com.lookiero.management.user.domain.utils.ConstantsService.WEIGHT_78_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.lookiero.management.user.domain.model.ResponseLookieroModel;
import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.domain.service.LookieroService;
import com.management.users.api.lookiero.dto.UpdateUser;
import com.management.users.api.lookiero.dto.UserLookiero;
import java.util.Collections;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureJsonTesters
@WebMvcTest(LookieroController.class)
class LookieroControllerTest {

  @Autowired private LookieroController controller;
  @MockBean private LookieroService service;

  private UserLookiero userLookiero;
  private UpdateUser updateUser;
  private UserModel userModel;
  private UserStatisticsModel userStatisticsModel;
  private UserHeightWeightModel userHeightWeightModel;

  @BeforeEach
  public void before() throws Exception {
    userModel = testUserModel();
    userLookiero = testUserLookiero();
    updateUser = testUpdateUser();
    userStatisticsModel = testUserStatisticsModel();
    userHeightWeightModel = testUserHeightWeightModel(updateUser);
  }

  @Test
  @DisplayName("Endpoint getUserById test")
  void getUserById() {
    doReturn(testResponseLookieroModel(String.format(USER_OBTAINED_OK, USERNAME_TEST), userModel))
        .when(service)
        .serviceGetUserByUserName(USERNAME_TEST);

    var responseEntity = controller.getUserById(USERNAME_TEST);

    assertEquals(
        String.format(USER_OBTAINED_OK, USERNAME_TEST),
        Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(USERNAME_TEST, responseEntity.getBody().getUser().getUsername());
  }

  @Test
  @DisplayName("Endpoint getUsers test")
  void getUsers() {
    doReturn(Collections.singletonList(userModel)).when(service).serviceGetUsers();

    var responseEntity = controller.getUsers();

    assertEquals(ONE, Objects.requireNonNull(responseEntity.getBody()).size());
    assertEquals(USERNAME_TEST, responseEntity.getBody().get(0).getUsername());
  }

  @Test
  @DisplayName("Endpoint createUser test")
  void postCreateUser() {
    doReturn(testResponseLookieroModel(USER_CREATED_OK, userModel))
        .when(service)
        .serviceCreateUser(userModel);

    var responseEntity = controller.postCreateUser(userLookiero);

    assertEquals(USER_CREATED_OK, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(USERNAME_TEST, responseEntity.getBody().getUser().getUsername());
    assertEquals(PASSWORD_TEST, responseEntity.getBody().getUser().getPassword());
    assertEquals(BIRTHDATE_TEST, responseEntity.getBody().getUser().getBirthdate());
    assertEquals(HEIGHT_180_TEST, responseEntity.getBody().getUser().getHeight());
    assertEquals(WEIGHT_78_TEST, responseEntity.getBody().getUser().getWeight());
  }

  @Test
  @DisplayName("Endpoint updateHeightOrWeight test")
  void updateHeightOrWeight() {
    doReturn(testResponseLookieroModel(USER_UPDATED_OK, testUpdateUserModel(userHeightWeightModel)))
        .when(service)
        .servicePutUserHeightWeight(userHeightWeightModel);

    var responseEntity = controller.updateHeightOrWeight(updateUser);

    assertEquals(USER_UPDATED_OK, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    assertEquals(USERNAME_TEST, responseEntity.getBody().getUser().getUsername());
    assertEquals(PASSWORD_TEST, responseEntity.getBody().getUser().getPassword());
    assertEquals(BIRTHDATE_TEST, responseEntity.getBody().getUser().getBirthdate());
    assertEquals(HEIGHT_165_TEST, responseEntity.getBody().getUser().getHeight());
    assertEquals(WEIGHT_70_TEST, responseEntity.getBody().getUser().getWeight());
  }

  @Test
  @DisplayName("Endpoint getStatisticsBMI test")
  void getStatisticsBMI() {
    doReturn(Collections.singletonList(userStatisticsModel)).when(service).serviceGetStatistics();

    var responseEntity = controller.getStatisticsBMI();

    assertEquals(ONE, Objects.requireNonNull(responseEntity.getBody()).size());
    assertEquals(USERNAME_TEST, responseEntity.getBody().get(0).getUsername());
    assertEquals(AGE_TEST, responseEntity.getBody().get(0).getAge());
    assertEquals(BMI_TEST, responseEntity.getBody().get(0).getBmi());
    assertEquals(BMI_NORMAL, responseEntity.getBody().get(0).getCategoryBMI());
  }

  /*
   Model management
  */

  private ResponseLookieroModel testResponseLookieroModel(String message, UserModel userModel) {
    return ResponseLookieroModel.builder().message(message).user(userModel).build();
  }

  private UserLookiero testUserLookiero() {
    UserLookiero userLookiero = new UserLookiero();
    userLookiero.username(USERNAME_TEST);
    userLookiero.password(PASSWORD_TEST);
    userLookiero.birthdate(BIRTHDATE_TEST);
    userLookiero.height(HEIGHT_180_TEST);
    userLookiero.weight(WEIGHT_78_TEST);

    return userLookiero;
  }

  private UpdateUser testUpdateUser() {
    UpdateUser updateUser = new UpdateUser();
    updateUser.setUsername(USERNAME_TEST);
    updateUser.height(HEIGHT_165_TEST);
    updateUser.weight(WEIGHT_70_TEST);

    return updateUser;
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

  private UserModel testUpdateUserModel(UserHeightWeightModel userHeightWeightModel) {
    UserModel userModel = new UserModel();
    userModel.setUsername(userHeightWeightModel.getUsername());
    userModel.setPassword(PASSWORD_TEST);
    userModel.setBirthdate(BIRTHDATE_TEST);
    userModel.setHeight(userHeightWeightModel.getHeight());
    userModel.setWeight(userHeightWeightModel.getWeight());

    return userModel;
  }

  private UserHeightWeightModel testUserHeightWeightModel(UpdateUser updateUser) {
    UserHeightWeightModel userHeightWeightModel = new UserHeightWeightModel();
    userHeightWeightModel.setUsername(updateUser.getUsername());
    userHeightWeightModel.setHeight(updateUser.getHeight());
    userHeightWeightModel.setWeight(updateUser.getWeight());

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
