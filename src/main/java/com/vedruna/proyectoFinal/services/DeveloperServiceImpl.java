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

/**
 * Implementación del servicio de los desarrolladores.
 * Proporciona métodos para gestionar la creación, eliminación y búsqueda de desarrolladores.
 */
@Service
public class DeveloperServiceImpl implements DeveloperServiceI {

    @Autowired
    private DeveloperRepositoryI developerRepository; // Repositorio para la gestión de desarrolladores.

    @Autowired
    private ProjectRepositoryI projectRepository; // Repositorio para la gestión de proyectos.

    /**
     * Guarda un desarrollador junto con los proyectos asociados.
     * Si alguno de los proyectos no existe, lanza una excepción.
     * 
     * @param developer el desarrollador que se desea guardar
     */
    @Override
    public void saveDeveloper(Developer developer) {
        List<Project> proyectosGestionados = new ArrayList<>();
        
        for (Project proyecto : developer.getProjectsDevelopers()) {
            Optional<Project> proyectoExistente = projectRepository.findById(proyecto.getId());
            
            if (proyectoExistente.isPresent()) {
                proyectosGestionados.add(proyectoExistente.get());
            } else {
                throw new IllegalArgumentException("No se encontró un proyecto con el ID: " + proyecto.getId());
            }
        }

        developer.setProjectsDevelopers(proyectosGestionados); // Asocia los proyectos al desarrollador.
        developerRepository.save(developer); // Guarda el desarrollador con los proyectos relacionados.
    }

    /**
     * Elimina un desarrollador por su ID.
     * 
     * @param id el ID del desarrollador que se desea eliminar
     * @return true si el desarrollador fue eliminado, false si no fue encontrado
     */
    @Override
    public boolean deleteDeveloper(Integer id) {
        Optional<Developer> developer = developerRepository.findById(id);
        
        if (developer.isPresent()) {
            developerRepository.deleteById(id); // Elimina al desarrollador de la base de datos.
            return true;
        } else {
            throw new IllegalArgumentException("No se encontró un desarrollador con el ID: " + id); // Lanza excepción si no existe.
        }
    }

    /**
     * Encuentra un desarrollador por su ID.
     * 
     * @param developerId el ID del desarrollador
     * @return el desarrollador encontrado o null si no se encuentra
     */
    @Override
    public Developer findById(Integer developerId) {
        return developerRepository.findById(developerId).orElse(null); // Devuelve el desarrollador o null si no existe.
    }
}
