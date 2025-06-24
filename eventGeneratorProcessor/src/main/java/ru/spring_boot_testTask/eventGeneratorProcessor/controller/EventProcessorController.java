package ru.spring_boot_testTask.eventGeneratorProcessor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.spring_boot_testTask.eventGeneratorProcessor.dto.EventDto;
import ru.spring_boot_testTask.eventGeneratorProcessor.dto.IncidentDto;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.EventEntity;
import ru.spring_boot_testTask.eventGeneratorProcessor.service.EventProcessorService;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.IncidentEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/processor")
@RequiredArgsConstructor
@Tag(name = "Процессор событий", description = "Прием событий и генерация инцидентов")
public class EventProcessorController {

    private final EventProcessorService eventProcessorService;

    @PostMapping("/event")
    @Operation(
            summary = "Прием события",
            description = "Принимает событие от генератора и обрабатывает его для возможного создания инцидента"
    )
    public void receiveEvent(@RequestBody EventDto dto) {
        EventEntity entity = new EventEntity();
        entity.setId(dto.id());
        entity.setTime(dto.time());
        entity.setType(dto.type());
        eventProcessorService.processEvent(entity);
    }

    @GetMapping("/incidents")
    @Operation(
            summary = "Получить список инцидентов",
            description = "Возвращает список созданных инцидентов с возможностью сортировки и пагинации"
    )
    public List<IncidentDto> getIncidents(
            @Parameter(description = "Номер страницы") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Поле сортировки") @RequestParam(defaultValue = "time") String sortBy,
            @Parameter(description = "Направление сортировки") @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<IncidentEntity> pageResult = eventProcessorService.getIncidents(page, size, sortBy, direction);
        return pageResult.getContent().stream().map(incident -> new IncidentDto(
                incident.getId(),
                incident.getType(),
                incident.getTime(),
                incident.getSourceEvents().stream()
                        .map(ev -> new EventDto(ev.getId(), ev.getType(), ev.getTime()))
                        .collect(Collectors.toList())
        )).toList();
    }
}
