package cvds.esmeralda.service.loans.service;

import cvds.esmeralda.service.loans.entity.equipment.Equipment;
import cvds.esmeralda.service.loans.repository.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EquipmentServiceTest {

    private EquipmentRepository equipmentRepository;
    private EquipmentService equipmentService;

    @BeforeEach
    void setUp() {
        equipmentRepository = mock(EquipmentRepository.class);
        equipmentService = new EquipmentService();
        // Usamos reflexión porque el repositorio está inyectado con @Autowired
        var field = EquipmentService.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(equipmentService, equipmentRepository);
        } catch (IllegalAccessException e) {
            fail("No se pudo inyectar el mock del repositorio");
        }
    }

    @Test
    void testGetAll() {
        List<Equipment> mockList = Arrays.asList(new Equipment(), new Equipment());
        when(equipmentRepository.findAll()).thenReturn(mockList);

        List<Equipment> result = equipmentService.getAll();

        assertEquals(2, result.size());
        verify(equipmentRepository).findAll();
    }

    @Test
    void testGetByIdFound() {
        Equipment equipment = new Equipment();
        equipment.setId("123");
        when(equipmentRepository.findById("123")).thenReturn(Optional.of(equipment));

        Optional<Equipment> result = equipmentService.getById("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(equipmentRepository.findById("999")).thenReturn(Optional.empty());

        Optional<Equipment> result = equipmentService.getById("999");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetByType() {
        Equipment e1 = new Equipment(); e1.setType("ball");
        Equipment e2 = new Equipment(); e2.setType("ball");
        when(equipmentRepository.findByType("ball")).thenReturn(List.of(e1, e2));

        List<Equipment> result = equipmentService.getByType("ball");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> "ball".equals(e.getType())));
    }

    @Test
    void testGetAvailableById() {
        Equipment equipment = new Equipment();
        equipment.setId("1");
        equipment.setAvailable(true);

        when(equipmentRepository.findById("1")).thenReturn(Optional.of(equipment));

        Boolean result = equipmentService.getAvailableById("1");

        assertTrue(result);
    }

    @Test
    void testGetAvailableByIdNotFound() {
        when(equipmentRepository.findById("2")).thenReturn(Optional.empty());

        Boolean result = equipmentService.getAvailableById("2");

        assertNull(result);
    }

    @Test
    void testAdd() {
        Equipment equipment = new Equipment();
        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        Equipment result = equipmentService.add(equipment);

        assertEquals(equipment, result);
        verify(equipmentRepository).save(equipment);
    }

    @Test
    void testUpdate() {
        Equipment equipment = new Equipment();
        when(equipmentRepository.save(any())).thenReturn(equipment);

        Equipment result = equipmentService.update(equipment, "456");

        ArgumentCaptor<Equipment> captor = ArgumentCaptor.forClass(Equipment.class);
        verify(equipmentRepository).save(captor.capture());
        assertEquals("456", captor.getValue().getId());
    }

    @Test
    void testDeleteById() {
        equipmentService.deleteById("789");

        verify(equipmentRepository).deleteById("789");
    }
}
