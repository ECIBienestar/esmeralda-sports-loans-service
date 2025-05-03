package cvds.esmeralda.service.loans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import cvds.esmeralda.service.loans.entity.loan.Loan;
import cvds.esmeralda.service.loans.entity.equipment.Equipment;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    boolean existsById(@NonNull String id);
}
