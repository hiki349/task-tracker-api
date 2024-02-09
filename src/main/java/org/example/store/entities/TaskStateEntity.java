package org.example.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_states")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Integer ordinal;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_states_id", referencedColumnName = "id")
    private List<TaskEntity> tasks = new ArrayList<>();
}
