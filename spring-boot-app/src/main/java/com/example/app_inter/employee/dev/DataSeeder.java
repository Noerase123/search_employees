package com.example.app_inter.employee.dev;

import com.example.app_inter.employee.models.Employee;
import com.example.app_inter.employee.models.Hobby;
import com.example.app_inter.employee.models.Skill;
import com.example.app_inter.employee.repositories.EmployeeRepository;
import com.example.app_inter.employee.repositories.HobbyRepository;
import com.example.app_inter.employee.repositories.SkillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Profile("!prod") // runs unless you set spring.profiles.active=prod
public class DataSeeder implements CommandLineRunner {

  private final EmployeeRepository employeeRepo;
  private final SkillRepository skillRepo;
  private final HobbyRepository hobbyRepo;

  public DataSeeder(EmployeeRepository employeeRepo, SkillRepository skillRepo, HobbyRepository hobbyRepo) {
    this.employeeRepo = employeeRepo;
    this.skillRepo = skillRepo;
    this.hobbyRepo = hobbyRepo;
  }

  @Override
  @Transactional
  public void run(String... args) {
    if (employeeRepo.count() > 0) return; // already seeded

    // --- skills (upsert by name) ---
    Skill JAVA        = skill("Java");
    Skill SPRING      = skill("Spring Boot");
    Skill POSTGRES    = skill("PostgreSQL");
    Skill REACT       = skill("React");
    Skill TS          = skill("TypeScript");
    Skill GO          = skill("Go");
    Skill KUBE        = skill("Kubernetes");
    Skill PY          = skill("Python");
    Skill DJANGO      = skill("Django");
    Skill DOCKER      = skill("Docker");
    Skill KAFKA       = skill("Kafka");
    Skill NODE        = skill("Node.js");
    Skill EXPRESS     = skill("Express");
    Skill AWS         = skill("AWS");
    Skill TERRAFORM   = skill("Terraform");
    Skill NEXTJS      = skill("Next.js");
    Skill FASTAPI     = skill("FastAPI");

    // --- hobbies (upsert by name) ---
    Hobby CHESS       = hobby("Chess");
    Hobby READING     = hobby("Reading");
    Hobby BASKETBALL  = hobby("Basketball");
    Hobby CYCLING     = hobby("Cycling");
    Hobby COOKING     = hobby("Cooking");
    Hobby GAMING      = hobby("Gaming");
    Hobby MUSIC       = hobby("Music");
    Hobby HIKING      = hobby("Hiking");
    Hobby PHOTO       = hobby("Photography");

    // --- employees (projects are plain String) ---
    employeeRepo.save(Employee.builder()
        .employeeId("E0001").name("John Caasi").project("Alpha")
        .skills(Set.of(JAVA, SPRING, POSTGRES))
        .hobbies(Set.of(BASKETBALL, CHESS))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0002").name("Jane Dela Cruz").project("Alpha")
        .skills(Set.of(REACT, TS, JAVA))
        .hobbies(Set.of(READING))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0003").name("Miguel Santos").project("Beta")
        .skills(Set.of(GO, KUBE))
        .hobbies(Set.of(CYCLING, CHESS))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0004").name("Ava Reyes").project("Gamma")
        .skills(Set.of(PY, DJANGO, POSTGRES))
        .hobbies(Set.of(COOKING))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0005").name("Ben Lee").project("Alpha")
        .skills(Set.of(JAVA, DOCKER))
        .hobbies(Set.of(GAMING))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0006").name("Carla Lim").project("Delta")
        .skills(Set.of(JAVA, KAFKA, SPRING))
        .hobbies(Set.of(MUSIC))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0007").name("Danilo Cruz").project("Beta")
        .skills(Set.of(NODE, EXPRESS, POSTGRES))
        .hobbies(Set.of(BASKETBALL))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0008").name("Erika Tan").project("Gamma")
        .skills(Set.of(AWS, TERRAFORM, KUBE))
        .hobbies(Set.of(HIKING))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0009").name("Franco Uy").project("Alpha")
        .skills(Set.of(REACT, NEXTJS, TS))
        .hobbies(Set.of(PHOTO))
        .build());

    employeeRepo.save(Employee.builder()
        .employeeId("E0010").name("Grace Ong").project("Delta")
        .skills(Set.of(PY, FASTAPI, AWS))
        .hobbies(Set.of(READING, CHESS))
        .build());
  }

  private Skill skill(String name) {
    return skillRepo.findByNameIgnoreCase(name).orElseGet(
        () -> skillRepo.save(Skill.builder().name(name).build())
    );
  }

  private Hobby hobby(String name) {
    return hobbyRepo.findByNameIgnoreCase(name).orElseGet(
        () -> hobbyRepo.save(Hobby.builder().name(name).build())
    );
  }
}
