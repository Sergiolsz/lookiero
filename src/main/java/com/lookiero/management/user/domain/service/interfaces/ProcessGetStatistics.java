package com.lookiero.management.user.domain.service.interfaces;

import com.lookiero.management.user.domain.model.UserStatisticsModel;
import java.util.List;

@FunctionalInterface
public interface ProcessGetStatistics {

  List<UserStatisticsModel> getStatistics();
}
