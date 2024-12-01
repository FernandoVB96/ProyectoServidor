package com.vedruna.proyectoFinal.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinal.dto.ProjectDTO;
import com.vedruna.proyectoFinal.dto.ResponseDTO;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.model.Status;
import com.vedruna.proyectoFinal.persistance.repository.StatusRepositoryI;
import com.vedruna.proyectoFinal.services.ProjectServiceI;

import jakarta.validation.Valid;

/**
 * Controlador que maneja las operaciones relacionadas con los proyectos.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceI projectService;
    @Autowired
    private StatusRepositoryI statusRepository;

    /**
     * Obtiene todos los proyectos paginados.
     * 
     * @param page número de página
     * @param size tamaño de la página
     * @return una lista de proyectos paginados
     */
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        return projectService.showAllProjects(page, size);
    }

    /**
     * Obtiene los proyectos por su nombre.
     * 
     * @param name nombre del proyecto
     * @return una respuesta con los proyectos encontrados
     */
    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> showProjectByName(@PathVariable String name) {
        List<ProjectDTO> projects = projectService.showProjectByName(name);
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Proyectos encontrados correctamente", projects);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo proyecto después de validar los datos.
     * 
     * @param projectDTO objeto que contiene los datos del nuevo proyecto
     * @param bindingResult resultados de la validación
     * @return una respuesta con el estado de la creación
     */
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult bindingResult) {
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                              .append(": ")
                              .append(error.getDefaultMessage())
                              .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LocalDate today = LocalDate.now();
        if (projectDTO.getStart_date().toLocalDate().isBefore(today)) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", "La fecha de inicio no puede ser anterior a hoy.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Status status = statusRepository.findByName(projectDTO.getStatusProjectName());
        if (status == null) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", "Nombre de estado no válido.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStart_date(projectDTO.getStart_date());
        project.setEnd_date(projectDTO.getEnd_date());
        project.setRepository_url(projectDTO.getRepository_url());
        project.setDemo_url(projectDTO.getDemo_url());
        project.setPicture(projectDTO.getPicture());
        project.setStatusProject(status);
        
        projectService.saveProject(project);
        ResponseDTO<Object> response = new ResponseDTO<>("Proyecto creado correctamente", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina un proyecto por su ID.
     * 
     * @param id ID del proyecto a eliminar
     * @return una respuesta con el estado de la eliminación
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            throw new IllegalArgumentException("No se encontró un proyecto con el ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Proyecto eliminado correctamente", "Proyecto con ID " + id + " eliminado.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Actualiza un proyecto existente por su ID.
     * 
     * @param id ID del proyecto a actualizar
     * @param project objeto con los datos del proyecto actualizado
     * @param bindingResult resultados de la validación
     * @return una respuesta con el estado de la actualización
     */
    @PutMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                             .append(": ")
                             .append(error.getDefaultMessage())
                             .append("\n")
            );
            
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        boolean projectUpdated = projectService.updateProject(id, project);
        if (!projectUpdated) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "No se encontró un proyecto con el ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<Object> response = new ResponseDTO<>("Proyecto actualizado correctamente", project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Mueve un proyecto al estado "Testing".
     * 
     * @param id ID del proyecto
     * @return una respuesta con el estado del cambio
     */
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToTesting(id);
            if (result) {
                return ResponseEntity.ok("Proyecto movido a Testing correctamente");
            } else if (projectService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se movieron proyectos a Testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mover el proyecto a Testing: " + e.getMessage());
        }
    }

    /**
     * Mueve un proyecto al estado "Production".
     * 
     * @param id ID del proyecto
     * @return una respuesta con el estado del cambio
     */
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToProduction(id);
            if (result) {
                return ResponseEntity.ok("Proyecto movido a Producción correctamente");
            } else if (projectService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se movieron proyectos a Producción");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mover el proyecto a Producción: " + e.getMessage());
        }
    }

    /**
     * Obtiene los proyectos que utilizan una tecnología específica.
     * 
     * @param tech nombre de la tecnología
     * @return una lista de proyectos que utilizan esa tecnología
     */
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        List<ProjectDTO> projects = projectService.getProjectsByTechnology(tech);
        if (projects.isEmpty()) {
            ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("No se encontraron proyectos con esta tecnología", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Proyectos encontrados correctamente", projects);
        return ResponseEntity.ok(response);
    }
}
