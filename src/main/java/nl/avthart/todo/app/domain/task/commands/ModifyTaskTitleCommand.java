package nl.avthart.todo.app.domain.task.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

/**
 * @author albert
 */
@Value
public class ModifyTaskTitleCommand {

    @TargetAggregateIdentifier
    private final String id;

    @NotNull
    private final String title;
}

