package com.vedruna.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vedruna.proyectoFinal.dto.ProjectDTO;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.model.Status;
import com.vedruna.proyectoFinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.proyectoFinal.persistance.repository.StatusRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    ProjectRepositoryI projectRepository; // Repositorio para interactuar con los proyectos.

    @Autowired
    StatusRepositoryI stateRepository; // Repositorio para interactuar con los estados de los proyectos.

    // Método para obtener todos los proyectos en formato de página.
    @Override
    public Page<ProjectDTO> showAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Creación de un Pageable con el número de página y tamaño.
        Page<Project> projectPage = projectRepository.findAll(pageable); // Obtención de los proyectos paginados.
        return projectPage.map(ProjectDTO::new); // Mapear los proyectos a DTOs.
    }

    // Método para obtener un proyecto por su nombre.
    @Override
    public List<ProjectDTO> showProjectByName(String name) {
    List<Project> projects = projectRepository.findAll(); // Obtener todos los proyectos.
    List<Project> matchingProjects = new ArrayList<>(); // Lista para almacenar los proyectos que coinciden con la cadena.

    for (Project project : projects) {
        if (project.getName().contains(name)) { // Comprobar si el nombre contiene la cadena.
            matchingProjects.add(project); // Si coincide, agregarlo a la lista.
        }
    }

    if (matchingProjects.isEmpty()) {
        throw new IllegalArgumentException("No projects found with name containing: " + name); // Excepción si no se encuentra ningún proyecto.
    }

    return matchingProjects.stream()
                           .map(ProjectDTO::new) // Convertir cada proyecto a un DTO.
                           .collect(Collectors.toList()); // Devolver la lista de proyectos en formato DTO.
}


    // Método para guardar un proyecto en la base de datos.
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project); // Guarda el proyecto en la base de datos.
    }

    // Método para eliminar un proyecto por su ID.
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id); // Busca el proyecto por ID.
        if (project.isPresent()) {
            projectRepository.deleteById(id); // Elimina el proyecto si se encuentra.
            return true;
        } else {
            throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + id); // Lanza excepción si no se encuentra el proyecto.
        }
    }

    // Método para actualizar un proyecto existente.
    public boolean updateProject(Integer id, Project project) {
        Optional<Project> projectToUpdate = projectRepository.findById(id); // Busca el proyecto por ID.
        if (projectToUpdate.isPresent()) {
            projectToUpdate.get().setName(project.getName()); // Actualiza los campos del proyecto.
            projectToUpdate.get().setDescription(project.getDescription());
            projectToUpdate.get().setStart_date(project.getStart_date());
            projectToUpdate.get().setEnd_date(project.getEnd_date());
            projectToUpdate.get().setRepository_url(project.getRepository_url());
            projectToUpdate.get().setDemo_url(project.getDemo_url());
            projectToUpdate.get().setPicture(project.getPicture());
            projectToUpdate.get().setTechnologies(project.getTechnologies());
            projectToUpdate.get().setDevelopers(project.getDevelopers());
            projectRepository.save(projectToUpdate.get()); // Guarda el proyecto actualizado.
            return true;
        } else {
            return false; // Retorna false si no se encuentra el proyecto.
        }
    }

    // Método para mover un proyecto al estado de "testing".
    @Override
    public boolean moveProjectToTesting(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id); // Busca el proyecto por ID.
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(2); // Busca el estado con ID 2 (testing).
            if (stateOptional.isPresent()) {
                project.setStatusProject(stateOptional.get()); // Asigna el nuevo estado al proyecto.
                projectRepository.save(project); // Guarda el proyecto con el nuevo estado.
                isUpdated = true;
            } else {
                System.out.println("State with ID 2 does not exist.");
            }
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
        return isUpdated;
    }

    // Método para mover un proyecto al estado de "producción".
    @Override
    public boolean moveProjectToProduction(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id); // Busca el proyecto por ID.
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(3); // Busca el estado con ID 3 (producción).
            if (stateOptional.isPresent()) {
                project.setStatusProject(stateOptional.get()); // Asigna el nuevo estado al proyecto.
                projectRepository.save(project); // Guarda el proyecto con el nuevo estado.
                isUpdated = true;
            } else {
                System.out.println("State with ID 3 does not exist.");
            }
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
        return isUpdated;
    }

    // Método para encontrar un proyecto por su ID.
    @Override
    public Project findById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null); // Devuelve el proyecto encontrado o null si no existe.
    }

    // Método para obtener proyectos asociados con una tecnología específica.
    @Override
    public List<ProjectDTO> getProjectsByTechnology(String techName) {
        return projectRepository.findProjectsByTechnology(techName); // Obtiene los proyectos asociados a la tecnología.
    }
}
