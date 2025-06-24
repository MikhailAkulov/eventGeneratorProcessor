package ru.spring_boot_testTask.eventGeneratorProcessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.EventEntity;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
}
