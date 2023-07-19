package com.formacion.examen_JPA_cascada.repository;

import com.formacion.examen_JPA_cascada.controllers.output.ClienteOutputDto;
import com.formacion.examen_JPA_cascada.domain.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClienteRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ClienteOutputDto> getCustomQuery(HashMap<String, Object> conditions) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
        Root<Cliente> root = query.from(Cliente.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value) -> {
            switch (field) {
                case "nombre":
                    predicates.add(cb.like(cb.lower(root.get(field)),
                            "%" + ((String) value).toLowerCase() + "%"));
                    break;
                case "poblacion":
                    predicates.add(cb.equal(root.get(field), value));
                    break;
                case "tiempo_en_empresa":
                    predicates.add(cb.greaterThanOrEqualTo(root.get(field), (Integer) value));
                    break;
            }
        });

        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(Cliente::clienteToClienteOutputDto)
                .toList();
    }
}
