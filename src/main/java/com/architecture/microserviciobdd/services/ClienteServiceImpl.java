package com.architecture.microserviciobdd.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.architecture.microserviciobdd.models.*;
import com.architecture.microserviciobdd.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteDto findById(Long id) {

       Cliente cliente = clienteRepository.findById(id).orElseThrow();
        return new ClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> findAll() {

        List<ClienteDto> listClientDto = new ArrayList<>();
        List<Cliente> listClient = clienteRepository.findAll();

        listClient.stream().forEach(p ->
            listClientDto.add(new ClienteDto(p.getId(), p.getNombre(), p.getApellidos(), p.getSexo())));

        return listClientDto;
    }

    @Override
    public ClienteDto save(ClienteDto cliente) {


        return new ClienteDto(clienteRepository
                .save(new Cliente(cliente.getId(), cliente.getNombre(), cliente.getApellidos(), cliente.getSexo())));

    }

    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

}
