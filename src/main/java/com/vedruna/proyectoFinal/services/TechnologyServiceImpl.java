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

@Service // Indica que esta clase es un servicio que se gestiona como un bean en el contexto de Spring.
public class TechnologyServiceImpl implements TechnologyServiceI {

    @Autowired
    TechnologyRepositoryI technologyRepository; // Repositorio para interactuar con las tecnologías.

    @Autowired
    ProjectRepositoryI projectRepository; // Repositorio para interactuar con los proyectos.

    @Override
    public void saveTechnology(Technology technology) {
        // Verifica si ya existe una tecnología con el mismo ID en la base de datos.
        if (technologyRepository.existsById(technology.getId())) {
            throw new IllegalArgumentException("El ID de la tecnología ya está en uso");
        }
        
        List<Project> managedProjects = new ArrayList<>();
        
        // Recorre los proyectos asociados a la tecnología y los valida.
        for (Project project : technology.getProjectsTechnologies()) {
            // Busca el proyecto por su ID, si existe lo agrega a la lista de proyectos gestionados.
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add, 
                () -> { 
                    // Si no se encuentra el proyecto, lanza una excepción.
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        // Asocia los proyectos gestionados a la tecnología.
        technology.setProjectsTechnologies(managedProjects);
        
        // Guarda la tecnología con los proyectos asociados en la base de datos.
        technologyRepository.save(technology);
    }

    @Override
    public boolean deleteTechnology(Integer id) {
        // Busca la tecnología por su ID.
        Optional<Technology> technology = technologyRepository.findById(id);
    
        // Si la tecnología existe, la elimina.
        if (technology.isPresent()) {
            technologyRepository.deleteById(id); 
            return true;
        } else {
            // Si no se encuentra la tecnología, lanza una excepción con un mensaje.
            throw new IllegalArgumentException("No existe ninguna tecnología con el ID: " + id);
        }
    }

    public Technology findById(Integer techId) {
        // Busca la tecnología y la devuelve si existe, de lo contrario devuelve null.
        return technologyRepository.findById(techId).orElse(null); 
    }

    @Override
    public void associateTechnologyWithProject(int projectId, int technologyId) {
        // Busca la tecnología por su ID, lanza una excepción si no se encuentra.
        Technology technology = technologyRepository.findById(technologyId).orElseThrow(() -> 
            new IllegalArgumentException("Technology with ID " + technologyId + " not found"));
        // Busca el proyecto por su ID, lanza una excepción si no se encuentra.
        Project project = projectRepository.findById(projectId).orElseThrow(() -> 
            new IllegalArgumentException("Project with ID " + projectId + " not found"));
        
        // Agrega la tecnología al proyecto y el proyecto a la tecnología.
        project.getTechnologies().add(technology);
        technology.getProjectsTechnologies().add(project);
        
        // Guarda los cambios tanto en el proyecto como en la tecnología.
        projectRepository.save(project);
        technologyRepository.save(technology);
    }
}
