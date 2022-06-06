package com.lookiero.management.user.domain.service;

import static com.lookiero.management.user.domain.utils.ConstantsService.USER_CREATED_OK;
import static com.lookiero.management.user.domain.utils.ConstantsService.USER_FOUND_OK;
import static com.lookiero.management.user.domain.utils.ConstantsService.USER_UPDATED_OK;
import static com.lookiero.management.user.domain.utils.UtilsService.formatterBirthDate;

import com.lookiero.management.user.domain.model.ResponseLookieroModel;
import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.domain.service.interfaces.ProcessCreateUser;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetStatistics;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUserByUserName;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUsers;
import com.lookiero.management.user.domain.service.interfaces.ProcessPutUserHeightWeight;
import com.lookiero.management.user.domain.utils.UtilsService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class LookieroService {

  private final ProcessGetUserByUserName processGetUserByUserName;
  private final ProcessGetUsers processGetUsers;
  private final ProcessCreateUser processCreateUser;
  private final ProcessPutUserHeightWeight processUpdateUserHeightWeight;
  private final ProcessGetStatistics processGetStatistics;

  public LookieroService(
      ProcessGetUserByUserName processGetUserByUserName,
      ProcessGetUsers processGetUsers,
      ProcessCreateUser processCreateUser,
      ProcessPutUserHeightWeight processUpdateUserHeightWeight,
      ProcessGetStatistics processGetStatistics) {
    this.processGetUserByUserName = processGetUserByUserName;
    this.processGetUsers = processGetUsers;
    this.processCreateUser = processCreateUser;
    this.processUpdateUserHeightWeight = processUpdateUserHeightWeight;
    this.processGetStatistics = processGetStatistics;
  }

  /**
   * The service will map the entity to the model the User searched for by its id.
   *
   * @param username The User ID.
   * @return Response with the User object.
   */
  public ResponseLookieroModel serviceGetUserByUserName(String username) {
    var user = processGetUserByUserName.getUserByUserName(username);
    return ResponseLookieroModel.builder().message(USER_FOUND_OK).user(user).build();
  }

  /**
   * The service will map the list of users to the retrieved model to order by the date of birth
   * field.
   *
   * @return Response with the list of users sorted by date of birth.
   */
  public List<UserModel> serviceGetUsers() {
    return processGetUsers.getUsers().stream()
        .sorted(Comparator.comparing(user -> formatterBirthDate(user.getBirthdate())))
        .collect(Collectors.toList());
  }

  /**
   * The service will create a record in the User table with the model data.
   *
   * @param userModel User data model to be created in the database.
   * @return Response with the User object created in the database.
   */
  public ResponseLookieroModel serviceCreateUser(@NotNull UserModel userModel) {

    // Verification of the conditions to create a new User

    // Verification Username
    UtilsService.checkUsername(userModel.getUsername());

    // Verification Password
    UtilsService.checkPasswordBlankSpace(userModel.getPassword());

    // Verification Birthdate
    UtilsService.checkBirthdate(userModel);

    // Verification Heigth
    UtilsService.checkHeigth(userModel.getHeight());

    // Verification Weigth
    UtilsService.checkWeight(userModel.getWeight());

    var userStatistics = UtilsService.calculationBMI(userModel);

    var createUser = processCreateUser.createUser(userModel, userStatistics);

    return ResponseLookieroModel.builder().message(USER_CREATED_OK).user(createUser).build();
  }

  /**
   * The service will assign the list of statistics of each user to the retrieved model.
   *
   * @return Response with the list of user statistics sorted by BMI.
   */
  public ResponseLookieroModel servicePutUserHeightWeight(
      @NotNull UserHeightWeightModel userHeightWeightModel) {

    // Verification Heigth
    UtilsService.checkHeigth(userHeightWeightModel.getHeight());

    // Verification Weigth
    UtilsService.checkWeight(userHeightWeightModel.getWeight());

    var userModel = processUpdateUserHeightWeight.putUserHeightWeight(userHeightWeightModel);

    return ResponseLookieroModel.builder().message(USER_UPDATED_OK).user(userModel).build();
  }

  /**
   * The service will assign the list of statistics of each user to the retrieved model.
   *
   * @return Response with the list of user statistics sorted by BMI.
   */
  public List<UserStatisticsModel> serviceGetStatistics() {
    return processGetStatistics.getStatistics().stream()
        .sorted(Comparator.comparing(UserStatisticsModel::getBmi))
        .collect(Collectors.toList());
  }
}
