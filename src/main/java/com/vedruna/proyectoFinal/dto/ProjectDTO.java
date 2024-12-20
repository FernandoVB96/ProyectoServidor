package com.vedruna.proyectoFinal.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinal.persistance.model.Developer;
import com.vedruna.proyectoFinal.persistance.model.Project;
import com.vedruna.proyectoFinal.persistance.model.Technology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa un proyecto con sus detalles,
 * incluyendo tecnologías y desarrolladores asociados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String repository_url;
    private String demo_url;
    private String picture;
    private String statusProjectName;
    private List<TechnologyDTO> technologies;
    private List<DeveloperDTO> developers;

    /**
     * Constructor que crea un ProjectDTO a partir de un objeto Project.
     * 
     * @param p el objeto Project que se convertirá a ProjectDTO
     */
    public ProjectDTO(Project p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.start_date = p.getStart_date();
        this.end_date = p.getEnd_date();
        this.repository_url = p.getRepository_url();
        this.demo_url = p.getDemo_url();
        this.picture = p.getPicture();
        this.statusProjectName = p.getStatusProject() != null ? p.getStatusProject().getName() : null;
        this.technologies = convertTechnologiesToDTO(p.getTechnologies());
        this.developers = convertDevelopersToDTO(p.getDevelopers());
    }

    /**
     * Convierte una lista de objetos Technology en una lista de objetos TechnologyDTO.
     *
     * @param technologies la lista de objetos Technology que se va a convertir
     * @return una lista de objetos TechnologyDTO
     */
    public List<TechnologyDTO> convertTechnologiesToDTO(List<Technology> technologies) {
        List<TechnologyDTO> technologyDTOList = new ArrayList<>();
        for (Technology t : technologies) {
            technologyDTOList.add(new TechnologyDTO(t));
        }
        return technologyDTOList;
    }

    /**
     * Convierte una lista de objetos Developer en una lista de objetos DeveloperDTO,
     * listos para ser serializados en formato JSON.
     * 
     * @param developers la lista de objetos Developer que se convertirá
     * @return una lista de objetos DeveloperDTO
     */
    public List<DeveloperDTO> convertDevelopersToDTO(List<Developer> developers) {
        List<DeveloperDTO> developerDTOList = new ArrayList<>();
        for (Developer d : developers) {
            developerDTOList.add(new DeveloperDTO(d));
        }
        return developerDTOList;
    }
}
