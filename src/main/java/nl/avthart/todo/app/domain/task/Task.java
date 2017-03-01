package nl.avthart.todo.app.domain.task;

import nl.avthart.todo.app.domain.task.commands.*;
import nl.avthart.todo.app.domain.task.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import javax.validation.constraints.NotNull;

/**
 * Task
 *
 * @author albert
 */
public class Task extends AbstractAnnotatedAggregateRoot<String> {

    /**
     * The constant serialVersionUID
     */
    private static final long serialVersionUID = -5977984483620451665L;

    @AggregateIdentifier
    private String id;

    @NotNull
    private boolean completed;

    /**
     * Creates a new Task.
     *
     * @param command create Task
     */
    @CommandHandler
    public Task(CreateTaskCommand command) {
        apply(new TaskCreatedEvent(command.getId(), command.getUsername(), command.getTitle()));
    }

    Task() {
    }

    /**
     * Completes a Task.
     *
     * @param command complete Task
     */
    @CommandHandler
    void on(CompleteTaskCommand command) {
        apply(new TaskCompletedEvent(command.getId()));
    }

    /**
     * Stars a Task.
     *
     * @param command star Task
     */
    @CommandHandler
    void on(StarTaskCommand command) {
        apply(new TaskStarredEvent(command.getId()));
    }

    /**
     * Unstars a Task.
     *
     * @param command unstar Task
     */
    @CommandHandler
    void on(UnstarTaskCommand command) {
        apply(new TaskUnstarredEvent(command.getId()));
    }

    /**
     * Modifies a Task title.
     *
     * @param command modify Task title
     */
    @CommandHandler
    void on(ModifyTaskTitleCommand command) {
        assertNotCompleted();
        apply(new TaskTitleModifiedEvent(command.getId(), command.getTitle()));
    }

    @EventHandler
    void on(TaskCreatedEvent event) {
        this.id = event.getId();
    }

    @EventHandler
    void on(TaskCompletedEvent event) {
        this.completed = true;
    }

    private void assertNotCompleted() {
        if (completed) {
            throw new TaskAlreadyCompletedException("Task [ identifier = " + id + " ] is completed.");
        }
    }
}
