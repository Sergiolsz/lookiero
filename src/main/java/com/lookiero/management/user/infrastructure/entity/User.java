package com.lookiero.management.user.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class User {

  @Id
  @Column(name = "USERNAME", nullable = false)
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Column(name = "BIRTH_DAY", nullable = false)
  private String birthdate;

  @Column(name = "HEIGHT", nullable = false)
  private String height;

  @Column(name = "WEIGHT", nullable = false)
  private Integer weight;
}
