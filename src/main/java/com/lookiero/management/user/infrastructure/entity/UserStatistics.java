package com.lookiero.management.user.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_STATISTICS")
public class UserStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID",unique=true, nullable = false)
  private Integer id;

  @Column(name = "USERNAME", nullable = false)
  private String username;

  @Column(name = "AGE", nullable = false)
  private Integer age;

  @Column(name = "BMI", nullable = false)
  private String bmi;

  @Column(name = "CATEGORY_BMI", nullable = false)
  private String categoryBMI;

}
