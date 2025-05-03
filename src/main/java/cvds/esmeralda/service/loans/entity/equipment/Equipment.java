package cvds.esmeralda.service.loans.entity.equipment;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document(collection = "equipment")
public class Equipment {
    @Id
    private String id;
    private Boolean available;
    private String type;
    private String description;
    private String status;

}
