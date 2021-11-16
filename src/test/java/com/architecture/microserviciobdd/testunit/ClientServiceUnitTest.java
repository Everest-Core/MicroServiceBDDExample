package com.architecture.microserviciobdd.testunit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.architecture.microserviciobdd.models.*;
import com.architecture.microserviciobdd.repositories.ClienteRepository;
import com.architecture.microserviciobdd.services.ClienteService;
import com.architecture.microserviciobdd.utils.Datos;

@SpringBootTest
class ClientServiceUnitTest {

    @MockBean
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService service;

    Datos datos;

    @BeforeEach
    void setUp() {
        datos = new Datos();
    }

    @Test
    void testFindById() {

        doReturn(datos.createClient01()).when(clienteRepository).findById(1L);

        ClienteDto client = service.findById(1L);

        assertEquals("Christian", client.getNombre());
        assertEquals("Meneses", client.getApellidos());
        assertEquals("M", client.getSexo());

        verify(clienteRepository, times(1)).findById(1L);

    }

    @Test
    void testFindAll() {
        doReturn(datos.listClient()).when(clienteRepository).findAll();

        List<ClienteDto> clientList = service.findAll();
        assertEquals(3, clientList.size());
        verify(clienteRepository, times(1)).findAll();

    }

    @Test
    void testSave() {
        // given
        ClienteDto newClient = datos.createClientDto04();

        when(clienteRepository.save(any())).then(invocation -> {
            Cliente client = invocation.getArgument(0);
            client.setId(4L);
            return client;
        });

        // when
        ClienteDto clientSave = service.save(newClient);
        // then
        assertEquals("Olivia", clientSave.getNombre());
        assertEquals(4, clientSave.getId());
        verify(clienteRepository).save(any());

    }

    @Test
    void testUpdate() {
        // given
        ClienteDto updateClient = datos.updateClientDto04();
        when(clienteRepository.save(any())).then(invocation -> {
            Cliente client = invocation.getArgument(0);
            client.setApellidos("Olvera Sanchez");
            client.setNombre("Olivia Aide");
            client.setSexo("F");
            return client;
        });
        // when
        ClienteDto clientSave = service.save(updateClient);
        // then
        assertEquals("Olvera Sanchez", clientSave.getApellidos());
        assertEquals("Olivia Aide", clientSave.getNombre());
        assertEquals("F", clientSave.getSexo());
        assertFalse(clientSave.equals(updateClient));
        assertFalse(clientSave.hashCode()==updateClient.hashCode());
        verify(clienteRepository).save(any());
    }

    @Test
    void testDeleteById() {
        service.deleteById(4L);
        verify(clienteRepository, times(1)).deleteById(4L);
    }

}
