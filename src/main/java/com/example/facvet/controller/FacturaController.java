package com.example.facvet.controller;

import com.example.facvet.model.Factura;
import com.example.facvet.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping
    public List<Factura> obtenerTodas() {
        List<Factura> facturas = service.obtenerTodas();
        for (Factura f : facturas) {
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(FacturaController.class).obtenerPorId(f.getId()))
                    .withSelfRel();
            f.add(selfLink);
        }
        return facturas;
    }

    @GetMapping("/{id}")
    public Factura obtenerPorId(@PathVariable Long id) {
        Factura f = service.obtenerPorId(id).orElse(null);
        if (f != null) {
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(FacturaController.class).obtenerPorId(id))
                    .withSelfRel();
            f.add(selfLink);
        }
        return f;
    }

    @PostMapping
    public Factura crear(@RequestBody Factura factura) {
        return service.crear(factura.getRutCliente(), factura.getIdsServicios());
    }

    @PutMapping("/{id}")
    public Factura actualizar(@PathVariable Long id, @RequestBody Factura factura) {
        return service.actualizar(id, factura);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
