## Тестовое задание

---

### Цель:
Разработать систему генерации и обработки событий, с последующей генерацией и хранением инцидентов на основании их. 
(например, инциденты системы безопасности на основе событий от камер, датчиков и пр.)

* сервис 1: Генератор событий
* сервис 2: Процессор событий
* оба должны запускать автоматически и работать постоянно (т.е. BackgroundService-ы)

Описание сервисов:

1. Генератор.
- постоянно генерирует события в случайное время в 2-секундном (например) интервале 
(т.е. в случайный промежуток времени - [время предыдущего события; время предыдущего события + 2 сек] - 
должно быть сгенерировано новое событие)
- имеет REST API (swagger) в котором в любой момент можно сгенерировать событие вручную.
- каждый раз, когда генерируется новое событие, он отсылает его через HTTP request Процессору.

2. Процессор
- имеет REST API (swagger) через которое принимает события и создает на их основе инциденты.
- имеет БД в которую сохраняет созданные инциденты.
  - инциденты создаются на основе 2-х шаблонов: простого и составного
- получая события, проверяет их на соответствие шаблонам.
  - если событие соответствует шаблону - создается инцидент и записывается в БД.
- имеет в REST API (swagger) метод для получения списка созданных инцидентов (sorting и pagination - опциональны, но будет плюсом)
- при этом, в списке сгенерированных инцидентов для каждого инцидента должен выводится список событий на основе которых он был создан

Описание сущностей:
1. Событие:
     Event { Id: Guid, Type: EventTypeEnum: 1, 2, 3, 4 Time: DateTime (дата генерации события) }
2. Инцидент:
     Incident { Id: Guid, Type: IncidentTypeEnum: 1, 2 Time: DateTime (дата создания инцидента) }
3. Шаблон
   * Шаблон №1 (простой): если получено событие с Event.Type = 1 то создать инцидент 1 типа.
   * Шаблон №2 (составной): если получено событие с Event.Type = 2, а затем в течении 20 секунд (и не позже!) 
получено событие с Event.Type = 1, то создать инцидент с Incident.Type = 2, иначе создать инцидент Incident.Type = 1 
на основе события с с Event.Type = 1.
   * составной шаблон описывает инцидент в котором участвуют несколько событий, он имеет временную границу.
   * составной шаблон имеет приоритет, если событие соответствует составному шаблону, оно не участвует в простом шаблоне №1.

---
### Реализация:

Структура проекта:

`configuration`
* [RestTemplateConfig](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/configuration/RestTemplateConfig.java)
* [SwaggerConfig](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/configuration/SwaggerConfig.java)

`controller`
* [EventGeneratorController](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/controller/EventGeneratorController.java)
* [EventProcessorController](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/controller/EventProcessorController.java)

`dto`
* [EventDto](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/dto/EventDto.java)
* [IncidentDto](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/dto/IncidentDto.java)

`enum`
* [EventType](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/enums/EventType.java)
* [IncidentType](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/enums/IncidentType.java)

`model`
* [EventEntity](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/model/EventEntity.java)
* [IncidentEntity](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/model/IncidentEntity.java)

`repository`
* [EventRepository](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/repository/EventRepository.java)
* [IncidentRepository](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/repository/IncidentRepository.java)

`service`
* [EventGeneratorService](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/service/EventGeneratorService.java)
* [EventProcessorService](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/service/EventProcessorService.java)

`точка входа`
* [Application](https://github.com/MikhailAkulov/eventGeneratorProcessor/blob/main/eventGeneratorProcessor/src/main/java/ru/spring_boot_testTask/eventGeneratorProcessor/Application.java)
