package com.lookiero.management.user.infrastructure.configuration;

import com.lookiero.management.user.domain.service.interfaces.ProcessCreateUser;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetStatistics;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUserByUserName;
import com.lookiero.management.user.domain.service.interfaces.ProcessGetUsers;
import com.lookiero.management.user.domain.service.interfaces.ProcessPutUserHeightWeight;
import com.lookiero.management.user.infrastructure.service.ProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

  @Bean
  public ProcessGetUserByUserName processGetUserByUserName(ProcessService processService) {
    return processService::getUserByUsername;
  }

  @Bean
  public ProcessGetUsers processGetUsers(ProcessService processService) {
    return processService::getUsers;
  }

  @Bean
  public ProcessCreateUser processCreateUser(ProcessService processService) {
    return processService::createUser;
  }

  @Bean
  public ProcessPutUserHeightWeight processPutUserHeightWeight(ProcessService processService) {
    return processService::updateUserHeightWeight;
  }

  @Bean
  public ProcessGetStatistics processGetStatistics(ProcessService processService) {
    return processService::getStatistics;
  }
}
