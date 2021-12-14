package com.architecture.microserviciobdd.testbdd;

import com.architecture.microserviciobdd.models.ClienteDto;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

//import static com.architecture.microserviciobdd.utils.Uri.createUri;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveClientSteps  {

    @Autowired
    private TestConfig uri;
    private RequestSpecification request;
    private Response response;

    @Given("que requiero dar de alta un cliente con el {string}, {string} y {string}")
    public void que_requiero_dar_de_alta_un_cliente(String nombre, String apellidos, String sexo) {
        ClienteDto newClient = new ClienteDto(null, nombre, apellidos, sexo);
        request = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE).body(newClient);
    }

    @When("ejecute la acción de guardar")
    public void ejecute_la_acción_de_guardar() {
        System.out.println("url:"+uri.getUri() +"/api/clientes");

        response = request.when().post(uri.getUri()+"/api/clientes");
    }

    @Then("se debe devolver un estado creado")
    public void se_debe_devolver_un_estado_creado() {
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @And("el {string}, {string} y {string} debió ser guardado en la Base de Datos")
    public void y_debe_ser_guardado_en_la_base_de_datos(String nombre, String apellidos, String sexo) {
        ClienteDto client = response.getBody().as(ClienteDto.class);
        assertNotNull(client);
        assertEquals(nombre, client.getNombre());
        assertEquals(apellidos, client.getApellidos());
        assertEquals(sexo, client.getSexo());
    }

}
