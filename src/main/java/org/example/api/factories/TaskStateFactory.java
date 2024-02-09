package org.example.api.factories;

import org.example.api.dto.TaskStateDto;
import org.example.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
        return new TaskStateDto(
                entity.getId(), entity.getName(), entity.getOrdinal(), entity.getCreatedAt()
        );
    }
}
