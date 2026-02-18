package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;
import tn.esprit.studentmanagement.services.DepartmentService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    // ---------------- MOCK DATA ----------------

    private Department mockDepartment1() {
        return Department.builder()
                .idDepartment(1L)
                .name("IT")
                .location("Tunis")
                .phone("123")
                .head("Sami")
                .students(null)
                .build();
    }

    private Department mockDepartment2() {
        return Department.builder()
                .idDepartment(2L)
                .name("HR")
                .location("Sfax")
                .phone("456")
                .head("Ali")
                .students(null)
                .build();
    }

    private Department mockDepartmentToSave() {
        return Department.builder()
                .name("Finance")
                .location("Tunis")
                .phone("789")
                .head("Amir")
                .students(null)
                .build();
    }

    private Department mockSavedDepartment() {
        return Department.builder()
                .idDepartment(3L)
                .name("Finance")
                .location("Tunis")
                .phone("789")
                .head("Amir")
                .students(null)
                .build();
    }

    // ---------------- TESTS ----------------

    @Test
    void testGetAllDepartments() {
        List<Department> departments = Arrays.asList(mockDepartment1(), mockDepartment2());

        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        verify(departmentRepository).findAll();
    }

    @Test
    void testGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(mockDepartment1()));

        Department result = departmentService.getDepartmentById(1L);

        assertEquals("IT", result.getName());
        verify(departmentRepository).findById(1L);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.getDepartmentById(1L));

        verify(departmentRepository).findById(1L);
    }

    @Test
    void testSaveDepartment() {
        // Use any(Department.class) to avoid PotentialStubbingProblem
        when(departmentRepository.save(any(Department.class))).thenReturn(mockSavedDepartment());

        Department result = departmentService.saveDepartment(mockDepartmentToSave());

        assertNotNull(result.getIdDepartment());
        assertEquals("Finance", result.getName());
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentRepository).deleteById(1L);

        departmentService.deleteDepartment(1L);

        verify(departmentRepository).deleteById(1L);
    }
}
