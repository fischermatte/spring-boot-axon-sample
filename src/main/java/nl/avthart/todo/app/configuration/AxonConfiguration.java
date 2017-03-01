package nl.avthart.todo.app.configuration;

import nl.avthart.todo.app.domain.task.Task;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static nl.avthart.todo.app.configuration.Profiles.LOCAL;

/**
 * Axon Java Configuration with inmemory eventsourcing repository.
 *
 * @author albert
 * @author fischermatte
 */
@Configuration
public class AxonConfiguration {

    @Profile(LOCAL)
    @Bean
    public EventStorageEngine eventStorageEngine(){
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public EventSourcingRepository<Task> taskRepository(EventStore eventStore) {
        return new EventSourcingRepository<Task>(Task.class, eventStore);
    }

    @Bean
    public AggregateAnnotationCommandHandler<Task> taskCommandHandler(CommandBus commandBus, EventSourcingRepository<Task> taskRepository) {
        AggregateAnnotationCommandHandler<Task> taskCommandHandler = new AggregateAnnotationCommandHandler<Task>(Task.class, taskRepository);
        taskCommandHandler.subscribe(commandBus);
        return taskCommandHandler;
    }
}
