package com.lookiero.management.user.domain.service.interfaces;

import com.lookiero.management.user.domain.model.UserModel;
import java.util.List;

@FunctionalInterface
public interface ProcessGetUsers {

  List<UserModel> getUsers();
}
