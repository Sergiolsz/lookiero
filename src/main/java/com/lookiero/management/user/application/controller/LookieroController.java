package com.lookiero.management.user.application.controller;

import com.lookiero.management.user.application.mapper.ControllerMapper;
import com.lookiero.management.user.domain.service.LookieroService;
import com.management.users.api.lookiero.LookieroApi;
import com.management.users.api.lookiero.dto.ResponseLookiero;
import com.management.users.api.lookiero.dto.UpdateUser;
import com.management.users.api.lookiero.dto.UserLookiero;
import com.management.users.api.lookiero.dto.UserStatistics;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LookieroController implements LookieroApi {

  private final LookieroService service;

  public LookieroController(LookieroService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<ResponseLookiero> getUserById(String username) {
    var user = ControllerMapper.mapper.modelToRS(service.serviceGetUserByUserName(username));

    return ResponseEntity.ok(user);
  }

  @Override
  public ResponseEntity<List<UserLookiero>> getUsers() {
    var users = ControllerMapper.mapper.listUserModelToRS(service.serviceGetUsers());

    return ResponseEntity.ok(users);
  }

  @Override
  public ResponseEntity<ResponseLookiero> postCreateUser(UserLookiero userLookiero) {
    var userModel = ControllerMapper.mapper.rsToUserModel(userLookiero);
    var responseCreate = ControllerMapper.mapper.modelToRS(service.serviceCreateUser(userModel));

    return ResponseEntity.ok(responseCreate);
  }

  @Override
  public ResponseEntity<ResponseLookiero> updateHeightOrWeight(UpdateUser updateUser) {
    var userHeightWeightModel = ControllerMapper.mapper.rsToUserHeightWeightModel(updateUser);
    var responseUpdate =
        ControllerMapper.mapper.modelToRS(
            service.servicePutUserHeightWeight(userHeightWeightModel));

    return ResponseEntity.ok(responseUpdate);
  }

  @Override
  public ResponseEntity<List<UserStatistics>> getStatisticsBMI() {
    var userStatistics =
        ControllerMapper.mapper.listStatisticsModelToEntity(service.serviceGetStatistics());

    return ResponseEntity.ok(userStatistics);
  }
}
