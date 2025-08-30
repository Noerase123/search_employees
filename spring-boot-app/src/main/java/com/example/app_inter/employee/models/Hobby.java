package com.example.app_inter.employee.models;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "hobbies", indexes = {
  @Index(name = "idx_hobby_name_lower", columnList = "name")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hobby {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 80)
  private String name;
}
