package ru.spring_boot_testTask.eventGeneratorProcessor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring_boot_testTask.eventGeneratorProcessor.dto.EventDto;
import ru.spring_boot_testTask.eventGeneratorProcessor.service.EventGeneratorService;

@RestController
@RequestMapping("/api/generator")
@RequiredArgsConstructor
@Tag(name = "Генератор событий", description = "REST API для генерации событий")
public class EventGeneratorController {

    private final EventGeneratorService eventGeneratorService;

    @GetMapping("/generate")
    @Operation(
            summary = "Сгенерировать событие вручную",
            description = "Генерирует одно событие случайного типа и времени вручную"
    )
    public EventDto generateEventManually() {
        return eventGeneratorService.generateManualEvent();
    }
}
