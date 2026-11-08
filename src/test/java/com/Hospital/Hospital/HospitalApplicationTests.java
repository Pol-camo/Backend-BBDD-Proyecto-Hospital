package com.Hospital.Hospital;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(NurseController.class)
class NurseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private NurseRepository nurseRepository;

  private Nurse nurse1;
  private Nurse nurse2;

  @BeforeEach
  void setUp() {
    nurse1 = new Nurse();
    nurse1.setId(1);
    nurse1.setFullName("Ana Lopez");
    nurse1.setUserName("analopez");
    nurse1.setPassword("1234");

    nurse2 = new Nurse();
    nurse2.setId(2);
    nurse2.setFullName("Pedro Ruiz");
    nurse2.setUserName("pruiz");
    nurse2.setPassword("abcd");
  }

  // ---------- POST /nurse/add ----------

  @Test
  void addNewUser_shouldSaveNurseAndReturnSaved() throws Exception {
    mockMvc.perform(post("/nurse/add")
            .param("name", "Ana Lopez")
            .param("password", "1234")
            .param("user", "analopez"))
        .andExpect(status().isOk())
        .andExpect(content().string("Saved"));

    ArgumentCaptor<Nurse> captor = ArgumentCaptor.forClass(Nurse.class);
    verify(nurseRepository).save(captor.capture());

    Nurse saved = captor.getValue();
    Assertions.assertEquals("Ana Lopez", saved.getFullName());
    Assertions.assertEquals("analopez", saved.getUserName());
    Assertions.assertEquals("1234", saved.getPassword());
  }

  @Test
  void addNewUser_missingParam_shouldReturnBadRequest() throws Exception {
    mockMvc.perform(post("/nurse/add")
            .param("name", "Ana Lopez")
            .param("user", "analopez"))
        .andExpect(status().isBadRequest());

    verify(nurseRepository, never()).save(any(Nurse.class));
  }

  // ---------- GET /nurse/index ----------

  @Test
  void getAllNurses_shouldReturnList() throws Exception {
    when(nurseRepository.findAll()).thenReturn(Arrays.asList(nurse1, nurse2));

    mockMvc.perform(get("/nurse/index"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].full_name").value("Ana Lopez"))
        .andExpect(jsonPath("$[1].full_name").value("Pedro Ruiz"));
  }

  @Test
  void getAllNurses_empty_shouldReturnEmptyList() throws Exception {
    when(nurseRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/nurse/index"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  // ---------- GET /nurse/name/{name} ----------

  @Test
  void findByName_found_shouldReturnNurse() throws Exception {
    when(nurseRepository.findFirstByFullNameIgnoreCaseOrUserNameIgnoreCase("analopez", "analopez"))
        .thenReturn(Optional.of(nurse1));

    mockMvc.perform(get("/nurse/name/{name}", "analopez"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.full_name").value("Ana Lopez"))
        .andExpect(jsonPath("$.user_name").value("analopez"));
  }

  @Test
  void findByName_notFound_shouldReturn404() throws Exception {
    when(nurseRepository.findFirstByFullNameIgnoreCaseOrUserNameIgnoreCase("nadie", "nadie"))
        .thenReturn(Optional.empty());

    mockMvc.perform(get("/nurse/name/{name}", "nadie"))
        .andExpect(status().isNotFound());
  }

  // ---------- POST /nurse/login ----------

  @Test
  void login_validCredentials_shouldReturnTrue() throws Exception {
    when(nurseRepository.findByUserName("analopez")).thenReturn(Optional.of(nurse1));

    mockMvc.perform(post("/nurse/login")
            .param("user", "analopez")
            .param("password", "1234"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  void login_wrongPassword_shouldReturn401() throws Exception {
    when(nurseRepository.findByUserName("analopez")).thenReturn(Optional.of(nurse1));

    mockMvc.perform(post("/nurse/login")
            .param("user", "analopez")
            .param("password", "incorrecta"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("false"));
  }

  @Test
  void login_userNotFound_shouldReturn401() throws Exception {
    when(nurseRepository.findByUserName("fantasma")).thenReturn(Optional.empty());

    mockMvc.perform(post("/nurse/login")
            .param("user", "fantasma")
            .param("password", "1234"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("false"));
  }

  // ---------- GET /nurse/{id} ----------

  @Test
  void getNurseById_found_shouldReturnNurse() throws Exception {
    when(nurseRepository.findById(1)).thenReturn(Optional.of(nurse1));

    mockMvc.perform(get("/nurse/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.full_name").value("Ana Lopez"))
        .andExpect(jsonPath("$.user_name").value("analopez"));
  }

  @Test
  void getNurseById_notFound_shouldReturn404() throws Exception {
    when(nurseRepository.findById(99)).thenReturn(Optional.empty());

    mockMvc.perform(get("/nurse/{id}", 99))
        .andExpect(status().isNotFound());
  }

  // ---------- PUT /nurse/{id} ----------

  @Test
  void updateNurse_found_shouldUpdateAndReturnNurse() throws Exception {
    Nurse updated = new Nurse();
    updated.setId(1);
    updated.setFullName("Ana Garcia");
    updated.setUserName("anagarcia");
    updated.setPassword("nueva");

    when(nurseRepository.findById(1)).thenReturn(Optional.of(nurse1));
    when(nurseRepository.save(any(Nurse.class))).thenAnswer(inv -> inv.getArgument(0));

    mockMvc.perform(put("/nurse/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.full_name").value("Ana Garcia"))
        .andExpect(jsonPath("$.user_name").value("anagarcia"));

    verify(nurseRepository).save(nurse1);
  }

  @Test
  void updateNurse_notFound_shouldReturn404() throws Exception {
    Nurse updated = new Nurse();
    updated.setId(1);
    updated.setFullName("Ana Garcia");
    updated.setUserName("anagarcia");
    updated.setPassword("nueva");

    when(nurseRepository.findById(99)).thenReturn(Optional.empty());

    mockMvc.perform(put("/nurse/{id}", 99)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
        .andExpect(status().isNotFound());

    verify(nurseRepository, never()).save(any(Nurse.class));
  }

  // ---------- DELETE /nurse/{id} ----------

  @Test
  void deleteNurse_exists_shouldReturn204() throws Exception {
    when(nurseRepository.existsById(1)).thenReturn(true);

    mockMvc.perform(delete("/nurse/{id}", 1))
        .andExpect(status().isNoContent());

    verify(nurseRepository).deleteById(1);
  }

  @Test
  void deleteNurse_notExists_shouldReturn404() throws Exception {
    when(nurseRepository.existsById(99)).thenReturn(false);

    mockMvc.perform(delete("/nurse/{id}", 99))
        .andExpect(status().isNotFound());

    verify(nurseRepository, never()).deleteById(anyInt());
  }
}
