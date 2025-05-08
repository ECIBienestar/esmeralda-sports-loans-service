package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setId("123");
        loan.setUserId("user123");
        // Agrega otros campos necesarios si existen en la clase Loan.
    }

    @Test
    void testGetAll() {
        Mockito.when(loanRepository.findAll()).thenReturn(List.of(loan));
        assertEquals(1, loanService.getAll().size());
    }

    @Test
    void testAdd() {
        Mockito.when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(loan);
        Loan result = loanService.add(loan);
        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void testUpdate() {
        Loan updatedLoan = new Loan();
        updatedLoan.setId("123");
        updatedLoan.setUserId("user123");
        Mockito.when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(updatedLoan);
        Loan result = loanService.update(updatedLoan, "123");
        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void testDeleteById() {
        Mockito.doNothing().when(loanRepository).deleteById("123");
        loanService.deleteById("123");
        Mockito.verify(loanRepository, Mockito.times(1)).deleteById("123");
    }

    @Test
    void testGetById() {
        Mockito.when(loanRepository.findById("123")).thenReturn(Optional.of(loan));
        Optional<Loan> result = loanService.getById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetByUserId() {
        Mockito.when(loanRepository.findByUserId("user123")).thenReturn(Optional.of(loan));
        Optional<Loan> result = loanService.getByUserId("user123");
        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUserId());
    }
}
