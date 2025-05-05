package com.example.facvet.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cliente;

    @ManyToMany
    @JoinTable(
        name = "factura_servicio",
        joinColumns = @JoinColumn(name = "factura_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<ServicioVeterinario> servicios;

    private double total;

    public Factura() {}

    public Factura(Long id, String cliente, List<ServicioVeterinario> servicios) {
        this.id = id;
        this.cliente = cliente;
        this.servicios = servicios;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return servicios != null
            ? servicios.stream().mapToDouble(ServicioVeterinario::getCosto).sum()
            : 0;
    }

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public List<ServicioVeterinario> getServicios() { return servicios; }
    public double getTotal() { return total; }

    public void setId(Long id) { this.id = id; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setServicios(List<ServicioVeterinario> servicios) {
        this.servicios = servicios;
        this.total = calcularTotal();
    }
    public void setTotal(double total) { this.total = total; }
}
