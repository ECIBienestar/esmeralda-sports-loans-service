package cvds.esmeralda.service.loans.controller;



import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.service.LoanService;
import cvds.esmeralda.service.loans.repository.LoanRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500","http://localhost:5500"})
@RequestMapping("/api/v1.0/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Retrieves a list of all loans.
     *
     * @return ResponseEntity containing the list of loans with HTTP status 200 (OK),
     *         or an error message with HTTP status 500 (Internal Server Error) if an exception occurs.
     */
    @GetMapping
    public List<Loan> getAll() {
        return loanService.getAll();
    }

    @PostMapping
    public Loan add(@RequestBody Loan loan) {
        return loanService.add(loan);
    }

}
