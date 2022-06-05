package com.lookiero.management.user.domain.service.interfaces;

import com.lookiero.management.user.domain.model.UserModel;

@FunctionalInterface
public interface ProcessGetUserByUserName {

  UserModel getUserByUserName(String userName);
}
