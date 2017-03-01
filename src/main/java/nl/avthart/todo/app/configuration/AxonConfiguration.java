package nl.avthart.todo.app.configuration;

import nl.avthart.todo.app.domain.task.Task;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.spring.config.EnableAxon;
import org.axonframework.spring.config.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static nl.avthart.todo.app.configuration.Profiles.LOCAL;

/**
 * Axon Java Configuration with reasonable defaults like SimpleCommandBus, SimpleEventBus and GenericJpaRepository.
 *
 * @author albert
 */
@EnableAxon
@Configuration
public class AxonConfiguration {

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        return new AnnotationCommandHandlerBeanPostProcessor();
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
        commandBus.registerHandlerInterceptor(new BeanValidationInterceptor());
        return commandBus;
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
        factory.setCommandBus(commandBus());
        return factory;
    }

    @Bean
    public EventSourcingRepository<Task> taskRepository() {
        return new EventSourcingRepository<Task>(Task.class, eventStore());
    }

    @Profile(LOCAL)
    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }

    @Bean
    public AggregateAnnotationCommandHandler<Task> taskCommandHandler() {
        AggregateAnnotationCommandHandler<Task> taskCommandHandler = new AggregateAnnotationCommandHandler<Task>(Task.class, taskRepository());
        taskCommandHandler.subscribe(commandBus());
        return taskCommandHandler;
    }
}
