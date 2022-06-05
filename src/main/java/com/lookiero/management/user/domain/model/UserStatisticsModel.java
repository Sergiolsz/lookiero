package com.lookiero.management.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserStatisticsModel {

  String username;
  Integer age;
  String bmi;
  String categoryBMI;
}
