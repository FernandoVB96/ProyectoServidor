package com.vedruna.proyectoFinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vedruna.proyectoFinal.persistance.model.Status;

public interface StatusRepositoryI extends JpaRepository<Status, Integer> {
    Status findByName(String name);
    
} 
