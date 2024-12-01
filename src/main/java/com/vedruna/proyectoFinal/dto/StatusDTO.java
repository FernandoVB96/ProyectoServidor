package com.vedruna.proyectoFinal.dto;

import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {

    private int id;
    private String name;
    private List<ProjectDTO> statusWithProject;

    /**
     * Constructor que convierte un objeto Status en un StatusDTO.
     *
     * @param s el objeto Status a convertir
     */
    public StatusDTO(Status s) {
        this.id = s.getId();
        this.name = s.getName();
        this.statusWithProject = convertProjectsToDTO(s.getStatusWithProject());
    }

    /**
     * Convierte una lista de objetos Project en una lista de objetos ProjectDTO.
     *
     * @param projects la lista de proyectos a convertir
     * @return una lista de ProjectDTOs
     */
    private List<ProjectDTO> convertProjectsToDTO(List<com.vedruna.proyectoFinal.persistance.model.Project> projects) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        if (projects != null) {
            for (Project p : projects) {
                projectDTOList.add(new ProjectDTO(p));
            }
        }
        return projectDTOList;
    }
}
