package com.vedruna.proyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vedruna.proyectoFinal.dto.ResponseDTO;
import com.vedruna.proyectoFinal.persistance.model.Technology;
import com.vedruna.proyectoFinal.services.TechnologyServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyServiceI technologyService;

    // Endpoint para crear una nueva tecnología
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody Technology technology) {
        try {
            technologyService.saveTechnology(technology); // Guarda la tecnología
            return ResponseEntity.status(HttpStatus.CREATED).body("Technology created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para eliminar una tecnología por su ID
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        boolean technologyDeleted = technologyService.deleteTechnology(id);
        if (!technologyDeleted) {
            throw new IllegalArgumentException("There isn't a technology with the ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Technology successfully removed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para asociar una tecnología con un proyecto
    @PostMapping("/technologies/used/{projectId}/{technologyId}")
    public ResponseEntity<String> associateTechnologyWithProject(@PathVariable int projectId, @PathVariable int technologyId) {
        try {
            technologyService.associateTechnologyWithProject(projectId, technologyId); // Asocia la tecnología con el proyecto
            return ResponseEntity.status(HttpStatus.OK).body("Technology associated with project successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
