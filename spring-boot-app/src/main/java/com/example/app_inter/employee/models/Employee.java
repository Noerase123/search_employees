// src/main/java/com/example/employee/model/Employee.java
package com.example.app_inter.employee.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "employees", indexes = {
  @Index(name = "idx_employee_name_lower", columnList = "name"),
  @Index(name = "idx_employee_employee_id", columnList = "employeeId", unique = true),
  @Index(name = "idx_employee_project_lower", columnList = "project")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Business ID: "E00123" etc. */
  @Column(nullable = false, unique = true, length = 40)
  private String employeeId;

  @Column(nullable = false, length = 160)
  private String name;

  /** Project as plain string */
  @Column(length = 120)
  private String project;

  @ManyToMany
  @JoinTable(name = "employee_skills",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  @Builder.Default
  private Set<Skill> skills = new LinkedHashSet<>();

  @ManyToMany
  @JoinTable(name = "employee_hobbies",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "hobby_id"))
  @Builder.Default
  private Set<Hobby> hobbies = new LinkedHashSet<>();

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;
}
