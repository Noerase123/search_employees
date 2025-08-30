package com.example.app_inter.employee.service;

import com.example.app_inter.employee.dto.EmployeeResponse;
import com.example.app_inter.employee.models.Employee;
import com.example.app_inter.employee.models.Hobby;
import com.example.app_inter.employee.models.Skill;
import com.example.app_inter.employee.repositories.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  public EmployeeService(EmployeeRepository employeeRepository) { this.employeeRepository = employeeRepository; }

  public Page<EmployeeResponse> search(
      String employeeId,
      String name,
      String project,
      Set<String> skills,
      Set<String> hobbies,
      boolean requireAllSkills,
      boolean requireAllHobbies,
      boolean matchAny,
      Pageable pageable
  ) {
    return employeeRepository.findAll(
        EmployeeSpecifications.fromParams(
            employeeId, name, project, skills, hobbies, requireAllSkills, requireAllHobbies, matchAny
        ),
        pageable
    ).map(this::toResponse);
  }

  private EmployeeResponse toResponse(Employee e) {
    var skills = e.getSkills().stream().map(Skill::getName).sorted(Comparator.naturalOrder()).toList();
    var hobbies = e.getHobbies().stream().map(Hobby::getName).sorted(Comparator.naturalOrder()).toList();
    return new EmployeeResponse(e.getId(), e.getEmployeeId(), e.getName(), e.getProject(), skills, hobbies);
  }
}
