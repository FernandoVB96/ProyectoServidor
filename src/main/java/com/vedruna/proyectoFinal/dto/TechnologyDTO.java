package com.vedruna.proyectoFinal.dto;

import com.vedruna.proyectoFinal.persistance.model.Technology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDTO{


    private int id;

    private String name;


    public TechnologyDTO(Technology t) {
        this.id = t.getId();
        this.name = t.getName();
    }
    
}
