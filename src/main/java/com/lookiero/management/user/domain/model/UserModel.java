package com.lookiero.management.user.domain.model;

import lombok.Data;

@Data
public class UserModel {

  private String username;
  private String password;
  private String birthdate;
  private String height;
  private Integer weight;
}
