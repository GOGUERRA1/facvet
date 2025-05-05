package com.example.facvet.controller;

import com.example.facvet.model.Factura;
import com.example.facvet.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping
    public List<EntityModel<Factura>> listarFacturas() {
        return service.obtenerTodas().stream()
                .map(f -> EntityModel.of(f,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FacturaController.class).obtenerPorId(f.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FacturaController.class).listarFacturas()).withRel("facturas")))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(f -> EntityModel.of(f,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FacturaController.class).obtenerPorId(id)).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FacturaController.class).listarFacturas()).withRel("facturas")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody FacturaDTO data) {
        if (data.getCliente() == null || data.getCliente().isBlank() || data.getIdsServicios() == null || data.getIdsServicios().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Factura nueva = service.crear(data.getCliente(), data.getIdsServicios());
        EntityModel<Factura> model = EntityModel.of(nueva,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FacturaController.class).obtenerPorId(nueva.getId())).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<EntityModel<Factura>> pagarFactura(@PathVariable Long id) {
        Factura pagada = service.pagarFactura(id);
        if (pagada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(EntityModel.of(pagada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // DTO interno
    public static class FacturaDTO {
        private String cliente;
        private List<Long> idsServicios;

        public String getCliente() { return cliente; }
        public void setCliente(String cliente) { this.cliente = cliente; }
        public List<Long> getIdsServicios() { return idsServicios; }
        public void setIdsServicios(List<Long> idsServicios) { this.idsServicios = idsServicios; }
    }
}
