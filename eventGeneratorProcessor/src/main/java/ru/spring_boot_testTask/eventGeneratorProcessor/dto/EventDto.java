package ru.spring_boot_testTask.eventGeneratorProcessor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Событие, получаемое или генерируемое системой")
public record EventDto(
        @Schema(description = "Уникальный идентификатор события", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Тип события", example = "TYPE_1")
        EventType type,

        @Schema(description = "Время генерации события", example = "2025-06-24T15:30:00")
        LocalDateTime time) {
}
