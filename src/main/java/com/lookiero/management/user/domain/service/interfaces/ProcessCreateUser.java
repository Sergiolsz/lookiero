package com.lookiero.management.user.domain.service.interfaces;

import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;

@FunctionalInterface
public interface ProcessCreateUser {

  UserModel createUser(UserModel userModel, UserStatisticsModel userStatisticsModel);
}
