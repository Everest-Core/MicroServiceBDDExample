package com.architecture.microserviciobdd.testbdd;

import com.architecture.microserviciobdd.models.ClienteDto;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.architecture.microserviciobdd.utils.Uri.createUri;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetByIdClientDteps {
    //test 1
    private RequestSpecification request;
    private Response response;

    @Given("dado que requiero consultar la información del cliente")
    public void dado_que_requiero_consultar_la_información_del_cliente() {
        request = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @When("ejecute la accion de consultar por Id {long}")
    public void ejecute_la_acción_de_consultar_por_id(Long id) {
        System.out.println("/api/clientes/" + id);
        response = request.when().get(createUri("/api/clientes/" + id));
    }

    @Then("se debe obtener un estado ok")
    public void se_debe_obtener_un_estado_ok() {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @And("debe proporcionarse el {string}, {string}, {string} del cliente con Id {long}")
    public void debe_proporcionarse_la_información_del_cliente_con_Id(String nombre, String apellidos, String sexo, Long id) {
        ClienteDto client = response.getBody().as(ClienteDto.class);
        assertNotNull(client);
        assertEquals(id, client.getId());
        assertEquals(nombre, client.getNombre());
        assertEquals(apellidos, client.getApellidos());
        assertEquals(sexo, client.getSexo());
    }

}
