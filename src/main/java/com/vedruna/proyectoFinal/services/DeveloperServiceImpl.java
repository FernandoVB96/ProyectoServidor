package com.vedruna.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vedruna.proyectoFinal.persistance.model.Developer;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.repository.DeveloperRepositoryI;
import com.vedruna.proyectoFinal.persistance.repository.ProjectRepositoryI;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI {

    @Autowired
    DeveloperRepositoryI developerRepository; // Repositorio para interactuar con los desarrolladores.

    @Autowired
    ProjectRepositoryI projectRepository; // Repositorio para interactuar con los proyectos.

    // Método para guardar un desarrollador con los proyectos asociados.
    @Override
    public void saveDeveloper(Developer developer) {
        List<Project> managedProjects = new ArrayList<>();
        
        // Verifica que los proyectos asociados al desarrollador existan en la base de datos.
        for (Project project : developer.getProjectsDevelopers()) {
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add, 
                () -> { 
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        // Asocia los proyectos gestionados al desarrollador.
        developer.setProjectsDevelopers(managedProjects);
        
        // Guarda el desarrollador con los proyectos asociados.
        developerRepository.save(developer);
    }

    // Método para eliminar un desarrollador por su ID.
    @Override
    public boolean deleteDeveloper(Integer id) {
        Optional<Developer> developer = developerRepository.findById(id); // Busca al desarrollador por ID.
        
        if (developer.isPresent()) {
            developerRepository.deleteById(id); // Elimina al desarrollador si se encuentra.
            return true;
        } else {
            throw new IllegalArgumentException("No existe ningún developer con el ID: " + id); // Lanza excepción si no se encuentra el desarrollador.
        }
    }

    // Método para encontrar un desarrollador por su ID.
    @Override
    public Developer findById(Integer developerId) {
        return developerRepository.findById(developerId).orElse(null); // Devuelve el desarrollador encontrado o null si no existe.
    }
}
