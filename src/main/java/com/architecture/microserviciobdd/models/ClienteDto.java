package com.architecture.microserviciobdd.models;

import java.util.Objects;

public class ClienteDto {
    private Long id;
    private String nombre;
    private String apellidos;
    private String sexo;

    public ClienteDto() {
    }

    public ClienteDto(Long id, String nombre, String apellidos, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
    }

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.sexo = cliente.getSexo();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDto that = (ClienteDto) o;
        return id.equals(that.id) && nombre.equals(that.nombre) && apellidos.equals(that.apellidos) && sexo.equals(that.sexo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, sexo);
    }
}
