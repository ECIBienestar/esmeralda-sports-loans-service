package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.repository.LoanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository /*, EquipmentService equipmentService*/) {
        this.loanRepository = loanRepository;
        //this.equipmentService = equipmentService;
    }

    /**
     * Retrieves a list of all loans.
     * @return a list of Loan objects
     */
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }
}
