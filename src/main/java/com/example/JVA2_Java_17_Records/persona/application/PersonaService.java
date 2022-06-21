package com.example.JVA2_Java_17_Records.persona.application;

import com.example.JVA2_Java_17_Records.persona.domain.Persona;
import com.example.JVA2_Java_17_Records.persona.infraestructure.dto.PersonaDTO;
import com.example.JVA2_Java_17_Records.persona.infraestructure.repository.PersonaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    ModelMapper modelMapper;

    public PersonaDTO addPersona(PersonaDTO personaDTO) throws Exception {
        if(personaDTO.usuario() != null && personaDTO.usuario().length() <= 10 && personaDTO.usuario().length() >= 6){
            if(personaDTO.name() != null){
                if (personaDTO.surname() != null) {
                    if (personaDTO.password() != null) {
                        if (personaDTO.company_email() != null) {
                            if(personaDTO.personal_email() != null){
                                if (personaDTO.city() != null) {
                                    Persona persona = new Persona();
                                    persona.setUsuario(personaDTO.usuario());
                                    persona.setName(personaDTO.name());
                                    persona.setSurname(personaDTO.surname());
                                    persona.setPassword(personaDTO.password());
                                    persona.setCompany_email(personaDTO.company_email());
                                    persona.setPersonal_email(personaDTO.personal_email());
                                    persona.setCity(personaDTO.city());

                                    personaRepository.save(persona);
                                    return personaDTO;
                                } else {
                                    throw new Exception("El campo email personal no puede estar vacío");
                                }
                            }else{
                                throw new Exception("El campo email personal no puede estar vacío");
                            }
                        } else {
                            throw new Exception("El campo email corporativo no puede estar vacío");
                        }
                    }else{
                        throw new Exception("El campo contraseña no puede estar vacío");
                    }
                }else{
                    throw new Exception("El campo apellido no puede estar vacío");
                }
            }else{
                throw new Exception("El campo nombre no puede estar vacío");
            }
        }else{
            throw new Exception("El campo usuario debe tener entre 6 y 10 caracteres");
        }
    }

    public Persona getPersonaByID(int id) throws Exception {
        return personaRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado a la persona con el id: "+id));
    }

    public List<PersonaDTO> getPersonaByName(String name){

        List <PersonaDTO> listaPersonasDTO = new ArrayList<>();
        personaRepository.findAll().forEach(
                persona -> {
                    if(persona.getName().equals(name)){
                        PersonaDTO personaDTO = new PersonaDTO(persona.getId_persona(),persona.getUsuario(), persona.getPassword(), persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(), persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date());
                        listaPersonasDTO.add(personaDTO);
                    }
                }
        );

        return listaPersonasDTO;
    }

    public List<PersonaDTO> getAll(){
        List <PersonaDTO> listaPersonas = new ArrayList<>();
        personaRepository.findAll().forEach(
                persona -> {
                    PersonaDTO personaDTO = new PersonaDTO(persona.getId_persona(),persona.getUsuario(), persona.getPassword(), persona.getName(), persona.getSurname(), persona.getCompany_email(), persona.getPersonal_email(), persona.getCity(), persona.getActive(), persona.getCreated_date(), persona.getImagen_url(), persona.getTermination_date());
                    listaPersonas.add(personaDTO);
                }
        );
        return listaPersonas;
    }

    public PersonaDTO updatePersona(int id, PersonaDTO personaDTO) {
        Optional<Persona> personaEntity = personaRepository.findById(id);
        if (personaEntity.isPresent()) {
            personaEntity.get().setUsuario(personaDTO.usuario());
            personaEntity.get().setPassword(personaDTO.password());
            personaEntity.get().setName(personaDTO.name());
            personaEntity.get().setSurname(personaDTO.surname());
            personaEntity.get().setCompany_email(personaDTO.company_email());
            personaEntity.get().setPersonal_email(personaDTO.personal_email());
            personaEntity.get().setCity(personaDTO.city());
            personaEntity.get().setImagen_url(personaDTO.image_url());
            personaEntity.get().setActive(personaDTO.active());
            personaEntity.get().setCreated_date((Date) personaDTO.created_date());

            personaRepository.saveAndFlush(modelMapper.map(personaEntity, Persona.class));
            return personaDTO;
        } else {
            return null;
        }
    }

    public String deletePersona(int id){
        personaRepository.deleteById(id);
        return "Persona eliminada";
    }
}
