package com.vedruna.proyectoFinal.persistance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinal.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa a un desarrollador en la base de datos.
 * Esta clase mapea la entidad "developers" en la base de datos.
 */
@NoArgsConstructor
@Data
@Entity
@Table(name="developers")
public class Developer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dev_id")
    private int id;

    @Column(name="dev_name")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String name;

    @Column(name="dev_surname")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres.")
    private String surname;

    @Column(name="email")
    @Email(message = "El correo electrónico debe ser válido.")
    private String email;

    @Column(name="linkedin_url")
    @ValidUrl(message = "Formato de URL inválido.")
    private String linkedin_url;

    @Column(name="github_url")
    @ValidUrl(message = "Formato de URL inválido.")
    private String github_url;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="developers_worked_on_projects", 
               joinColumns={@JoinColumn(name="developers_dev_id")}, 
               inverseJoinColumns={@JoinColumn(name="projects_project_id")})
    private List<Project> projectsDevelopers = new ArrayList<>();
}
