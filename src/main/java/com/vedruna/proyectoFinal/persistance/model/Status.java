package com.vedruna.proyectoFinal.persistance.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa el estado de un proyecto en la base de datos.
 * Esta clase mapea la entidad "status" en la base de datos.
 */
@NoArgsConstructor
@Data
@Entity
@Table(name="status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="status_id")
    private int id;

    @Column(name="status_name")
    @NotNull(message = "El nombre no puede ser nulo.")
    private String name;

    @OneToMany(fetch= FetchType.LAZY, mappedBy="statusProject")
    private List<Project> statusWithProject;
}
