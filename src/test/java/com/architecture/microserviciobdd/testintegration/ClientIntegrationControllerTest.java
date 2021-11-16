package com.architecture.microserviciobdd.testintegration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.architecture.microserviciobdd.models.ClienteDto;
import com.architecture.microserviciobdd.utils.Datos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ClientIntegrationControllerTest {
    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @LocalServerPort
    private int puerto;

    Datos datos;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        datos = new Datos();
    }

    @Test
    @Order(1)
    void testFindAll() throws JsonProcessingException {


        ResponseEntity<ClienteDto[]> responseClient = client.getForEntity(createUri("/api/clientes"),
                ClienteDto[].class);
        List<ClienteDto> clientList = Arrays.asList(responseClient.getBody());

        assertEquals(HttpStatus.OK, responseClient.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseClient.getHeaders().getContentType());

        assertEquals(3, clientList.size());
        assertEquals(1L, clientList.get(0).getId());
        assertEquals("Christian", clientList.get(0).getNombre());
        assertEquals("Meneses", clientList.get(0).getApellidos());
        assertEquals("M", clientList.get(0).getSexo());

        JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(clientList));
        assertEquals(2L, json.get(1).path("id").asLong());
        assertEquals("Juan", json.get(1).path("nombre").asText());
        assertEquals("Salgado", json.get(1).path("apellidos").asText());
        assertEquals("M", json.get(1).path("sexo").asText());
    }

    @Test
    @Order(2)
    void findById() {
        ResponseEntity<ClienteDto> responseClient = client.getForEntity(createUri("/api/clientes/3"), ClienteDto.class);
        ClienteDto client = responseClient.getBody();

        assertEquals(HttpStatus.OK, responseClient.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseClient.getHeaders().getContentType());

        assertNotNull(client);
        assertEquals(3L, client.getId());
        assertEquals("Sandra", client.getNombre());
        assertEquals("Martinez", client.getApellidos());
        assertEquals("F", client.getSexo());

    }

    @Test
    @Order(3)

    void testSave() {
        ClienteDto newClient = datos.createClientDto04();

        ResponseEntity<ClienteDto> responseSave = client.postForEntity(createUri("/api/clientes"), newClient,
                ClienteDto.class);

        assertEquals(HttpStatus.CREATED, responseSave.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseSave.getHeaders().getContentType());

        ClienteDto clientSave = responseSave.getBody();
        assertNotNull(clientSave);
        assertEquals(4L, clientSave.getId());
        assertEquals("Olivia", clientSave.getNombre());
        assertEquals("Olvera", clientSave.getApellidos());
        assertEquals("F", clientSave.getSexo());

    }

    private String createUri(String uri) {
        return "http://localhost:" + puerto + uri;
    }

    @Test
    @Order(4)
    void testUpdate() {

        ClienteDto updateClient = datos.updateClientDto04();
        updateClient.setApellidos("Olvera Sanchez");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(updateClient, headers);
        ResponseEntity<ClienteDto> responseUpdate = client.exchange(createUri("/api/clientes"), HttpMethod.PUT,
                requestUpdate, ClienteDto.class);

        assertEquals(HttpStatus.ACCEPTED, responseUpdate.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseUpdate.getHeaders().getContentType());

        ClienteDto clientSave = responseUpdate.getBody();
        assertNotNull(clientSave);
        assertEquals(4L, clientSave.getId());
        assertEquals("Olivia", clientSave.getNombre());
        assertEquals("Olvera Sanchez", clientSave.getApellidos());
        assertEquals("F", clientSave.getSexo());

    }

    @Test
    @Order(5)
    void testDeleteById() {

        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", 4L);

        ResponseEntity<Void> exchange = client.exchange(createUri("/api/clientes/{id}"), HttpMethod.DELETE, null,
                Void.class, pathVariables);
        assertEquals(HttpStatus.NO_CONTENT, exchange.getStatusCode());
        assertFalse(exchange.hasBody());

    }
}
