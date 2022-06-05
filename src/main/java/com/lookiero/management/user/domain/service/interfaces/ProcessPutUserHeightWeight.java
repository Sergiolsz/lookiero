package com.lookiero.management.user.domain.service.interfaces;

import com.lookiero.management.user.domain.model.UserHeightWeightModel;
import com.lookiero.management.user.domain.model.UserModel;

@FunctionalInterface
public interface ProcessPutUserHeightWeight {

  UserModel putUserHeightWeight(UserHeightWeightModel userHeightWeightModel);
}
