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
import com.vedruna.proyectoFinal.services.StatusServiceI;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceI projectService;
    @Autowired
    private StatusRepositoryI statusRepository;

    // Endpoint para obtener todos los proyectos paginados
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        return projectService.showAllProjects(page, size);
    }
    
    // Endpoint para obtener un proyecto por su nombre
    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> showProjectByName(@PathVariable String name) {
        ProjectDTO project = projectService.showProjectByName(name);
        ResponseDTO<ProjectDTO> response = new ResponseDTO<>("Project found successfully", project);
        return ResponseEntity.ok(response);
    }

    // Endpoint para crear un nuevo proyecto, con validación de fecha y estado
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                              .append(": ")
                              .append(error.getDefaultMessage())
                              .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LocalDate today = LocalDate.now();
        if (projectDTO.getStart_date().toLocalDate().isBefore(today)) {
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "The start date cannot be before today.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Status status = statusRepository.findByName(projectDTO.getStatusProjectName());
        if (status == null) {
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "Invalid status name provided.");
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
        project.setStatusProject(status); // Asignar el estado

        projectService.saveProject(project);
        ResponseDTO<Object> response = new ResponseDTO<>("Project created successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para eliminar un proyecto por su ID
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            throw new IllegalArgumentException("There isn't any project with the ID:" + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Project deleted successfully", "Project with ID " + id + " deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para actualizar un proyecto existente
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
            
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        boolean projectUpdated = projectService.updateProject(id, project);
        if (!projectUpdated) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "There isn't any project with the ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<Object> response = new ResponseDTO<>("Project updated successfully", project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para mover un proyecto al estado "Testing"
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToTesting(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to testing successfully");
            } else if (projectService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to testing: " + e.getMessage());
        }
    }
    
    // Endpoint para mover un proyecto al estado "Production"
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToProduction(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to production successfully");
            } else if (projectService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to production");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to production: " + e.getMessage());
        }
    }

    // Endpoint para obtener proyectos por tecnología
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<Project>>> getProjectsByTechnology(@PathVariable String tech) {
        List<Project> projects = projectService.getProjectsByTechnology(tech);
        if (projects.isEmpty()) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>("No projects found with this technology", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponseDTO<List<Project>> response = new ResponseDTO<>("Projects found successfully", projects);
        return ResponseEntity.ok(response);
    }
}
