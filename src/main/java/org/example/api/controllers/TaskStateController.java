package org.example.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.api.controllers.helpers.ControllerHelper;
import org.example.api.dto.AckDto;
import org.example.api.dto.TaskStateDto;
import org.example.api.exceptions.BadRequestException;
import org.example.api.exceptions.NotFoundException;
import org.example.api.factories.TaskStateDtoFactory;
import org.example.store.entities.ProjectEntity;
import org.example.store.entities.TaskStateEntity;
import org.example.store.repositories.TaskStateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@RestController
public class TaskStateController {

    private final TaskStateRepository taskStateRepository;

    private final TaskStateDtoFactory taskStateDtoFactory;

    private final ControllerHelper controllerHelper;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    public static final String EDIT_TASK_STATE = "/api/task-states/{task_states_id}";
    public static final String DELETE_TASK_STATE = "/api//task-states/{task_states_id}";

    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskStates(@PathVariable("project_id") Long projectId) {

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        return project
                .getTaskStates()
                .stream()
                .map(taskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDto createTaskState(
            @PathVariable("project_id") Long projectId,
            @RequestParam("task_state_name") String taskStateName
    ) {
        if (taskStateName.trim().isEmpty()) {
            throw new BadRequestException("Task state name required");
        }

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);
        ensureUniqueTaskStateNameOrThrowException(project, taskStateName);

        Long countTaskStates = project.getTaskStates().stream().count();

        TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                TaskStateEntity.builder()
                        .name(taskStateName)
                        .orderliness(countTaskStates)
                        .build()
        );

        return taskStateDtoFactory.makeTaskStateDto(taskState);
    }

    @PatchMapping(EDIT_TASK_STATE)
    public TaskStateDto editTaskState(
            @PathVariable("project_id") Long projectId,
            @PathVariable("task_states_id") Long taskStatesId,
            @RequestParam("task_state_name") String taskStateName) {

        if (taskStateName.isBlank()) {
            throw new BadRequestException("Task state name is required");
        }

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);
        ensureUniqueTaskStateNameOrThrowException(project, taskStateName);

        TaskStateEntity taskState = taskStateRepository
                .findById(taskStatesId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Task state with \"%s\" doesn't exists.", taskStatesId))
                );

        taskState.setName(taskStateName);

        return taskStateDtoFactory.makeTaskStateDto(taskState);
    }

    @DeleteMapping(DELETE_TASK_STATE)
    public AckDto deleteTaskState(@PathVariable("task_states_id") Long taskStatesId) {
        taskStateRepository.deleteById(taskStatesId);

        return new AckDto(true);
    }

    private void ensureUniqueTaskStateNameOrThrowException(ProjectEntity project, String taskStateName) {
        project
                .getTaskStates()
                .stream()
                .map(TaskStateEntity::getName)
                .filter(name -> name.equals(taskStateName))
                .findAny()
                .ifPresent(it -> {
                    throw new BadRequestException(String.format("Task state with \"%s\" name already exists", it));
                });
    }
}
