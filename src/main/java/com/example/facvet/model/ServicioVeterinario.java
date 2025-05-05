package com.example.facvet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios_veterinarios")
public class ServicioVeterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private double costo;

    public ServicioVeterinario() {}

    public ServicioVeterinario(Long id, String nombre, String descripcion, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getCosto() { return costo; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCosto(double costo) { this.costo = costo; }
}
