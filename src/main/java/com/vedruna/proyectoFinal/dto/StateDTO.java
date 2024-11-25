package com.vedruna.proyectoFinal.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.vedruna.proyectoFinal.persistance.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {

  
    private int id;
    private String name;
    private List<ProjectDTO> statusWithProject;

    public StateDTO(Status s) {
        this.id = s.getId();
        this.name = s.getName();
        this.statusWithProject = s.getStatusWithProject() != null ? s.getStatusWithProject().stream()
            .map(ProjectDTO::new)
            .collect(Collectors.toList()) : null;
    }
    
}
