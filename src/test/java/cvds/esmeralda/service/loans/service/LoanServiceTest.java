package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.exception.LoanException;
import cvds.esmeralda.service.loans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        loan.setEquipmentId("equipment123");
        loan.setUserId("user123");
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 13, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 13, 0));
    }

    @Test
    void testGetAll() {
        when(loanRepository.findAll()).thenReturn(List.of(loan));
        assertEquals(1, loanService.getAll().size());
    }

    @Test
    void testAdd() {
        when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(loan);
        Loan result = loanService.add(loan);
        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void testUpdate() {
        Loan updatedLoan = new Loan();
        updatedLoan.setId("123");
        updatedLoan.setUserId("user123");
        when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(updatedLoan);
        Loan result = loanService.update(updatedLoan, "123");
        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void testDeleteById() {
        Mockito.doNothing().when(loanRepository).deleteById("123");
        loanService.deleteById("123");
        verify(loanRepository, Mockito.times(1)).deleteById("123");
    }

    @Test
    void testGetById() {
        when(loanRepository.findById("123")).thenReturn(Optional.of(loan));
        Optional<Loan> result = loanService.getById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetByEquipmentId() {
        when(loanRepository.findByEquipmentId("equipment123")).thenReturn(List.of(loan));

        List<Loan> result = loanService.getByEquipmentId("equipment123");

        assertFalse(result.isEmpty());
        assertEquals("equipment123", result.get(0).getEquipmentId());
    }


    @Test
    void testGetByUserId() {
        when(loanRepository.findByUserId("user123")).thenReturn(List.of(loan));

        List<Loan> result = loanService.getByUserId("user123");

        assertFalse(result.isEmpty());
        assertEquals("user123", result.get(0).getUserId());
    }

    @Test
    void shouldThrowExceptionWhenLoanOverlaps() {
        Loan loan = new Loan();
        loan.setId("loan1");
        loan.setEquipmentId("eq1");
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 13, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 15, 0));

        Loan existingLoan = new Loan();
        existingLoan.setId("loan2");
        existingLoan.setEquipmentId("eq1");
        existingLoan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 14, 0));
        existingLoan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 16, 0));

        when(loanRepository.findByEquipmentId("eq1")).thenReturn(List.of(existingLoan));

        assertThrows(LoanException.class, () -> loanService.add(loan));
        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldSaveLoanWhenNoOverlap() {
        Loan loan = new Loan();
        loan.setId("loan1");
        loan.setEquipmentId("eq1");
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 10, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 12, 0));

        Loan existingLoan = new Loan();
        existingLoan.setId("loan2");
        existingLoan.setEquipmentId("eq1");
        existingLoan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 13, 0));
        existingLoan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 14, 0));

        when(loanRepository.findByEquipmentId("eq1")).thenReturn(List.of(existingLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.add(loan);

        assertNotNull(result);
        assertEquals("loan1", result.getId());
    }

    @Test
    void shouldSaveLoanWhenOverlapsWithDifferentEquipmentId() {
        Loan loan = new Loan();
        loan.setId("loan1");
        loan.setEquipmentId("eq1");
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 13, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 15, 0));

        Loan existingLoan = new Loan();
        existingLoan.setId("loan2");
        existingLoan.setEquipmentId("eq2");
        existingLoan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 5, 14, 0));
        existingLoan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 5, 16, 0));

        when(loanRepository.findByEquipmentId("eq1")).thenReturn(List.of(existingLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.add(loan);

        assertNotNull(result);
        assertEquals("loan1", result.getId());
    }

    @Test
    void shouldThrowExceptionWhenReturnDateIsDifferentDay() {
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 11, 10, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 12, 12, 0));

        assertThrows(LoanException.class, () -> loanService.add(loan));
    }

    @Test
    void shouldThrowExceptionWhenStartAfterReturn() {
        loan.setDateAndTimeLoan(LocalDateTime.of(2026, 5, 12, 14, 0));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.of(2026, 5, 12, 10, 0));

        assertThrows(LoanException.class, () -> loanService.add(loan));
    }

    @Test
    void shouldThrowExceptionWhenLoanDateIsInPast() {
        loan.setDateAndTimeLoan(LocalDateTime.now().minusDays(1));
        loan.setDateAndTimeScheduleReturn(LocalDateTime.now().plusHours(1));

        assertThrows(LoanException.class, () -> loanService.add(loan));
    }
}
