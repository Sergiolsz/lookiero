package com.lookiero.management.user.domain.service;

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
import static org.mockito.Mockito.doReturn;

import com.lookiero.management.user.domain.model.ResponseLookieroModel;
import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.domain.service.interfaces.ProcessCreateUser;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetStatistics;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUserByUserName;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUsers;
import com.lookiero.management.user.domain.service.interfaces.ProcessPutUserHeightWeight;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LookieroServiceTest {

  @Autowired private LookieroService lookieroService;

  @MockBean private ProcessGetUserByUserName processGetUserByUserName;
  @MockBean private ProcessGetUsers processGetUsers;
  @MockBean private ProcessCreateUser processCreateUser;
  @MockBean private ProcessPutUserHeightWeight processUpdateUserHeightWeight;
  @MockBean private ProcessGetStatistics processGetStatistics;

  private UserModel userModel;
  private UserModel userModelUpdate;
  private UserStatisticsModel userStatisticsModel;
  private UserHeightWeightModel userHeightWeightModel;

  @BeforeEach
  public void before() throws Exception {
    userModel = testUserModel();
    userStatisticsModel = testUserStatisticsModel();
    userHeightWeightModel = testUserHeightWeightModel();
    userModelUpdate = testUserModelUpdate(userHeightWeightModel);
  }

  @Test
  @DisplayName("Service getUserByUserName test")
  void serviceGetUserByUserName() {
    doReturn(userModel).when(processGetUserByUserName).getUserByUserName(Mockito.any());

    var response = lookieroService.serviceGetUserByUserName(USERNAME_TEST);

    assertEquals(USERNAME_TEST, response.getUser().getUsername());
    assertEquals(PASSWORD_TEST, response.getUser().getPassword());
    assertEquals(BIRTHDATE_TEST, response.getUser().getBirthdate());
    assertEquals(HEIGHT_180_TEST, response.getUser().getHeight());
    assertEquals(WEIGHT_78_TEST, response.getUser().getWeight());
  }

  @Test
  @DisplayName("Service getUsers test")
  void serviceGetUsers() {
    doReturn(Collections.singletonList(userModel)).when(processGetUsers).getUsers();

    var response = lookieroService.serviceGetUsers();

    assertEquals(ONE, response.size());
    assertEquals(USERNAME_TEST, response.get(0).getUsername());
    assertEquals(PASSWORD_TEST, response.get(0).getPassword());
    assertEquals(BIRTHDATE_TEST, response.get(0).getBirthdate());
    assertEquals(HEIGHT_180_TEST, response.get(0).getHeight());
    assertEquals(WEIGHT_78_TEST, response.get(0).getWeight());
  }

  @Test
  @DisplayName("Service createUser test")
  void serviceCreateUser() {
    doReturn(userModel).when(processCreateUser).createUser(Mockito.any(), Mockito.any());

    var response = lookieroService.serviceCreateUser(userModel);

    assertEquals(USERNAME_TEST, response.getUser().getUsername());
    assertEquals(PASSWORD_TEST, response.getUser().getPassword());
    assertEquals(BIRTHDATE_TEST, response.getUser().getBirthdate());
    assertEquals(HEIGHT_180_TEST, response.getUser().getHeight());
    assertEquals(WEIGHT_78_TEST, response.getUser().getWeight());
  }

  @Test
  @DisplayName("Service putUserHeightWeight test")
  void servicePutUserHeightWeight() {
    doReturn(userModelUpdate)
        .when(processUpdateUserHeightWeight)
        .putUserHeightWeight(Mockito.any());

    var response = lookieroService.servicePutUserHeightWeight(userHeightWeightModel);

    assertEquals(USERNAME_TEST, response.getUser().getUsername());
    assertEquals(PASSWORD_TEST, response.getUser().getPassword());
    assertEquals(BIRTHDATE_TEST, response.getUser().getBirthdate());
    assertEquals(HEIGHT_165_TEST, response.getUser().getHeight());
    assertEquals(WEIGHT_70_TEST, response.getUser().getWeight());
  }

  @Test
  @DisplayName("Service getStatistics test")
  void serviceGetStatistics() {
    doReturn(Collections.singletonList(userStatisticsModel))
        .when(processGetStatistics)
        .getStatistics();

    var response = lookieroService.serviceGetStatistics();

    assertEquals(ONE, response.size());
    assertEquals(USERNAME_TEST, response.get(0).getUsername());
    assertEquals(AGE_TEST, response.get(0).getAge());
    assertEquals(BMI_TEST, response.get(0).getBmi());
    assertEquals(BMI_NORMAL, response.get(0).getCategoryBMI());
  }

  /*
   Model management
  */

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

  private UserModel testUserModelUpdate(UserHeightWeightModel userHeightWeightModel) {
    UserModel userModel = new UserModel();
    userModel.setUsername(USERNAME_TEST);
    userModel.setPassword(PASSWORD_TEST);
    userModel.setBirthdate(BIRTHDATE_TEST);
    userModel.setHeight(userHeightWeightModel.getHeight());
    userModel.setWeight(userHeightWeightModel.getWeight());

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
