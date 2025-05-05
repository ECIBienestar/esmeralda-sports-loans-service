package cvds.esmeralda.service.loans.controller;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.entity.loan.LoanDTO;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.service.LoanService;
import cvds.esmeralda.service.loans.repository.LoanRepository;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500","http://localhost:5500"})
@RequestMapping("/api/v1.0/loans")
@Tag(name = "Loan Controller", description = "Manage equipment loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Get all loans",
            description = "Retrieve a list of all existing loans"
    )
    @GetMapping
    public List<Loan> getAll() {
        return loanService.getAll();
    }

    @Operation(
            summary = "Create a new loan",
            description = "Register a new loan using the provided loan data"
    )
    @PostMapping
    public Loan add(@RequestBody Loan loan) {
        return loanService.add(loan);
    }

    @Operation(
            summary = "Update a loan",
            description = "Update loan details using the given ID"
    )
    @PutMapping("/{id}")
    public Loan update(@RequestBody Loan loan, @PathVariable String id) {
        return loanService.update(loan, id);
    }

    @Operation(
            summary = "Delete a loan",
            description = "Delete a loan using its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        loanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get a loan by ID",
            description = "Retrieve the details of a specific loan by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getById(@PathVariable String id) {
        return loanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get loans by user ID",
            description = "Retrieve all loans associated with a specific user"
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<Loan> getByUserId(@PathVariable String userId) {
        return loanService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
