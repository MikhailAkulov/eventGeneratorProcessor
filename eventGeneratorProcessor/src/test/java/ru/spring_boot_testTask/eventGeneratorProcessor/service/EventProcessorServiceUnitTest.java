package ru.spring_boot_testTask.eventGeneratorProcessor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.EventType;
import ru.spring_boot_testTask.eventGeneratorProcessor.enums.IncidentType;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.EventEntity;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.IncidentEntity;
import ru.spring_boot_testTask.eventGeneratorProcessor.repository.EventRepository;
import ru.spring_boot_testTask.eventGeneratorProcessor.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class EventProcessorServiceUnitTest {

    private EventRepository eventRepository;
    private IncidentRepository incidentRepository;
    private EventProcessorService service;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        incidentRepository = mock(IncidentRepository.class);
        service = new EventProcessorService(eventRepository, incidentRepository);
    }

    @Test
    void testCreateSimpleIncidentFromType1() {
        EventEntity event = new EventEntity(UUID.randomUUID(), EventType.TYPE_1, LocalDateTime.now());

        when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));

        service.processEvent(event);

        ArgumentCaptor<IncidentEntity> captor = ArgumentCaptor.forClass(IncidentEntity.class);
        verify(incidentRepository).save(captor.capture());

        IncidentEntity incident = captor.getValue();
        assertThat(incident.getType()).isEqualTo(IncidentType.TYPE_1);
        assertThat(incident.getSourceEvents()).containsExactly(event);
    }

    @Test
    void testCreateCompositeIncidentFromType2ThenType1() {
        EventEntity type2 = new EventEntity(UUID.randomUUID(), EventType.TYPE_2, LocalDateTime.now().minusSeconds(10));
        EventEntity type1 = new EventEntity(UUID.randomUUID(), EventType.TYPE_1, LocalDateTime.now());

        when(eventRepository.findAll()).thenReturn(java.util.List.of(type2));

        service.processEvent(type2); // process first
        service.processEvent(type1); // process second

        ArgumentCaptor<IncidentEntity> captor = ArgumentCaptor.forClass(IncidentEntity.class);
        verify(incidentRepository).save(captor.capture());

        IncidentEntity incident = captor.getValue();
        assertThat(incident.getType()).isEqualTo(IncidentType.TYPE_2);
        assertThat(incident.getSourceEvents()).containsExactlyInAnyOrder(type2, type1);
    }
}
