package com.example.JVA2_Java_17_Records.persona.infraestructure.controller;

import com.example.JVA2_Java_17_Records.persona.application.PersonaService;
import com.example.JVA2_Java_17_Records.persona.domain.Persona;
import com.example.JVA2_Java_17_Records.persona.infraestructure.dto.PersonaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static javax.xml.datatype.DatatypeConstants.EQUAL;
import static sun.tools.jconsole.Messages.GREATER_THAN;
import static sun.tools.jconsole.Messages.LESS_THAN;


@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/addPersona")
    public String addPersona(@RequestBody PersonaDTO personaDTO) throws Exception {
        personaService.addPersona(personaDTO);
        return "Persona añadida";
    }

    @GetMapping("/getPersonaID/{id}")
    public Persona getPersonaByID(@PathVariable int id) throws Exception {
        return personaService.getPersonaByID(id);
    }


    @GetMapping("/getPersonaName/{name}")
    public List<PersonaDTO> getPersonaByName(@PathVariable String name){
        return personaService.getPersonaByName(name);
    }

    @GetMapping("/getAll")
    public List <PersonaDTO> getAll(){
        return personaService.getAll();
    }

    @PutMapping("/update/{id}")
    public String updatePersona(@RequestBody PersonaDTO personaDTO, @PathVariable int id){
        personaService.updatePersona(id, personaDTO);
        return "Persona actualziada";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePersona(@PathVariable int id){
        personaService.deletePersona(id);
        return "Persona eliminada";
    }


    ////////////////////////////////BBA1. CirteriaBuilder////////////////////////////////

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/filtroCriteria")
    public List<Persona> getData(@RequestParam HashMap<String, Object> params){

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
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                case "surname":
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                /*case "created_date":
                    String dateCondition=(String) params.get("dateCondition");
                    switch (dateCondition)
                    {
                        case GREATER_THAN:
                            predicates.add(cb.greaterThan(root.<Date>get(field), (Date) value));
                            break;
                        case LESS_THAN:
                            predicates.add(cb.lessThan(root.<Date>get(field), (Date) value));
                            break;
                        case EQUAL:
                            predicates.add(cb.equal(root.<Date>get(field), value));
                            break;
                    }
                    break;*/
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
