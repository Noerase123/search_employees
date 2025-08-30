package com.example.app_inter.employee.repositories;
import com.example.app_inter.employee.models.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
  Optional<Hobby> findByNameIgnoreCase(String name);
}
