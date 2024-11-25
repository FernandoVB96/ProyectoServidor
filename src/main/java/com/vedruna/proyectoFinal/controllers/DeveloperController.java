package com.vedruna.proyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vedruna.proyectoFinal.dto.ResponseDTO;
import com.vedruna.proyectoFinal.persistance.model.Developer;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.services.DeveloperServiceI;
import com.vedruna.proyectoFinal.services.ProjectServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {

    @Autowired
    private DeveloperServiceI developerService;

    @Autowired
    private ProjectServiceI projectService;

    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody Developer developer) {
        // Guarda el nuevo desarrollador en la base de datos
        developerService.saveDeveloper(developer);
        ResponseDTO<String> response = new ResponseDTO<>("Developer created successfully", null);
        // Devuelve la respuesta con un estado HTTP 201 (Created)
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        // Intenta eliminar el desarrollador por ID
        boolean developerDeleted = developerService.deleteDeveloper(id);
        if (!developerDeleted) {
            // Si no se encuentra el desarrollador, lanza una excepción
            throw new IllegalArgumentException("There isn't a developer with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Developer successfully removed", null);
        // Devuelve la respuesta con un estado HTTP 200 (OK)
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/developers/worked/{developerId}/{projectId}")
    public ResponseEntity<?> addDeveloperToProject(@PathVariable int developerId, @PathVariable int projectId) {
        // Busca al desarrollador por su ID
        Developer developer = developerService.findById(developerId);
        // Busca el proyecto por su ID
        Project project = projectService.findById(projectId);
        
        if (developer == null) {
            // Si no se encuentra al desarrollador, devuelve un error 400
            return ResponseEntity.badRequest().body("Developer not found");
        }
        
        if (project == null) {
            // Si no se encuentra el proyecto, devuelve un error 400
            return ResponseEntity.badRequest().body("Project not found");
        }

        // Si el proyecto no tiene al desarrollador, lo agrega
        if (!project.getDevelopers().contains(developer)) {
            project.getDevelopers().add(developer);
            developer.getProjectsDevelopers().add(project);
            // Guarda el proyecto con el desarrollador añadido
            projectService.saveProject(project);
            // Guarda al desarrollador con el proyecto asignado
            developerService.saveDeveloper(developer);  
        }
    
        // Devuelve un mensaje de éxito
        return ResponseEntity.ok("Developer added to project");
    }
}
