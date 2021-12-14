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

import java.util.List;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllClientSteps   {

    @Autowired
    private TestConfig uri;
    private RequestSpecification request;
    private Response response;

    @Given("que requiero consultar la información de todos los clientes")
    public void que_requiero_consultar_la_información_de_todos_los_clientes() {
        request = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @When("ejecute la acción de obtener los clientes")
    public void ejecute_la_acción_de_obtener_los_clientes() {
        response = request.when().get(uri.getUri()+"/api/clientes");
    }

    @Then("se debe devolver un estado ok")
    public void se_debe_devolver_un_estado_ok() {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @And("se debe proporcionar la información de todos los clientes")
    public void se_debe_proporcionar_la_información_de_todos_los_clientes() {
        assertNotNull(response.getBody().asString());
        List<ClienteDto> clientList = response.jsonPath().getList("$");
        assertTrue(clientList.size() > 0);
    }

}

