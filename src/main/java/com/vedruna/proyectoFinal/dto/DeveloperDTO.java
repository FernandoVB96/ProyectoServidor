package com.vedruna.proyectoFinal.dto;

import com.vedruna.proyectoFinal.persistance.model.Developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa un desarrollador con su información personal
 * y sus perfiles en redes sociales profesionales.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDTO {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String linkedin_url;
    private String github_url;

    /**
     * Constructor que crea un DeveloperDTO a partir de un objeto Developer.
     * 
     * @param d el objeto Developer que se va a convertir a DeveloperDTO
     */
    public DeveloperDTO(Developer d) {
        this.id = d.getId();
        this.name = d.getName();
        this.surname = d.getSurname();
        this.email = d.getEmail();
        this.linkedin_url = d.getLinkedin_url();
        this.github_url = d.getGithub_url();
    }
}
