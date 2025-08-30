package com.example.app_inter.employee.controllers;

import com.example.app_inter.employee.dto.EmployeeResponse;
import com.example.app_inter.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "List and search employees via a single endpoint")
public class EmployeeController {

  private final EmployeeService service;
  public EmployeeController(EmployeeService service) { this.service = service; }

  @Operation(
      summary = "List & search employees",
      description = """
        Filters: employeeId (exact), name/project (contains), skills/hobbies (ANY or ALL).
        Set matchAny=true to use OR across fields (e.g., name OR project OR skills OR hobbies).
        """
  )
  @GetMapping
  public Page<EmployeeResponse> listAndSearch(
      @RequestParam(name = "employeeId", required = false) String employeeId,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "project", required = false) String project,
      @RequestParam(name = "skills", required = false) String skillsCsv,
      @RequestParam(name = "hobbies", required = false) String hobbiesCsv,
      @RequestParam(name = "requireAllSkills", defaultValue = "false") boolean requireAllSkills,
      @RequestParam(name = "requireAllHobbies", defaultValue = "false") boolean requireAllHobbies,
      @RequestParam(name = "matchAny", defaultValue = "false") boolean matchAny,   // NEW
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size,
      @RequestParam(name = "sort", defaultValue = "name,asc") String sort
  ) {
    Pageable pageable = PageRequest.of(page, Math.min(size, 200), toSort(sort));
    return service.search(
        emptyToNull(employeeId),
        emptyToNull(name),
        emptyToNull(project),
        csvToSet(skillsCsv),
        csvToSet(hobbiesCsv),
        requireAllSkills,
        requireAllHobbies,
        matchAny,
        pageable
    );
  }

  // helpers
  private static Set<String> csvToSet(String csv) {
    if (csv == null || csv.isBlank()) return null;
    return Arrays.stream(csv.split(",")).map(String::trim).filter(s -> !s.isBlank()).collect(Collectors.toSet());
  }
  private static Sort toSort(String sortParam) {
    String[] parts = sortParam.split(",");
    String field = parts[0];
    Sort.Direction dir = (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
    return Sort.by(dir, field);
  }
  private static String emptyToNull(String s) { return (s == null || s.isBlank()) ? null : s; }
}
