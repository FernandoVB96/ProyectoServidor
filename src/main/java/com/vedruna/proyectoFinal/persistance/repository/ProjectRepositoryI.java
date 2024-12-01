package com.vedruna.proyectoFinal.persistance.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.vedruna.proyectoFinal.dto.ProjectDTO;
import com.vedruna.proyectoFinal.persistance.model.Project;

public interface ProjectRepositoryI extends JpaRepository<Project, Integer> {

    public Optional<Project> findByName(String name);

    Page<Project> findAll(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Project p JOIN p.technologies t WHERE t.name = :techName")
    List<ProjectDTO> findProjectsByTechnology(String techName);
}
