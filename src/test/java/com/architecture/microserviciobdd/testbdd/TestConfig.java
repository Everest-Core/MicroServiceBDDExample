package com.architecture.microserviciobdd.testbdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@CucumberContextConfiguration
public class TestConfig {

    @Value("${url.test.bdd}")
    private String uri;

    public String getUri() {
        return uri;
    }
}
