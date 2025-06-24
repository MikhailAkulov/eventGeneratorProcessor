package ru.spring_boot_testTask.eventGeneratorProcessor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.IncidentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Инцидент, созданный на основе одного или нескольких событий")
public record IncidentDto(
        @Schema(description = "Уникальный идентификатор инцидента", example = "c91f8ad7-7b0c-4f30-bbe9-6e6e78177bd9")
        UUID id,

        @Schema(description = "Тип инцидента: 1 — простой, 2 — составной", example = "TYPE_2")
        IncidentType type,

        @Schema(description = "Время создания инцидента", example = "2025-06-24T15:35:12")
        LocalDateTime time,

        @Schema(description = "Список событий, на основе которых создан инцидент")
        List<EventDto> events) {
}
