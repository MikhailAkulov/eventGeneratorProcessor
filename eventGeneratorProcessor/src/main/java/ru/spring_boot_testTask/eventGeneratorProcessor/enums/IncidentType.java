package ru.spring_boot_testTask.eventGeneratorProcessor.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Типы инцидентов, создаваемых на основе событий")
public enum IncidentType {
    @Schema(description = "Тип 1 — инцидент на основе одиночного события")
    TYPE_1,

    @Schema(description = "Тип 2 — составной инцидент, основанный на нескольких событиях в интервале")
    TYPE_2
}
