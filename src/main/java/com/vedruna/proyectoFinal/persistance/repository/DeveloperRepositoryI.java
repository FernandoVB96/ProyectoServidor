package com.vedruna.proyectoFinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vedruna.proyectoFinal.persistance.model.Developer;

public interface DeveloperRepositoryI extends JpaRepository<Developer, Integer> {
    
    
} 
