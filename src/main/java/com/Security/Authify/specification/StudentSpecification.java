package com.Security.Authify.specification;

import com.Security.Authify.entity.StudentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<StudentEntity> getSpecification(String search) {
        return new Specification<StudentEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(search == null || search.isEmpty()){
                   return criteriaBuilder.conjunction();
                }

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%"+search.toLowerCase()+"%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%"+search.toLowerCase()+"%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%"+search.toLowerCase()+"%"));
                predicates.add(criteriaBuilder.equal(root.get("rollNo"), search));

                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
