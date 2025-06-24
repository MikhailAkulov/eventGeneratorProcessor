package ru.spring_boot_testTask.eventGeneratorProcessor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.EventType;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.IncidentType;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.EventEntity;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.IncidentEntity;
import ru.spring_boot_testTask.eventGeneratorProcessor.repository.EventRepository;
import ru.spring_boot_testTask.eventGeneratorProcessor.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventProcessorService {

    private final EventRepository eventRepository;
    private final IncidentRepository incidentRepository;

    public void processEvent(EventEntity event) {
        eventRepository.save(event);

        if (event.getType() == EventType.TYPE_1) {
            // проверим наличие события TYPE_2 за последние 20 секунд
            LocalDateTime from = event.getTime().minusSeconds(20);
            List<EventEntity> recentEvents = eventRepository.findAll();
            for (EventEntity e : recentEvents) {
                if (e.getType() == EventType.TYPE_2 &&
                        !e.getId().equals(event.getId()) &&
                        e.getTime().isAfter(from) &&
                        e.getTime().isBefore(event.getTime())) {

                    IncidentEntity incident = new IncidentEntity();
                    incident.setId(UUID.randomUUID());
                    incident.setType(IncidentType.TYPE_2);
                    incident.setTime(LocalDateTime.now());
                    incident.setSourceEvents(List.of(e, event));
                    incidentRepository.save(incident);
                    return;
                }
            }

            // если не нашли TYPE_2, то TYPE_1 инцидент
            IncidentEntity incident = new IncidentEntity();
            incident.setId(UUID.randomUUID());
            incident.setType(IncidentType.TYPE_1);
            incident.setTime(LocalDateTime.now());
            incident.setSourceEvents(List.of(event));
            incidentRepository.save(incident);

        } else if (event.getType() == EventType.TYPE_2) {
            // TYPE_2 сам по себе ничего не вызывает
        }
    }

    public Page<IncidentEntity> getIncidents(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return incidentRepository.findAll(pageable);
    }
}
