package nl.avthart.todo.app.query.task;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author albert
 */
@Document(indexName = "tasks", type = "entry")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TaskEntry {

    @Id
    private String id;

    private String username;

    @Setter
    private String title;

    @Setter
    private boolean completed;

    @Setter
    private boolean starred;
}