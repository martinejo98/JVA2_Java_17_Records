package com.example.JVA2_Java_17_Records.persona.infraestructure.repository;

import com.example.JVA2_Java_17_Records.persona.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaRepository extends JpaRepository <Persona, Integer> {
}
