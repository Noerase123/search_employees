package com.example.app_inter.employee.service;

import com.example.app_inter.employee.models.Employee;
import com.example.app_inter.employee.models.Hobby;
import com.example.app_inter.employee.models.Skill;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

public final class EmployeeSpecifications {
  private EmployeeSpecifications() {}

  public static Specification<Employee> fromParams(
      String employeeId,
      String name,
      String project,
      Set<String> skills,
      Set<String> hobbies,
      boolean requireAllSkills,
      boolean requireAllHobbies,
      boolean matchAnyAcrossFields   // NEW
  ) {
    return (root, query, cb) -> {
      query.distinct(true);
      List<Predicate> fields = new ArrayList<>();

      if (notBlank(employeeId)) {
        fields.add(cb.equal(cb.lower(root.get("employeeId")), employeeId.toLowerCase(Locale.ROOT)));
      }
      if (notBlank(name)) {
        fields.add(ilike(cb, root.get("name"), name));
      }
      if (notBlank(project)) {
        fields.add(ilike(cb, root.get("project"), project));
      }

      if (notEmpty(skills)) {
        Set<String> sset = lowerSet(skills);
        Predicate skillPredicate;
        if (requireAllSkills) {
          List<Predicate> all = new ArrayList<>();
          for (String s : sset) all.add(existsSkill(root, query, cb, s));
          skillPredicate = andAll(all, cb);
        } else {
          Join<Employee, Skill> sj = root.join("skills", JoinType.LEFT);
          skillPredicate = cb.lower(sj.get("name")).in(sset);
        }
        fields.add(skillPredicate);
      }

      if (notEmpty(hobbies)) {
        Set<String> hset = lowerSet(hobbies);
        Predicate hobbyPredicate;
        if (requireAllHobbies) {
          List<Predicate> all = new ArrayList<>();
          for (String h : hset) all.add(existsHobby(root, query, cb, h));
          hobbyPredicate = andAll(all, cb);
        } else {
          Join<Employee, Hobby> hj = root.join("hobbies", JoinType.LEFT);
          hobbyPredicate = cb.lower(hj.get("name")).in(hset);
        }
        fields.add(hobbyPredicate);
      }

      if (fields.isEmpty()) return cb.conjunction(); // no filters -> all rows
      return matchAnyAcrossFields
          ? cb.or(fields.toArray(Predicate[]::new))
          : cb.and(fields.toArray(Predicate[]::new));
    };
  }

  private static Predicate andAll(List<Predicate> list, CriteriaBuilder cb) {
    return list.size() == 1 ? list.get(0) : cb.and(list.toArray(Predicate[]::new));
  }

  private static Predicate ilike(CriteriaBuilder cb, Path<String> path, String q) {
    return cb.like(cb.lower(path), "%" + q.toLowerCase(Locale.ROOT) + "%");
  }
  private static boolean notBlank(String s) { return s != null && !s.isBlank(); }
  private static boolean notEmpty(Set<?> s) { return s != null && !s.isEmpty(); }
  private static Set<String> lowerSet(Set<String> in) {
    return in.stream().filter(Objects::nonNull).map(v -> v.toLowerCase(Locale.ROOT)).collect(Collectors.toSet());
  }

  private static Predicate existsSkill(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb, String s) {
    Subquery<Long> sq = query.subquery(Long.class);
    Root<Employee> e2 = sq.from(Employee.class);
    Join<Employee, Skill> sj = e2.join("skills");
    sq.select(e2.get("id")).where(cb.equal(e2.get("id"), root.get("id")), cb.equal(cb.lower(sj.get("name")), s));
    return cb.exists(sq);
  }

  private static Predicate existsHobby(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb, String h) {
    Subquery<Long> sq = query.subquery(Long.class);
    Root<Employee> e2 = sq.from(Employee.class);
    Join<Employee, Hobby> hj = e2.join("hobbies");
    sq.select(e2.get("id")).where(cb.equal(e2.get("id"), root.get("id")), cb.equal(cb.lower(hj.get("name")), h));
    return cb.exists(sq);
  }
}
