package org.example.api.factories;

import org.example.api.dto.TaskDto;

import org.example.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity) {
        return new TaskDto(
                entity.getId(), entity.getName(), entity.getDescription()
        );
    }
}
