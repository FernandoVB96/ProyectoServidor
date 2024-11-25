package com.vedruna.proyectoFinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinal.persistance.model.Status;
import com.vedruna.proyectoFinal.persistance.repository.StatusRepositoryI;

@Service // Indica que esta clase es un servicio gestionado por Spring
public class StatusServiceImpl implements StatusServiceI {

    @Autowired
    private StatusRepositoryI statusRepository; // Repositorio para interactuar con los estados

    // Método para encontrar un estado por su nombre.
    // Si el estado con el nombre proporcionado se encuentra, se retorna el objeto Status, 
    // de lo contrario, retorna null.
    public Status findByName(String name) {
        return statusRepository.findByName(name);
    }
}
