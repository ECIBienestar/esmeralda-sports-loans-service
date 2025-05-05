package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.repository.EquipmentRepository;
import cvds.esmeralda.service.loans.repository.LoanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAll(){return loanRepository.findAll();}

    public Loan add(Loan loan){
        return loanRepository.save(loan);
    }
}
