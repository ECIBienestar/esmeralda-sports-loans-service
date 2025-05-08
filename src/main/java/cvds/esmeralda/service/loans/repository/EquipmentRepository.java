package cvds.esmeralda.service.loans.repository;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment,String> {

    List<Equipment> findByType(String type);
}
