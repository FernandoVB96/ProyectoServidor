package com.vedruna.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.model.Technology;
import com.vedruna.proyectoFinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.proyectoFinal.persistance.repository.TechnologyRepositoryI;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI {

    @Autowired
    TechnologyRepositoryI technologyRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    /**
     * Guarda una tecnología en la base de datos, validando su existencia y asociando los proyectos correspondientes.
     * 
     * @param technology la tecnología a guardar
     * @throws IllegalArgumentException si el ID de la tecnología ya está en uso o si alguno de los proyectos asociados no existe
     */
    @Override
    public void saveTechnology(Technology technology) {
        if (technologyRepository.existsById(technology.getId())) {
            throw new IllegalArgumentException("El ID de la tecnología ya está en uso");
        }
        
        List<Project> projects = new ArrayList<>();
        
        for (Project project : technology.getProjectsTechnologies()) {
            projectRepository.findById(project.getId()).ifPresentOrElse(
                projects::add, 
                () -> { 
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        technology.setProjectsTechnologies(projects);
        technologyRepository.save(technology);
    }

    /**
     * Elimina una tecnología de la base de datos.
     * 
     * @param id el ID de la tecnología a eliminar
     * @return true si la tecnología fue eliminada correctamente, false si no se encuentra
     * @throws IllegalArgumentException si no se encuentra la tecnología con el ID especificado
     */
    @Override
    public boolean deleteTechnology(Integer id) {
        Optional<Technology> technology = technologyRepository.findById(id);
    
        if (technology.isPresent()) {
            technologyRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("No existe ninguna tecnología con el ID: " + id);
        }
    }

    /**
     * Busca una tecnología por su ID.
     * 
     * @param techId el ID de la tecnología
     * @return la tecnología encontrada o null si no existe
     */
    public Technology findById(Integer techId) {
        return technologyRepository.findById(techId).orElse(null);
    }

    /**
     * Asocia una tecnología con un proyecto existente.
     * 
     * @param projectId el ID del proyecto
     * @param technologyId el ID de la tecnología
     * @throws IllegalArgumentException si no se encuentra la tecnología o el proyecto con los IDs especificados
     */
    @Override
    public void associateTechnologyWithProject(int projectId, int technologyId) {
        Technology technology = technologyRepository.findById(technologyId).orElseThrow(() -> 
            new IllegalArgumentException("No se encontró la tecnología con el ID " + technologyId));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> 
            new IllegalArgumentException("No se encontró el proyecto con el ID " + projectId));
        
        project.getTechnologies().add(technology);
        technology.getProjectsTechnologies().add(project);
        
        projectRepository.save(project);
        technologyRepository.save(technology);
    }
}
