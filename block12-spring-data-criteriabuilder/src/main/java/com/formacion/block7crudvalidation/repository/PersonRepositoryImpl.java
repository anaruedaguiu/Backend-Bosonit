package com.formacion.block7crudvalidation.repository;

import com.formacion.block7crudvalidation.controllers.dto.output.PersonOutputDto;
import com.formacion.block7crudvalidation.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PersonRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PersonOutputDto> getCustomQuery(HashMap<String, Object> conditions, String orderBy, int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = cb.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value) -> {
            switch (field) {
                case "username":
                    predicates.add(cb.like(root.get(field), "%" + (String) value + "%"));
                    break;
                case "name":
                    predicates.add(cb.like(root.get(field), "%" + (String) value + "%"));
                    break;
                case "surname":
                    predicates.add(cb.like(root.get(field), "%" + (String) value + "%"));
                    break;
                case "created_date":
                    LocalDate date = (LocalDate) value;
                    predicates.add(cb.greaterThanOrEqualTo(root.get(field), date));
                    predicates.add((cb.lessThanOrEqualTo(root.get(field), date)));
                    break;
            }
        });

        if(orderBy.equals("Order by username")) {
            query.orderBy(cb.asc(root.get("username")));
        }
        if(orderBy.equals("Order by name")) {
            query.orderBy(cb.asc(root.get("name")));
        }

        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager
                .createQuery(query)
                .setFirstResult((pageNumber - 1) * pageSize) // determina a partir de qué registro comenzar a obtener los resultados
                .setMaxResults(pageSize) // máximo número de resultados por página
                .getResultList()
                .stream()
                .map(Person::personToPersonOutputDto)
                .toList();
    }
}
