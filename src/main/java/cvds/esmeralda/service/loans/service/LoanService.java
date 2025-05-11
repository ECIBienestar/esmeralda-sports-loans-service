package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.repository.EquipmentRepository;
import cvds.esmeralda.service.loans.repository.LoanRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAll(){return loanRepository.findAll();}

    //public Loan add(Loan loan){
    //    return loanRepository.save(loan);
    //}

    public Loan add(Loan loan) {
        List<Loan> existingLoans = loanRepository.findByEquipmentId(loan.getEquipmentId());
        if (isOverlapping(loan.getDateAndTimeLoan(), loan.getDateAndTimeScheduleReturn(), existingLoans)) {
            throw new LoanException("Conflicting reservation: overlapping times for the same equipment.");
        }
        return loanRepository.save(loan);
    }

    public Loan update(Loan loan, String id){
        loan.setId(id);
        return loanRepository.save(loan);
    }

    public void deleteById(String id){
        loanRepository.deleteById(id);
    }

    public Optional<Loan> getById(String id){return loanRepository.findById(id);}

    public List<Loan> getByEquipmentId(String equipmentId){return loanRepository.findByEquipmentId(equipmentId);}

    public List<Loan> getByUserId(String userId){return loanRepository.findByUserId(userId);}

    /**
     * Verifies if the start and end dates of a new loans overlaps with an existing one.
     * @param newStart Start date of the new Loan
     * @param newEnd End date of the new Loan
     * @param existingLoans All existing Loans
     * @return true if overlaps, false otherwhise
     */
    private boolean isOverlapping(LocalDateTime newStart, LocalDateTime newEnd, List<Loan> existingLoans) {
        for (Loan existing : existingLoans) {
            LocalDateTime existingStart = existing.getDateAndTimeLoan();
            LocalDateTime existingEnd = existing.getDateAndTimeScheduleReturn();
            boolean overlap = !newStart.isAfter(existingEnd) && !newEnd.isBefore(existingStart);
            if (overlap) return true;
        }
        return false;
    }
}
