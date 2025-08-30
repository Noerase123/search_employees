package com.example.app_inter.employee.repositories;
import com.example.app_inter.employee.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
  Optional<Skill> findByNameIgnoreCase(String name);
}
