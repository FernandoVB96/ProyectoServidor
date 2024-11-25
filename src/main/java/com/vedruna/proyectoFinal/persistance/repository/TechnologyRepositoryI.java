package com.vedruna.proyectoFinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vedruna.proyectoFinal.persistance.model.Technology;

public interface TechnologyRepositoryI extends JpaRepository<Technology, Integer> {
    
    
} 
