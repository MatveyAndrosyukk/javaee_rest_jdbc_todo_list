package api.model;


import api.util.LocalDateDeserializer;
import api.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"id", "title", "description", "deadline_date", "done", "executor"})
public class Task {
    private int id;
    private String title;
    private String description;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deadline_date;
    private boolean done;
    private User executor;
}
