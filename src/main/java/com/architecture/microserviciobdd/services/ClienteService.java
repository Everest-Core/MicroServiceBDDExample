package com.architecture.microserviciobdd.services;

import java.util.List;
import com.architecture.microserviciobdd.models.*;
public interface ClienteService {
    ClienteDto findById(Long id);

    List<ClienteDto> findAll();

    ClienteDto save(ClienteDto cliente);

    void deleteById(Long id);
}
