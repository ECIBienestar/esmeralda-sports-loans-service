package cvds.esmeralda.service.loans.controller;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.1:5500","http://localhost:5500"})
@RequestMapping("/api/v1.0/equipment")
@Tag(name = "Equipment Controller", description = "Manage equipment inventory and availability")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Operation(
            summary = "Get all equipment",
            description = "Retrieve a list of all available equipment"
    )
    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAll();
    }

    @Operation(
            summary = "Get equipment by ID",
            description = "Retrieve detailed information about a specific equipment item by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(@PathVariable String id) {
        return equipmentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get equipment by type",
            description = "Retrieve all equipment items filtered by a specific type (e.g. Audiovisual, Sports)"
    )
    @GetMapping("/type/{type}")
    public List<Equipment> getByType(@PathVariable String type) {
        return equipmentService.getByType(type);
    }

    @Operation(
            summary = "Check availability by ID",
            description = "Check if a specific equipment item is currently available"
    )
    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> getAvailableById(@PathVariable String id) {
        Boolean available = equipmentService.getAvailableById(id);
        return (available != null)
                ? ResponseEntity.ok(available)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Register new equipment",
            description = "Add a new equipment item to the inventory"
    )
    @PostMapping
    public Equipment add(@RequestBody Equipment equipment) {
        return equipmentService.add(equipment);
    }

    @Operation(
            summary = "Update equipment",
            description = "Update the information of an existing equipment item by its ID"
    )
    @PutMapping("/{id}")
    public Equipment update(@RequestBody Equipment equipment, @PathVariable String id) {
        return equipmentService.update(equipment, id);
    }

    @Operation(
            summary = "Delete equipment",
            description = "Remove an equipment item from the inventory using its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        equipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
