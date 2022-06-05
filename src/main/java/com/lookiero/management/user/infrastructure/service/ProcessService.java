package com.lookiero.management.user.infrastructure.service;

import static com.lookiero.management.user.domain.exceptions.ExceptionMessage.USER_NOT_FOUND;

import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.infrastructure.database.UserRepository;
import com.lookiero.management.user.infrastructure.database.UserStatisticsRepository;
import com.lookiero.management.user.infrastructure.entity.UserStatistics;
import com.lookiero.management.user.infrastructure.mapper.DatabaseMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

  private final UserRepository userRepository;
  private final UserStatisticsRepository userStatisticsRepository;

  public ProcessService(
      UserRepository lookieroRepository, UserStatisticsRepository userStatisticsRepository) {
    this.userRepository = lookieroRepository;
    this.userStatisticsRepository = userStatisticsRepository;
  }

  /**
   * Service to retrieve a User by ID. The ID is the Username field.
   *
   * @param username The user ID
   * @return The user searched by his ID
   */
  public UserModel getUserByUsername(String username) {
    return DatabaseMapper.databaseMapper.entityToUserModel(
        userRepository
            .findById(username)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), username))));
  }

  /**
   * Service to retrieve the complete list of Users.
   *
   * @return List of users
   */
  public List<UserModel> getUsers() {
    return userRepository.findAll().stream()
        .map(DatabaseMapper.databaseMapper::entityToUserModel)
        .collect(Collectors.toList());
  }

  /**
   * Service to create and persist User and User Statistics objects
   *
   * @param userModel User data
   * @param userStatisticsModel User Statistics data
   * @return Created the User and User Statistics objects. Return the User object
   */
  public UserModel createUser(UserModel userModel, UserStatisticsModel userStatisticsModel) {
    var userModelToEntity = DatabaseMapper.databaseMapper.userModelToEntity(userModel);
    userRepository.save(userModelToEntity);

    UserStatistics userStatistics =
        DatabaseMapper.databaseMapper.userStatisticsModelToEntity(userStatisticsModel);
    userStatisticsRepository.save(userStatistics);

    return userModel;
  }

  /**
   * Service to update the user's height or weight data
   *
   * @param userHeightWeightModel User data for update Height or Weight
   * @return User data
   */
  public UserModel updateUserHeightWeight(UserHeightWeightModel userHeightWeightModel) {
    var userUpdate =
        userRepository
            .findById(userHeightWeightModel.getUsername())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            USER_NOT_FOUND.getMessage(), userHeightWeightModel.getUsername())));

    userUpdate.setHeight(userHeightWeightModel.getHeight());
    userUpdate.setWeight(userHeightWeightModel.getWeight());
    userRepository.save(userUpdate);

    return DatabaseMapper.databaseMapper.entityToUserModel(userUpdate);
  }

  /**
   * Service to retrieve users' BMI statistical data
   *
   * @return List of user statistics
   */
  public List<UserStatisticsModel> getStatistics() {
    return userStatisticsRepository.findAll().stream()
        .map(DatabaseMapper.databaseMapper::entityToUserStatisticsModel)
        .collect(Collectors.toList());
  }
}
