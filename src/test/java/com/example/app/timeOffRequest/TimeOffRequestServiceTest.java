package com.example.app.timeOffRequest;

import com.example.app.exception.TimeOffRequestNotFoundException;
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

@ExtendWith(MockitoExtension.class)
class TimeOffRequestServiceTest {

    @Mock
    private TimeOffRequestRepository repository;
    @Mock
    private TimeOffRequestProcessingStrategy strategy;
    private TimeOffRequestStrategyFactory strategyFactory;
    private TimeOffRequestService service;

    private static final UUID EXISTING_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID MISSING_ID  = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private TimeOffRequest fixture;

    @BeforeEach
    void seedFixture() {
        fixture = new TimeOffRequest();
        
        ReflectionTestUtils.setField(fixture, "id", EXISTING_ID);
        fixture.setEmployeeId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        fixture.setManagerId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        fixture.setStatus("sample-status");
        fixture.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        fixture.setUpdatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        strategyFactory = new TimeOffRequestStrategyFactory(List.of(strategy));
        service = new TimeOffRequestService(repository, strategyFactory);
    }

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    void should_return_all_responses_when_repository_has_entities() {
        Pageable pageable = PageRequest.of(0, 20);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(fixture)));
        Page<TimeOffRequestResponse> result = service.findAll(pageable);
        assertThat(result.getContent()).hasSize(1);
        verify(repository).findAll(pageable);
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    void should_return_response_when_entity_exists_by_id() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        TimeOffRequestResponse result = service.findById(EXISTING_ID);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        verify(repository).findById(EXISTING_ID);
    }

    @Test
    void should_throw_entity_not_found_when_id_is_unknown() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(MISSING_ID))
                .isInstanceOf(TimeOffRequestNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));
    }

    // ── create ─────────────────────────────────────────────────────────────────

    @Test
    void should_persist_entity_and_return_response_when_create_is_called() {
        TimeOffRequestCreateRequest request = new TimeOffRequestCreateRequest(UUID.fromString("11111111-1111-1111-1111-111111111111"), UUID.fromString("11111111-1111-1111-1111-111111111111"), "test-value");
        when(strategy.supports(any())).thenReturn(true);
        when(repository.save(any(TimeOffRequest.class))).thenReturn(fixture);

        TimeOffRequestResponse result = service.create(request);

        assertThat(result.id()).isEqualTo(fixture.getId());
        assertThat(result.employeeId()).isEqualTo(fixture.getEmployeeId());
        
        ArgumentCaptor<TimeOffRequest> createCaptor = ArgumentCaptor.forClass(TimeOffRequest.class);
        verify(repository).save(createCaptor.capture());
        assertThat(createCaptor.getValue().getEmployeeId()).isEqualTo(request.employeeId());
        
    }

    // ── update ─────────────────────────────────────────────────────────────────

    @Test
    void should_update_entity_and_return_response_when_entity_exists() {
        TimeOffRequestUpdateRequest request = new TimeOffRequestUpdateRequest(UUID.fromString("22222222-2222-2222-2222-222222222222"), UUID.fromString("22222222-2222-2222-2222-222222222222"), "updated-value");
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));
        when(repository.save(any(TimeOffRequest.class))).thenReturn(fixture);

        TimeOffRequestResponse result = service.update(EXISTING_ID, request);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        assertThat(result.employeeId()).isEqualTo(fixture.getEmployeeId());
        verify(repository).findById(EXISTING_ID);
        ArgumentCaptor<TimeOffRequest> updateCaptor = ArgumentCaptor.forClass(TimeOffRequest.class);
        verify(repository).save(updateCaptor.capture());
        assertThat(updateCaptor.getValue().getEmployeeId()).isEqualTo(request.employeeId());
        
    }

    @Test
    void should_throw_entity_not_found_when_updating_with_unknown_id() {
        TimeOffRequestUpdateRequest request = new TimeOffRequestUpdateRequest(UUID.fromString("22222222-2222-2222-2222-222222222222"), UUID.fromString("22222222-2222-2222-2222-222222222222"), "updated-value");
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(MISSING_ID, request))
                .isInstanceOf(TimeOffRequestNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository).findById(MISSING_ID);
        verify(repository, never()).save(any(TimeOffRequest.class));
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
                .isInstanceOf(TimeOffRequestNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository, never()).delete(any(TimeOffRequest.class));
    }
}
