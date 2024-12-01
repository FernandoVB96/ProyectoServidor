package com.vedruna.proyectoFinal.services;

import com.vedruna.proyectoFinal.persistance.model.Status;

public interface StatusServiceI {
    
    public Status findByName(String name);
}
