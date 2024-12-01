package com.vedruna.proyectoFinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinal.dto.ResponseDTO;
import com.vedruna.proyectoFinal.dto.TechnologyDTO;
import com.vedruna.proyectoFinal.persistance.model.Technology;
import com.vedruna.proyectoFinal.services.TechnologyServiceI;

/**
 * Controlador que maneja las solicitudes relacionadas con las tecnologías.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyServiceI technologyService;

    /**
     * Crea una nueva tecnología en el sistema.
     * 
     * @param technologyDTO el DTO que contiene la información de la tecnología a crear
     * @return una respuesta HTTP indicando el resultado de la operación
     */
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody TechnologyDTO technologyDTO) {
        try {
            // Convertir TechnologyDTO a Technology
            Technology technology = new Technology();
            technology.setId(technologyDTO.getId());  // Asigna el ID
            technology.setName(technologyDTO.getName());

            // Guardar la tecnología usando el servicio
            technologyService.saveTechnology(technology);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tecnología creada con éxito");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Elimina una tecnología por su ID.
     * 
     * @param id el ID de la tecnología a eliminar
     * @return una respuesta HTTP con el resultado de la operación
     */
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        boolean technologyDeleted = technologyService.deleteTechnology(id);
        if (!technologyDeleted) {
            throw new IllegalArgumentException("No se encuentra una tecnología con el ID: " + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Tecnología eliminada correctamente", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Asocia una tecnología con un proyecto.
     * 
     * @param projectId el ID del proyecto
     * @param technologyId el ID de la tecnología a asociar
     * @return una respuesta HTTP con el resultado de la operación
     */
    @PostMapping("/technologies/used/{projectId}/{technologyId}")
    public ResponseEntity<String> associateTechnologyWithProject(@PathVariable int projectId, @PathVariable int technologyId) {
        try {
            technologyService.associateTechnologyWithProject(projectId, technologyId);
            return ResponseEntity.status(HttpStatus.OK).body("Tecnología asociada al proyecto correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
