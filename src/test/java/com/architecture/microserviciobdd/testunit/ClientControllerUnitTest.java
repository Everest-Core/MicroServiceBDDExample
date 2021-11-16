package com.architecture.microserviciobdd.testunit;

import com.architecture.microserviciobdd.utils.Datos;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.architecture.microserviciobdd.controller.ClienteController;
import com.architecture.microserviciobdd.models.ClienteDto;
import com.architecture.microserviciobdd.services.ClienteService;

@WebMvcTest(ClienteController.class)
class ClientControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    ObjectMapper objectMapper;

    Datos datos;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        datos = new Datos();
    }

    @Test

    void testFindAll() throws Exception {

        List<ClienteDto> clienteList = new ArrayList<ClienteDto>();


        clienteList = datos.listClientDto();
        // Given
        when(clienteService.findAll()).thenReturn(clienteList);

        // When
        mvc.perform(get("/api/clientes").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Christian")).andExpect(jsonPath("$[1].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellidos").value("Meneses"))
                .andExpect(jsonPath("$[1].apellidos").value("Salgado")).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(clienteList)));

        verify(clienteService).findAll();

    }

    @Test
    void testFindById() throws Exception {
        ClienteDto client = new ClienteDto(datos.createClient01().orElseThrow());

        // Given
        when(clienteService.findById(1L)).thenReturn(client);

        // When
        mvc.perform(get("/api/clientes/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Christian")).andExpect(jsonPath("$.apellidos").value("Meneses"))
                .andExpect(jsonPath("$.sexo").value("M"));

        verify(clienteService).findById(1L);
    }

    @Test
    void testSave() throws Exception {

        // Given
        ClienteDto newClient = datos.createClientDto04();

        when(clienteService.save(any())).then(invocation -> {
            ClienteDto client = invocation.getArgument(0);
            client.setId(4L);
            return client;
        });

        // when
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newClient)))
                // Then
                .andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4))).andExpect(jsonPath("$.nombre", is("Olivia")))
                .andExpect(jsonPath("$.apellidos", is("Olvera"))).andExpect(jsonPath("$.sexo", is("F")));

        verify(clienteService).save(any());
    }

    @Test
    void testUpdate() throws Exception {

        // Given
        ClienteDto updateClient = datos.updateClientDto04();

        when(clienteService.save(any())).then(invocation -> {
            ClienteDto client = invocation.getArgument(0);
            client.setNombre("Olivia Aide");
            client.setApellidos("Olvera Sanchez");
            client.setSexo("F");
            return client;
        });

        // when
        mvc.perform(put("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateClient)))
                // Then
                .andExpect(status().isAccepted()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(4))).andExpect(jsonPath("$.nombre", is("Olivia Aide")))
                .andExpect(jsonPath("$.apellidos", is("Olvera Sanchez"))).andExpect(jsonPath("$.sexo", is("F")));

        verify(clienteService).save(any());
    }

    @Test
    void testDelete() throws Exception {

        mvc.perform(delete("/api/clientes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(clienteService, times(1)).deleteById(1L);
    }
}
