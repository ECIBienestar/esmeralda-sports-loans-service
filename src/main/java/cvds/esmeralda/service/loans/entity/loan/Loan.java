package cvds.esmeralda.service.loans.entity.loan;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String loanStatus;
    private LocalDateTime dateAndTimeScheduleReturn;
    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateAndTimeLoan() {
        return dateAndTimeLoan;
    }

    public void setDateAndTimeLoan(LocalDateTime dateAndTimeLoan) {
        this.dateAndTimeLoan = dateAndTimeLoan;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LocalDateTime getDateAndTimeScheduleReturn() {
        return dateAndTimeScheduleReturn;
    }

    public void setDateAndTimeScheduleReturn(LocalDateTime dateAndTimeScheduleReturn) {
        this.dateAndTimeScheduleReturn = dateAndTimeScheduleReturn;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
