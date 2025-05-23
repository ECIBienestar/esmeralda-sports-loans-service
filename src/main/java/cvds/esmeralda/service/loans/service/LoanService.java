package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.repository.EquipmentRepository;
import cvds.esmeralda.service.loans.repository.LoanRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Loan> getAll(){return loanRepository.findAll();}

    public Loan add(Loan loan) {
        List<Loan> existingLoans = loanRepository.findByEquipmentId(loan.getEquipmentId());
        if (loan.getDateAndTimeLoan().isBefore(LocalDateTime.now())) {
            throw new LoanException("Wrong reservation: Loans on past dates are not allowed.");
        }
        if (isOverlapping(loan.getDateAndTimeLoan(), loan.getDateAndTimeScheduleReturn(), existingLoans, loan.getEquipmentId())) {
            throw new LoanException("Conflicting reservation: overlapping times for the same equipment.");
        }
        if (!loan.getDateAndTimeLoan().toLocalDate().equals(loan.getDateAndTimeScheduleReturn().toLocalDate())) {
            throw new LoanException("Wrong reservation: The repayment date must be the same day of the loan.");
        }
        if (loan.getDateAndTimeLoan().isAfter(loan.getDateAndTimeScheduleReturn())) {
            throw new LoanException("Wrong reservation: The start date cannot be later than the return date.");
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
    private boolean isOverlapping(LocalDateTime newStart, LocalDateTime newEnd, List<Loan> existingLoans, String equipmentId) {
        for (Loan existing : existingLoans) {
            LocalDateTime existingStart = existing.getDateAndTimeLoan();
            LocalDateTime existingEnd = existing.getDateAndTimeScheduleReturn();
            boolean overlap = !newStart.isAfter(existingEnd) && !newEnd.isBefore(existingStart);
            if (overlap) {
                if (existing.getEquipmentId().equals(equipmentId)) return true;
            }
        }
        return false;
    }

    public List<Loan> getActiveLoansByUserId(String userId) {
        return loanRepository.findByUserId(userId).stream()
                .filter(loan -> "EN_PRESTAMO".equalsIgnoreCase(loan.getLoanStatus()))
                .toList();
    }

    public List<Loan> getReturnedLoansByUserId(String userId) {
        return loanRepository.findByUserId(userId).stream()
                .filter(loan -> "ENTREGADO".equalsIgnoreCase(loan.getLoanStatus()))
                .toList();
    }

    public Map<String, Long> getAllLoanedEquipmentTypeCounts() {
        return loanRepository.findAll().stream()
                .map(loan -> {
                    String equipmentId = loan.getEquipmentId();
                    if (equipmentId == null) {
                        return null;
                    }
                    return equipmentRepository.findById(equipmentId).orElse(null);
                })
                .filter(equipment -> equipment != null && equipment.getType() != null)
                .collect(Collectors.groupingBy(Equipment::getType, Collectors.counting()));
    }


    public Map<String, Long> getLoanedEquipmentTypeCountsFromLastWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfLastWeek = today.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate endOfLastWeek = today.minusWeeks(1).with(DayOfWeek.SUNDAY);

        return loanRepository.findAll().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getDateAndTimeLoan().toLocalDate();
                    return !loanDate.isBefore(startOfLastWeek) && !loanDate.isAfter(endOfLastWeek);
                })
                .map(loan -> equipmentRepository.findById(loan.getEquipmentId()).orElse(null))
                .filter(equipment -> equipment != null && equipment.getType() != null)
                .collect(Collectors.groupingBy(Equipment::getType, Collectors.counting()));
    }
}
