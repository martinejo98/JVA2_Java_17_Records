package com.example.JVA2_Java_17_Records;

public record PersonaDTO(int id, String usuario, String password, String name, String surname, String company_email, String personal_email, String city, Boolean active, String created_date, String image_url, String termination_date) {

    /*public PersonaDTO(Integer id, String usuario, String password, String name, String surname, String company_email, String personal_email, String city, Boolean active, String created_date, String image_url, String termination_date) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.company_email = company_email;
        this.personal_email = personal_email;
        this.city = city;
        this.active = active;
        this.created_date = created_date;
        this.image_url = image_url;
        this.termination_date = termination_date;
    }

    public Integer id() {
        return this.id;
    }

    public String usuario() {
        return this.usuario;
    }


    public String password() {
        return this.password;
    }

    public String name() {
        return this.name;
    }

    public String surname() {
        return this.surname;
    }

    public String company_email() {
        return this.company_email;
    }

    public String personal_email() {
        return this.personal_email;
    }

    public String city() {
        return this.city;
    }


    public Boolean active() {
        return this.active;
    }


    public String created_date() {
        return this.created_date;
    }


    public String image_url() {
        return this.image_url;
    }


    public String termination_date() {
        return this.termination_date;
    }*/

}
