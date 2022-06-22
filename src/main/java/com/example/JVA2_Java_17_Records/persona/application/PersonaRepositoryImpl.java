package com.example.JVA2_Java_17_Records.persona.application;

import com.example.JVA2_Java_17_Records.persona.domain.Persona;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static jdk.javadoc.internal.doclets.formats.html.markup.Entity.GREATER_THAN;
import static jdk.javadoc.internal.doclets.formats.html.markup.Entity.LESS_THAN;

@RestController
public class PersonaRepositoryImpl {

    ////////////////////////////////BBA1. CirteriaBuilder////////////////////////////////

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/filtroCriteria")
    public List<Persona> getData(HashMap<String, Object> params){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query= cb.createQuery(Persona.class);
        Root<Persona> root = query.from(Persona.class);

        List<Predicate> predicates = new ArrayList<>();

        params.forEach((field,value) ->
        {
            switch (field)
            {
                case "user":
                    predicates.add(cb.equal(root.get(field), value));
                    break;
                case "name":
                    predicates.add(cb.like(root.get(field),"%"+ value +"%"));
                    break;
                case "surname":
                    predicates.add(cb.like(root.get(field),"%"+value+"%"));
                    break;
                case "created_date":
                    LocalTime dateCondition=(LocalTime) params.get("dateCondition");
                    if (GREATER_THAN.equals(dateCondition)) {
                        predicates.add(cb.greaterThan(root.<Date>get(field), (Date) value));
                    } else if (LESS_THAN.equals(dateCondition)) {
                        predicates.add(cb.lessThan(root.<Date>get(field), (Date) value));
                    } else {
                        predicates.add(cb.equal(root.<Date>get(field), value));
                    }
                    break;
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
