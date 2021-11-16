package com.architecture.microserviciobdd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.architecture.microserviciobdd.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    @Query("select c from Cliente c where c.nombre=?1")
    Optional<Cliente> findByNombre(String nombre);
}
