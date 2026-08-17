package com.example.app.employee;

import com.example.app.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.Instant;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.app.department.Department;
import com.example.app.department.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeProcessingStrategy strategy;
    private EmployeeStrategyFactory strategyFactory;
    private EmployeeService service;

    private static final UUID EXISTING_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID MISSING_ID  = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private Employee fixture;

    @BeforeEach
    void seedFixture() {
        fixture = new Employee();
        
        ReflectionTestUtils.setField(fixture, "id", EXISTING_ID);
        fixture.setFirstName("sample-first-name");
        fixture.setLastName("sample-last-name");
        fixture.setEmail("sample-email");
        fixture.setJobTitle("sample-job-title");
        fixture.setStatus("sample-status");
        fixture.setPhone("sample-phone");
        fixture.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        fixture.setUpdatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        strategyFactory = new EmployeeStrategyFactory(List.of(strategy));
        service = new EmployeeService(repository, departmentRepository, strategyFactory);
    }

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    void should_return_all_responses_when_repository_has_entities() {
        Pageable pageable = PageRequest.of(0, 20);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(fixture)));
        Page<EmployeeResponse> result = service.findAll(pageable);
        assertThat(result.getContent()).hasSize(1);
        verify(repository).findAll(pageable);
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    void should_return_response_when_entity_exists_by_id() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        EmployeeResponse result = service.findById(EXISTING_ID);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        verify(repository).findById(EXISTING_ID);
    }

    @Test
    void should_throw_entity_not_found_when_id_is_unknown() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(MISSING_ID))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));
    }

    // ── create ─────────────────────────────────────────────────────────────────

    @Test
    void should_persist_entity_and_return_response_when_create_is_called() {
        EmployeeCreateRequest request = new EmployeeCreateRequest("test-value", "test-value", "test-value", "test-value", java.time.LocalDate.of(2024, 1, 1), "test-value", "test-value", UUID.fromString("11111111-1111-1111-1111-111111111111"));
        when(departmentRepository.findById(any())).thenReturn(Optional.of(new Department()));
        when(strategy.supports(any())).thenReturn(true);
        when(repository.save(any(Employee.class))).thenReturn(fixture);

        EmployeeResponse result = service.create(request);

        assertThat(result.id()).isEqualTo(fixture.getId());
        assertThat(result.firstName()).isEqualTo(fixture.getFirstName());
        
        ArgumentCaptor<Employee> createCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(repository).save(createCaptor.capture());
        assertThat(createCaptor.getValue().getFirstName()).isEqualTo(request.firstName());
        
    }

    // ── update ─────────────────────────────────────────────────────────────────

    @Test
    void should_update_entity_and_return_response_when_entity_exists() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest("updated-value", "updated-value", "updated-value", "updated-value", java.time.LocalDate.of(2025, 6, 1), "updated-value", "updated-value", UUID.fromString("22222222-2222-2222-2222-222222222222"));
        when(departmentRepository.findById(any())).thenReturn(Optional.of(new Department()));
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));
        when(repository.save(any(Employee.class))).thenReturn(fixture);

        EmployeeResponse result = service.update(EXISTING_ID, request);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        assertThat(result.firstName()).isEqualTo(fixture.getFirstName());
        verify(repository).findById(EXISTING_ID);
        ArgumentCaptor<Employee> updateCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(repository).save(updateCaptor.capture());
        assertThat(updateCaptor.getValue().getFirstName()).isEqualTo(request.firstName());
        
    }

    @Test
    void should_throw_entity_not_found_when_updating_with_unknown_id() {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest("updated-value", "updated-value", "updated-value", "updated-value", java.time.LocalDate.of(2025, 6, 1), "updated-value", "updated-value", UUID.fromString("22222222-2222-2222-2222-222222222222"));
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(MISSING_ID, request))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository).findById(MISSING_ID);
        verify(repository, never()).save(any(Employee.class));
    }

    // ── delete ─────────────────────────────────────────────────────────────────

    @Test
    void should_remove_entity_when_id_exists() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        service.delete(EXISTING_ID);

        verify(repository).findById(EXISTING_ID);
        verify(repository).delete(fixture);
    }

    @Test
    void should_throw_entity_not_found_when_deleting_with_unknown_id() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(MISSING_ID))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository, never()).delete(any(Employee.class));
    }
}
