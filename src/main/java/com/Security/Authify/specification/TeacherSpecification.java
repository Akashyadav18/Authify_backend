package com.Security.Authify.specification;

import com.Security.Authify.entity.TeacherEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherSpecification {

    public static Specification<TeacherEntity> teachSpecification(Long id, String name, String qualification, Date startDate, Date endDate) {
        return new Specification<TeacherEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<TeacherEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(id == null && name == null && qualification == null && startDate == null && endDate == null){
                    return criteriaBuilder.conjunction();
                }
                List<Predicate> list = new ArrayList<>();
               if(id != null){
                   list.add(criteriaBuilder.equal(root.get("id"), id));
               }
               if(name != null && !name.isEmpty()){
                    Predicate firstName = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("firstName")), "%"+name.toLowerCase()+"%"
                    );
                    Predicate lastName = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("lastName")), "%"+name.toLowerCase()+"%"
                    );
                      list.add(criteriaBuilder.or(firstName, lastName));
               }
               if(qualification != null && !qualification.isEmpty()){
                   list.add(criteriaBuilder.equal(root.get("qualification"), qualification));
               }
               if(startDate != null && endDate != null){
                   list.add(criteriaBuilder.and
                           (criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate),
                                   criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate)));
               }

               return criteriaBuilder.or(list.toArray(new Predicate[0]));
            }
        };
    }
}
