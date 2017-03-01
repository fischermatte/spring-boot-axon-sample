package nl.avthart.todo.app.domain.task.commands;

import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author albert
 */
@Value
public class CreateTaskCommand {

    @NotNull
    private final String id;

    @NotNull
    private final String username;

    @NotNull
    private final String title;
}
