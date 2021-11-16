package com.architecture.microserviciobdd.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.architecture.microserviciobdd.services.ClienteService;
import com.architecture.microserviciobdd.models.ClienteDto;

import io.swagger.annotations.*;

@Api
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ApiOperation(value = "findall", nickname = "findall", notes = "Return all client")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public List<ClienteDto> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "findbyid", nickname = "findbyid", response = ClienteDto.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public ClienteDto findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "save", nickname = "save", response = ClienteDto.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public ClienteDto save(@RequestBody ClienteDto cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping
    @ResponseStatus(ACCEPTED)
    @ApiOperation(value = "update", nickname = "update", response = ClienteDto.class)
    @ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public ClienteDto update(@RequestBody ClienteDto cliente) {
        return clienteService.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "delete", nickname = "delete", response = ClienteDto.class)
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure") })
    public void delete(@PathVariable Long id) {
        clienteService.deleteById(id);

    }
}
