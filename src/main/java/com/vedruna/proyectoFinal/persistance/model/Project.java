package com.vedruna.proyectoFinal.persistance.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinal.validation.ValidUrl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un proyecto en la base de datos.
 * Esta clase mapea la entidad "projects" en la base de datos.
 */
@NoArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private int id;

    @Column(name="project_name")
    @NotNull(message = "El nombre no puede ser nulo.")
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;

    @Column(name="description")
    @Size(min = 2, max = 50, message = "La descripción debe tener entre 2 y 50 caracteres.")
    private String description;

    @Column(name="start_date")
    @FutureOrPresent(message = "La fecha de inicio no puede ser anterior a la fecha actual.")
    private Date start_date;

    @Column(name="end_date")
    private Date end_date;

    @Column(name="repository_url")
    @ValidUrl(message = "Formato de URL inválido.")
    private String repository_url;

    @Column(name="demo_url")
    @ValidUrl(message = "Formato de URL inválido.")
    private String demo_url;

    @Column(name="picture")
    private String picture;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="status_status_id", referencedColumnName = "status_id")
    private Status statusProject;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsTechnologies")
    private List<Technology> technologies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="projectsDevelopers")
    private List<Developer> developers = new ArrayList<>();
}
