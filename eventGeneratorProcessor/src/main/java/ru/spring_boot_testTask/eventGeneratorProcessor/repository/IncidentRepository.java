package ru.spring_boot_testTask.eventGeneratorProcessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring_boot_testTask.eventGeneratorProcessor.model.IncidentEntity;

import java.util.UUID;

public interface IncidentRepository extends JpaRepository<IncidentEntity, UUID> {
}
