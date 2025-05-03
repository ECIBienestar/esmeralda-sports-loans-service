package cvds.esmeralda.service.loans.entity.loan;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document(collection = "loans")
public class Loan {
    @Id
    private String id;
    private String userName;
    private String userId;
    private LocalDateTime dateAndTimeLoan;
    private LocalDateTime dateAndTimeRepayment;
    private String loanStatus;
    private LocalDateTime dateAndTimeScheduleReturn;
    private String duration;

}
