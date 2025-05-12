package cvds.esmeralda.service.loans.controller;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.service.EquipmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment list retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAll();
    }

    @Operation(
            summary = "Get equipment by ID",
            description = "Retrieve detailed information about a specific equipment item by its ID (in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment ID"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id")
    public ResponseEntity<Equipment> getById(@RequestHeader("equipment-id") String id) {
        return equipmentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get equipment by type",
            description = "Retrieve all equipment items filtered by a specific type (in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment list retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/type")
    public List<Equipment> getByType(@RequestHeader("equipment-type") String type) {
        return equipmentService.getByType(type);
    }

    @Operation(
            summary = "Check availability by ID",
            description = "Check if a specific equipment item is currently available (ID in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability status retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment ID"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/available")
    public ResponseEntity<Boolean> getAvailableById(@RequestHeader("equipment-id") String id) {
        Boolean available = equipmentService.getAvailableById(id);
        return (available != null)
                ? ResponseEntity.ok(available)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Register new equipment",
            description = "Add a new equipment item to the inventory"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public Equipment add(@RequestBody Equipment equipment) {
        return equipmentService.add(equipment);
    }

    @Operation(
            summary = "Update equipment",
            description = "Update the information of an existing equipment item (ID in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment ID or data"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public Equipment update(@RequestBody Equipment equipment, @RequestHeader("equipment-id") String id) {
        return equipmentService.update(equipment, id);
    }

    @Operation(
            summary = "Delete equipment",
            description = "Remove an equipment item from the inventory (ID in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipment deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment ID"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestHeader("equipment-id") String id) {
        equipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get equipment by type filtering just availables",
            description = "Retrieve all available equipment items filtered by a specific type (in header)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipment list retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equipment type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/availablesType")
    public List<Equipment> getAvailablesByType(@RequestHeader("equipment-type") String type) {
        return equipmentService.getAvailableByType(type);
    }
}
