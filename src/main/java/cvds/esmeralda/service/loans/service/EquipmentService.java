package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAll(){return equipmentRepository.findAll();}

    public Optional<Equipment> getById(String id){return equipmentRepository.findById(id);}

    public List<Equipment> getByType(String type){return equipmentRepository.findByType(type);}

    public Boolean getAvailableById(String id){
        return getById(id)
                .map(Equipment::getAvailable)
                .orElse(null);
    }

    public Equipment add(Equipment equipment){
        return equipmentRepository.save(equipment);
    }

    public Equipment update(Equipment equipment, String id){
        equipment.setId(id);
        return equipmentRepository.save(equipment);
    }

    public void deleteById(String id){
        equipmentRepository.deleteById(id);
    }

    public List<Equipment> getAvailableByType(String type) {
        List<Equipment> allByType = equipmentRepository.findByType(type);
        return allByType.stream()
                .filter(Equipment::getAvailable)
                .toList();
    }

    public Map<String, Long> getAllEquipmentTypeCounts() {
        return equipmentRepository.findAll().stream()
                .filter(equipment -> equipment.getType() != null)
                .collect(Collectors.groupingBy(Equipment::getType, Collectors.counting()));
    }
}
