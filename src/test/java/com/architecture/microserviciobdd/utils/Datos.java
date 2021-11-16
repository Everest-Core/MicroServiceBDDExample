package com.architecture.microserviciobdd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.architecture.microserviciobdd.models.Cliente;
import com.architecture.microserviciobdd.models.ClienteDto;

public class Datos {
    public  Optional<Cliente> createClient01() {
        return Optional.ofNullable(new Cliente(1L, "Christian", "Meneses", "M"));
    }

    public  Optional<Cliente> createClient02() {
        return Optional.ofNullable(new Cliente(2L, "Juan", "Salgado", "M"));

    }

    public  Optional<Cliente> createClient03() {
        return Optional.ofNullable(new Cliente(3L, "Sandra", "Martinez", "F"));

    }

    public  ClienteDto updateClientDto04() {
        return new ClienteDto(4L, "Olivia", "Olvera", "F");
    }
    public  ClienteDto createClientDto04() {
        return new ClienteDto(null, "Olivia", "Olvera", "F");
    }

    public  List<Cliente> listClient() {
        List<Cliente> client = new ArrayList<Cliente>();
        client.add(createClient01().orElseThrow());
        client.add(createClient02().orElseThrow());
        client.add(createClient03().orElseThrow());
        return client;
    }

    public  List<ClienteDto> listClientDto() {
        List<ClienteDto> client = new ArrayList<ClienteDto>();
        client.add(new ClienteDto(createClient01().orElseThrow()));
        client.add(new ClienteDto(createClient02().orElseThrow()));
        client.add(new ClienteDto(createClient03().orElseThrow()));
        return client;
    }
}
