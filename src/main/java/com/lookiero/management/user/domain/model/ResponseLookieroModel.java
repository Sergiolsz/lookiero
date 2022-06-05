package com.lookiero.management.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseLookieroModel {

  String message;
  UserModel user;
}
