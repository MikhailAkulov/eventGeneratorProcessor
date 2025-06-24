package ru.spring_boot_testTask.eventGeneratorProcessor.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.IncidentType;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.EventEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class IncidentEntity {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private IncidentType type;

    private LocalDateTime time;

//    @OneToMany(cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<EventEntity> sourceEvents;

}
