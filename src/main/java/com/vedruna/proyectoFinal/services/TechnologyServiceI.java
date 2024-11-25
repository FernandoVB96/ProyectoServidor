package com.vedruna.proyectoFinal.services;

import com.vedruna.proyectoFinal.persistance.model.Technology;

// Esta interfaz define los métodos necesarios para interactuar con las tecnologías en el sistema.
public interface TechnologyServiceI {

    // Método para guardar una nueva tecnología en la base de datos.
    void saveTechnology(Technology technology);

    // Método para eliminar una tecnología por su ID. 
    // Devuelve true si la eliminación fue exitosa.
    boolean deleteTechnology(Integer id);

    // Método para buscar una tecnología por su ID.
    // Devuelve la tecnología encontrada, o null si no existe.
    Technology findById(Integer techId);

    // Método para asociar una tecnología con un proyecto, utilizando los IDs de ambos.
    void associateTechnologyWithProject(int projectId, int technologyId);
}
