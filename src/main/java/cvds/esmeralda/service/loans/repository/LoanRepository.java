package cvds.esmeralda.service.loans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cvds.esmeralda.service.loans.entity.loan.Loan;

import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    Optional<Loan> findByUserId(String userId);
}
