package com.architecture.microserviciobdd.testbdd;

import com.architecture.microserviciobdd.models.ClienteDto;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

//import static com.architecture.microserviciobdd.utils.Uri.createUri;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateClientSteps  {

    @Autowired
    private TestConfig uri;
    private RequestSpecification request;
    private Response response;

    @Given("que requiero actualizar el {string}, {string}, {string} de los clientes con Id {long}")
    public void que_requiero_actualizar(String nombre, String apellidos, String sexo, Long id) {
        ClienteDto updateClient = new ClienteDto(id, nombre, apellidos, sexo);
        request = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE).body(updateClient);
    }

    @When("ejecute la acción de actualizar")
    public void ejecute_la_acción_de_actualizar() {
       response = request.when().put(uri.getUri()+"/api/clientes");
    }

    @Then("se debe devolver un estado aceptado")
    public void se_debe_devolver_un_estado_aceptado() {
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode());
    }

    @And("el {string}, {string} y {string} de los clientes con Id {long} debió ser guardado en la Base de Datos")
    public void el_cliente_debio_ser_guardado_en_la_base_de_datos(String nombre, String apellidos, String sexo, Long id) {
        ClienteDto client = response.getBody().as(ClienteDto.class);
        assertNotNull(client);
        assertEquals(id, client.getId());
        assertEquals(nombre, client.getNombre());
        assertEquals(apellidos, client.getApellidos());
        assertEquals(sexo, client.getSexo());
    }

}
