package ru.spring_boot_testTask.eventGeneratorProcessor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class EventEntity {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private LocalDateTime time;

    public EventEntity(UUID id, EventType type, LocalDateTime time) {
        this.id = id;
        this.type = type;
        this.time = time;
    }

    public EventEntity() {
    }
}
