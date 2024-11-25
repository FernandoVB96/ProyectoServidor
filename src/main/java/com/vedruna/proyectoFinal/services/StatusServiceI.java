package com.vedruna.proyectoFinal.services;

import com.vedruna.proyectoFinal.persistance.model.Status;

// Esta interfaz define el método necesario para interactuar con los estados en el sistema.
public interface StatusServiceI {

    // Método para encontrar un estado por su nombre.
    // Devuelve el objeto Status si se encuentra el estado, de lo contrario retorna null.
    public Status findByName(String name);
}
