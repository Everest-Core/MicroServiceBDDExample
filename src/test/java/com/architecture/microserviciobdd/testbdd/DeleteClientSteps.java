package com.architecture.microserviciobdd.testbdd;

import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteClientSteps {

    @Autowired
    private TestConfig uri;
    private RequestSpecification request;
    private Response response;

    @Given("dado que requiero eliminar clientes")
    public void dado_que_requiero_eliminar_clientes() {
        request = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @When("ejecute la accion de eliminar por el Id {long}")
    public void ejecute_la_acción_de_eliminar_por_id(Long id) {
       response = request.when().delete(uri.getUri()+"/api/clientes/" + id);
    }

    @Then("se debe devolver un estado de no contenido")
    public void se_debe_devolver_un_estado_no_contenido() {
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
    }

}
