package com.vedruna.proyectoFinal.persistance.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.vedruna.proyectoFinal.persistance.model.Project;

public interface ProjectRepositoryI extends JpaRepository<Project, Integer> {

    // Método para buscar un proyecto por su nombre.
    public Optional<Project> findByName(String name);

    // Método para obtener todos los proyectos con paginación.
    Page<Project> findAll(Pageable pageable);

    // Método para encontrar proyectos que utilicen una tecnología específica.
    @Query("SELECT p FROM Project p JOIN p.technologies t WHERE t.name = :techName")
    List<Project> findProjectsByTechnology(String techName);
}
