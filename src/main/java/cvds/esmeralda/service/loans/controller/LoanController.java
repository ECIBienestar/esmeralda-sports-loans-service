package cvds.esmeralda.service.loans.controller;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500"})
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Loan> getAll() {
        return loanService.getAll();
    }

    @Operation(
            summary = "Create a new loan",
            description = "Register a new loan using the provided loan data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid loan data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Loan add(@RequestBody Loan loan) {
        return loanService.add(loan);
    }

    @Operation(
            summary = "Update a loan",
            description = "Update loan details using the loan ID provided in headers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid loan ID or data"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public Loan update(@RequestBody Loan loan, @RequestHeader("loan-id") String id) {
        return loanService.update(loan, id);
    }

    @Operation(
            summary = "Delete a loan",
            description = "Delete a loan using its ID provided in headers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Loan deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid loan ID"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestHeader("loan-id") String id) {
        loanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get a loan by ID",
            description = "Retrieve the details of a specific loan by its ID provided in headers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid loan ID"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id")
    public ResponseEntity<Loan> getById(@RequestHeader("loan-id") String id) {
        return loanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get loans by equipment ID",
            description = "Retrieve all loans associated with a specific equipment, provided in the request header"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User or loans not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/equipment")
    public ResponseEntity<List<Loan>> getByEquipmentId(@RequestHeader("equipment-id") String equipmentId) {
        List<Loan> loans = loanService.getByEquipmentId(equipmentId);
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }

    @Operation(
            summary = "Get loans by user ID",
            description = "Retrieve all loans associated with a specific user, provided in the request header"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User or loans not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user")
    public ResponseEntity<List<Loan>> getByUserId(@RequestHeader("user-id") String userId) {
        List<Loan> loans = loanService.getByUserId(userId);
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }
}
