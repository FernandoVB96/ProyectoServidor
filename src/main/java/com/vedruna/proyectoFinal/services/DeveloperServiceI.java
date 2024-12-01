package com.vedruna.proyectoFinal.services;

import com.vedruna.proyectoFinal.persistance.model.Developer;

public interface DeveloperServiceI {

    void saveDeveloper(Developer developer);
    boolean deleteDeveloper(Integer id);
    Developer findById(Integer developerId);
} 
