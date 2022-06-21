package com.example.JVA2_Java_17_Records.persona.infraestructure.dto;

import java.util.Date;

public record PersonaDTO(int id, String usuario, String password, String name, String surname, String company_email, String personal_email, String city, Boolean active, Date created_date, String image_url, Date termination_date) {

}
