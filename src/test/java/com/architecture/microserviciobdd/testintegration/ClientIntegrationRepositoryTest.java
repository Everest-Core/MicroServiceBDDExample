package com.architecture.microserviciobdd.testintegration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.architecture.microserviciobdd.models.Cliente;
import com.architecture.microserviciobdd.repositories.ClienteRepository;
import com.architecture.microserviciobdd.utils.Datos;

@DataJpaTest
class ClientIntegrationRepositoryTest {
    @Autowired
    ClienteRepository clienteRepository;

    @Test
    @Order(1)
    void testFindById() {
        Optional<Cliente> client = clienteRepository.findById(1L);
        assertTrue(client.isPresent());
        assertEquals("Christian", client.orElseThrow().getNombre());
    }

    @Test
    @Order(2)
    void testFindByNombre() {
        Datos datos = new Datos();
        Optional<Cliente> clientOptional = clienteRepository.findByNombre("Christian");
        Cliente client = clientOptional.orElseThrow();
        Cliente clientChristian = datos.createClient01().orElseThrow();
        assertEquals("Christian", client.getNombre());
        assertEquals("Meneses", client.getApellidos());
        assertEquals("M", client.getSexo());
        assertTrue(client.equals(clientChristian));
        assertTrue(client.hashCode() == clientChristian.hashCode());
    }

    @Test
    @Order(3)
    void testFindByNombreThrowException() {
        Optional<Cliente> client = clienteRepository.findByNombre("Ernesto");
        assertThrows(NoSuchElementException.class, client::orElseThrow);
        assertFalse(client.isPresent());
    }

    @Test
    @Order(4)
    void testFindAll() {
        List<Cliente> clients = clienteRepository.findAll();
        assertFalse(clients.isEmpty());
        assertEquals(3, clients.size());
    }

    @Test
    @Order(5)
    void testSave() {

        // Given
        Cliente client = new Cliente(null, "Olivia", "Olvera", "F");

        // When
        Cliente clientSave = clienteRepository.save(client);

        // Then
        assertEquals("Olivia", clientSave.getNombre());
        assertEquals("Olvera", clientSave.getApellidos());
        assertEquals("F", clientSave.getSexo());

    }

    @Test
    @Order(6)
    void testUpdate() {
        // Given
        Cliente client = new Cliente(1L, "Christian", "Meneses Sanchez", "F");

        // When
        Cliente clientSave = clienteRepository.save(client);

        // Then
        assertEquals(client.getNombre(), clientSave.getNombre());
        assertEquals(client.getApellidos(), clientSave.getApellidos());

    }

    @Test
    @Order(7)
    void testDeleteById() {
        clienteRepository.deleteById(2L);

        Optional<Cliente> client = clienteRepository.findById(2L);
        assertThrows(NoSuchElementException.class, client::orElseThrow);
        assertFalse(client.isPresent());
        assertEquals(2, clienteRepository.findAll().size());

    }
}
