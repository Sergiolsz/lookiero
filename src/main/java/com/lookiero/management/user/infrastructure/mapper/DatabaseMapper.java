package com.lookiero.management.user.infrastructure.mapper;

import com.lookiero.management.user.domain.model.UserModel;
import com.lookiero.management.user.domain.model.UserStatisticsModel;
import com.lookiero.management.user.infrastructure.entity.User;
import com.lookiero.management.user.infrastructure.entity.UserStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DatabaseMapper {

  DatabaseMapper databaseMapper = Mappers.getMapper(DatabaseMapper.class);

  User userModelToEntity(UserModel model);

  UserModel entityToUserModel(User user);

  @Mapping(target = "id", ignore = true)
  UserStatistics userStatisticsModelToEntity(UserStatisticsModel userStatisticsModel);

  UserStatisticsModel entityToUserStatisticsModel(UserStatistics userStatistics);
}
