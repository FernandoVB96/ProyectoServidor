package com.vedruna.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

/**
 * Implementación del servicio para la gestión de proyectos.
 * Proporciona métodos para obtener, guardar, actualizar y eliminar proyectos, 
 * así como para cambiar el estado de los proyectos.
 */
@Service
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    private ProjectRepositoryI projectRepository;

    @Autowired
    private StatusRepositoryI stateRepository;

    /**
     * Obtiene todos los proyectos paginados.
     * 
     * @param page el número de página
     * @param size el tamaño de la página
     * @return una página de proyectos como DTOs
     */
    @Override
    public Page<ProjectDTO> showAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAll(pageable);
        return projectPage.map(ProjectDTO::new);
    }

    /**
     * Busca proyectos por su nombre.
     * 
     * @param name el nombre del proyecto
     * @return una lista de proyectos que contienen el nombre proporcionado
     * @throws IllegalArgumentException si no se encuentran proyectos
     */
    @Override
    public List<ProjectDTO> showProjectByName(String name) {
        List<Project> projects = projectRepository.findAll();
        List<Project> matchingProjects = new ArrayList<>();

        for (Project project : projects) {
            if (project.getName().contains(name)) {
                matchingProjects.add(project);
            }
        }

        if (matchingProjects.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron proyectos con el nombre: " + name);
        }

        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project p : matchingProjects) {
            projectDTOs.add(new ProjectDTO(p));
        }

        return projectDTOs;
    }

    /**
     * Guarda un proyecto en la base de datos.
     * 
     * @param project el proyecto a guardar
     */
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    /**
     * Elimina un proyecto por su ID.
     * 
     * @param id el ID del proyecto a eliminar
     * @return true si el proyecto fue eliminado, false si no se encuentra
     * @throws IllegalArgumentException si no existe un proyecto con ese ID
     */
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            projectRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("No existe un proyecto con el ID: " + id);
        }
    }

    /**
     * Actualiza los datos de un proyecto existente.
     * 
     * @param id el ID del proyecto a actualizar
     * @param project el proyecto con los nuevos datos
     * @return true si el proyecto fue actualizado, false si no se encuentra
     */
    public boolean updateProject(Integer id, Project project) {
        Optional<Project> projectToUpdate = projectRepository.findById(id);
        if (projectToUpdate.isPresent()) {
            Project existingProject = projectToUpdate.get();
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setStart_date(project.getStart_date());
            existingProject.setEnd_date(project.getEnd_date());
            existingProject.setRepository_url(project.getRepository_url());
            existingProject.setDemo_url(project.getDemo_url());
            existingProject.setPicture(project.getPicture());
            existingProject.setTechnologies(project.getTechnologies());
            existingProject.setDevelopers(project.getDevelopers());
            projectRepository.save(existingProject);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cambia el estado de un proyecto a "testing".
     * 
     * @param id el ID del proyecto
     * @return true si el estado fue cambiado, false si no se encuentra el proyecto
     */
    @Override
    public boolean moveProjectToTesting(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(2);
            if (stateOptional.isPresent()) {
                project.setStatusProject(stateOptional.get());
                projectRepository.save(project);
                isUpdated = true;
            }
        }
        return isUpdated;
    }

    /**
     * Cambia el estado de un proyecto a "producción".
     * 
     * @param id el ID del proyecto
     * @return true si el estado fue cambiado, false si no se encuentra el proyecto
     */
    @Override
    public boolean moveProjectToProduction(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        boolean isUpdated = false;
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(3);
            if (stateOptional.isPresent()) {
                project.setStatusProject(stateOptional.get());
                projectRepository.save(project);
                isUpdated = true;
            }
        }
        return isUpdated;
    }

    /**
     * Encuentra un proyecto por su ID.
     * 
     * @param projectId el ID del proyecto
     * @return el proyecto encontrado o null si no existe
     */
    @Override
    public Project findById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    /**
     * Obtiene los proyectos asociados a una tecnología específica.
     * 
     * @param techName el nombre de la tecnología
     * @return una lista de proyectos que utilizan esa tecnología
     */
    @Override
    public List<ProjectDTO> getProjectsByTechnology(String techName) {
        return projectRepository.findProjectsByTechnology(techName);
    }
}
