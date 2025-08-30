// src/main/java/com/example/employee/api/dto/EmployeeResponse.java
package com.example.app_inter.employee.dto;

import java.util.List;

public record EmployeeResponse(
    Long id,
    String employeeId,
    String name,
    String project,
    List<String> skills,
    List<String> hobbies
) {}
