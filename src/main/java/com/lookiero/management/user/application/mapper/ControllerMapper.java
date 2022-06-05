package com.lookiero.management.user.application.mapper;

import com.lookiero.management.user.domain.model.ResponseLookieroModel;
import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.management.users.api.lookiero.dto.ResponseLookiero;
import com.management.users.api.lookiero.dto.UpdateUser;
import com.management.users.api.lookiero.dto.UserLookiero;
import com.management.users.api.lookiero.dto.UserStatistics;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ControllerMapper {

  ControllerMapper mapper = Mappers.getMapper(ControllerMapper.class);

  ResponseLookiero modelToRS(ResponseLookieroModel responseLookieroModel);

  UserLookiero userModelToRS(UserModel userModel);

  UserModel rsToUserModel(UserLookiero userLookiero);

  List<UserLookiero> listUserModelToRS(List<UserModel> usersModel);

  List<UserModel> rsToListUserModel(List<UserLookiero> usersLookiero);

  UpdateUser userHeightWeightModelToRS(UserHeightWeightModel userHeightWeightModel);

  UserHeightWeightModel rsToUserHeightWeightModel(UpdateUser updateUser);

  UserStatistics statisticsModelToRS(UserStatisticsModel userStatisticsModel);

  UserStatisticsModel rsToStatisticsModel(UserStatistics userStatistics);

  List<UserStatistics> listStatisticsModelToEntity(List<UserStatisticsModel> statistics);

  List<UserStatisticsModel> entityToListStatisticsModel(List<UserStatistics> statistics);
}
