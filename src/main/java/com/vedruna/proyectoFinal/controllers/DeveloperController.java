package com.vedruna.proyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinal.dto.DeveloperDTO;
import com.vedruna.proyectoFinal.dto.ResponseDTO;
import com.vedruna.proyectoFinal.persistance.model.Developer;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.services.DeveloperServiceI;
import com.vedruna.proyectoFinal.services.ProjectServiceI;

/**
 * Controlador para manejar las operaciones relacionadas con los desarrolladores.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {

    @Autowired
    private DeveloperServiceI developerService;

    @Autowired
    private ProjectServiceI projectService;

    /**
     * Método para crear un nuevo desarrollador.
     * 
     * @param developerDTO el objeto Developer a ser guardado
     * @return ResponseEntity con el mensaje de éxito y estado HTTP 201
     */
    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody DeveloperDTO developerDTO) {
    Developer developer = new Developer();
    developer.setName(developerDTO.getName());
    developer.setSurname(developerDTO.getSurname());
    developer.setEmail(developerDTO.getEmail());
    developer.setLinkedin_url(developerDTO.getLinkedin_url());
    developer.setGithub_url(developerDTO.getGithub_url());

    developerService.saveDeveloper(developer);
    ResponseDTO<String> response = new ResponseDTO<>("Desarrollador creado con éxito", null);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

    /**
     * Método para eliminar un desarrollador por su ID.
     * 
     * @param id el ID del desarrollador a eliminar
     * @return ResponseEntity con el mensaje de éxito y estado HTTP 200
     * @throws IllegalArgumentException si el desarrollador no existe
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        boolean developerDeleted = developerService.deleteDeveloper(id);
        if (!developerDeleted) {
            throw new IllegalArgumentException("No existe un desarrollador con el ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Desarrollador eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Método para asignar un desarrollador a un proyecto.
     * 
     * @param developerId el ID del desarrollador a añadir
     * @param projectId el ID del proyecto al que se agregará el desarrollador
     * @return ResponseEntity con el mensaje de éxito o error según corresponda
     */
    @PostMapping("/developers/worked/{developerId}/{projectId}")
    public ResponseEntity<ResponseDTO<String>> addDeveloperToProject(@PathVariable int developerId, @PathVariable int projectId) {
        Developer developer = developerService.findById(developerId);
        Project project = projectService.findById(projectId);
        
        if (developer == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>("Desarrollador no encontrado", null));
        }
        
        if (project == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>("Proyecto no encontrado", null));
        }
    
        if (!project.getDevelopers().contains(developer)) {
            project.getDevelopers().add(developer);
            developer.getProjectsDevelopers().add(project);
            projectService.saveProject(project);
            developerService.saveDeveloper(developer);  
        }
    
        return ResponseEntity.ok(new ResponseDTO<>("Desarrollador añadido al proyecto", null));
    }
}
