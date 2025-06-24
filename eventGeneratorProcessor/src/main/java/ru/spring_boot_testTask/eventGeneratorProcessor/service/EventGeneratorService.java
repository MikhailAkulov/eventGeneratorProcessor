package ru.spring_boot_testTask.eventGeneratorProcessor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.spring_boot_testTask.eventGeneratorProcessor.dto.EventDto;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.EventType;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventGeneratorService {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    private final String processorUrl = "http://localhost:8080/api/processor/event";

    public EventDto generateManualEvent() {
        EventDto dto = generateEvent();
        sendEvent(dto);
        return dto;
    }

    @Scheduled(fixedDelay = 2000)
    public void generateScheduledEvent() throws InterruptedException {
        Thread.sleep(random.nextInt(2000));
        EventDto dto = generateEvent();
        sendEvent(dto);
    }

    private EventDto generateEvent() {
        UUID id = UUID.randomUUID();
        EventType type = EventType.values()[random.nextInt(EventType.values().length)];
        LocalDateTime time = LocalDateTime.now();
        return new EventDto(id, type, time);
    }

    private void sendEvent(EventDto dto) {
        restTemplate.postForObject(processorUrl, dto, Void.class);
    }
}
