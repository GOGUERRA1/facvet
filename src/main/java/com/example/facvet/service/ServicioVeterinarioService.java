package com.example.facvet.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Factura extends RepresentationModel<Factura> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    @ManyToMany
    @JoinTable(
        name = "factura_servicio",
        joinColumns = @JoinColumn(name = "factura_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<ServicioVeterinario> servicios;

    private int total;

    public Factura() {}

    public Factura(Long id, String cliente, List<ServicioVeterinario> servicios) {
        this.id = id;
        this.cliente = cliente;
        this.servicios = servicios;
        this.total = servicios.stream().mapToInt(ServicioVeterinario::getCosto).sum();
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public List<ServicioVeterinario> getServicios() {
        return servicios;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    // Agregar estos getters para HATEOAS y controller
    public String getRutCliente() {
        return this.cliente;
    }

    public List<Long> getIdsServicios() {
        return this.servicios.stream()
                             .map(ServicioVeterinario::getId)
                             .collect(Collectors.toList());
    }
}
