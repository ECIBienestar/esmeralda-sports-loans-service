package cvds.esmeralda.service.loans.controller;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500","http://localhost:5500"})
@RequestMapping("/api/v1.0/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(@PathVariable String id) {
        return equipmentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<Equipment> getByType(@PathVariable String type) {
        return equipmentService.getByType(type);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> getAvailableById(@PathVariable String id) {
        Boolean available = equipmentService.getAvailableById(id);
        return (available != null)
                ? ResponseEntity.ok(available)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Equipment add(@RequestBody Equipment equipment) {
        return equipmentService.add(equipment);
    }

    @PutMapping("/{id}")
    public Equipment update(@RequestBody Equipment equipment, @PathVariable String id) {
        return equipmentService.update(equipment, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        equipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
