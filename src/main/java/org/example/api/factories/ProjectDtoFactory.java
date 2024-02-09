package org.example.api.factories;

import org.example.api.dto.ProjectDto;
import org.example.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity) {

        return new ProjectDto(
                entity.getId(), entity.getName(), entity.getCreatedAt()
        );
    }
}
