package org.example.api.factories;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.TaskStateDto;
import org.example.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskStateDtoFactory {

    private final TaskDtoFactory taskDtoFactory;

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {
        return new TaskStateDto(
                entity.getId(),
                entity.getName(),
                entity.getOrderliness(),
                entity.getCreatedAt(),
                entity.getTasks()
                        .stream()
                        .map(taskDtoFactory::makeTaskDto)
                        .collect(Collectors.toList())
        );
    }
}
