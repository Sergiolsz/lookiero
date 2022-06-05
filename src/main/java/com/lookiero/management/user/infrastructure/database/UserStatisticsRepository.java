package com.lookiero.management.user.infrastructure.database;

import com.lookiero.management.user.infrastructure.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {}
