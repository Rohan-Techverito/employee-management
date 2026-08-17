package com.example.app.employeeDocument;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.security.test.context.support.WithMockUser;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.Random.class)
class EmployeeDocumentControllerIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired EmployeeDocumentRepository repository;

    /** Returns the minimal valid JSON payload, substituting live parent IDs for FK fields. */
    private String payload() {
        return "{\"employeeId\":\"00000000-0000-0000-0000-000000000001\",\"originalFilename\":\"test-security\",\"mimeType\":\"test-security\",\"storagePath\":\"test-security\",\"sizeBytes\":1}";
    }

    @BeforeAll
    static void disableRyuk() {
        System.setProperty("testcontainers.ryuk.disabled", "true");
    }

    @BeforeEach
    void setUp() throws Exception {
        repository.deleteAll();
    }

    // ── GET /api/v1/employee-documents ─────────────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_200_with_empty_list_when_no_entities_exist() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_200_with_entities_after_create() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    // ── GET /api/v1/employee-documents/{id} ───────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_200_when_entity_exists_by_id() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.employeeId").value("00000000-0000-0000-0000-000000000001"))
                .andExpect(jsonPath("$.originalFilename").value("test-security"))
                .andExpect(jsonPath("$.mimeType").value("test-security"))
                .andExpect(jsonPath("$.storagePath").value("test-security"))
                .andExpect(jsonPath("$.sizeBytes").value(1));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_404_when_entity_is_not_found_by_id() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_400_when_id_format_is_invalid() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents/{id}", "not-a-valid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    // ── POST /api/v1/employee-documents ────────────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_201_and_persist_entity_when_valid_request_is_provided() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.employeeId").value("00000000-0000-0000-0000-000000000001"))
                .andExpect(jsonPath("$.originalFilename").value("test-security"))
                .andExpect(jsonPath("$.mimeType").value("test-security"))
                .andExpect(jsonPath("$.storagePath").value("test-security"))
                .andExpect(jsonPath("$.sizeBytes").value(1));

        assertThat(repository.count()).isEqualTo(1);
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_400_when_create_payload_is_empty() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors").isArray());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_400_when_create_request_has_blank_string_fields() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":\"00000000-0000-0000-0000-000000000000\",\"originalFilename\":\"\",\"mimeType\":\"\",\"storagePath\":\"\",\"sizeBytes\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    // ── PUT /api/v1/employee-documents/{id} ────────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_200_and_update_entity_when_valid_request_is_provided() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(put(location)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_404_when_updating_non_existent_entity() throws Exception {
        mockMvc.perform(put("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_400_when_update_payload_is_empty() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(put(location)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    // ── DELETE /api/v1/employee-documents/{id} ─────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_204_and_remove_entity_when_id_exists() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(delete(location))
                .andExpect(status().isNoContent());

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_404_when_deleting_non_existent_entity() throws Exception {
        mockMvc.perform(delete("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // ── Lifecycle & data-integrity ─────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_404_when_fetching_entity_after_delete() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_404_when_entity_is_deleted_twice() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(delete(location)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_empty_list_when_all_entities_are_deleted() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_return_400_when_update_request_has_blank_string_fields() throws Exception {
        String location = mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
        mockMvc.perform(put(location)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"employeeId\":\"00000000-0000-0000-0000-000000000000\",\"originalFilename\":\"\",\"mimeType\":\"\",\"storagePath\":\"\",\"sizeBytes\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    // ── Security: 401 Unauthenticated ─────────────────────────────────────────

    @Test
    void should_return_401_when_find_all_is_called_without_authentication() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_return_401_when_find_by_id_is_called_without_authentication() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_return_401_when_create_is_called_without_authentication() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_return_401_when_update_is_called_without_authentication() throws Exception {
        mockMvc.perform(put("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_return_401_when_delete_is_called_without_authentication() throws Exception {
        mockMvc.perform(delete("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isUnauthorized());
    }

    // ── Security: 403 Insufficient Role ───────────────────────────────────────
    @Test
    @WithMockUser(roles = {"USER"})
    void should_return_403_when_find_all_is_called_by_user_role() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void should_return_403_when_find_by_id_is_called_by_user_role() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void should_return_403_when_create_is_called_by_user_role() throws Exception {
        mockMvc.perform(post("/api/v1/employee-documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void should_return_403_when_update_is_called_by_user_role() throws Exception {
        mockMvc.perform(put("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload()))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void should_return_403_when_delete_is_called_by_user_role() throws Exception {
        mockMvc.perform(delete("/api/v1/employee-documents/{id}", "00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isForbidden());
    }

    // ── Correlation ID ─────────────────────────────────────────────────────────

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_echo_correlation_id_header_when_provided_in_request() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents")
                        .header("X-Request-ID", "test-trace-abc-123"))
                .andExpect(header().string("X-Request-ID", "test-trace-abc-123"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_generate_correlation_id_header_when_absent_from_request() throws Exception {
        mockMvc.perform(get("/api/v1/employee-documents"))
                .andExpect(header().exists("X-Request-ID"));
    }
}
