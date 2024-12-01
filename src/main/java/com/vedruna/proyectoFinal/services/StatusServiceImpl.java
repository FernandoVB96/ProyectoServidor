package com.vedruna.proyectoFinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinal.persistance.model.Status;
import com.vedruna.proyectoFinal.persistance.repository.StatusRepositoryI;

@Service
public class StatusServiceImpl implements StatusServiceI {

    @Autowired
    private StatusRepositoryI statusRepository;

    public Status findByName(String name) {
        return statusRepository.findByName(name);
    }
}
