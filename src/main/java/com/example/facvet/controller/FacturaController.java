package com.example.facvet.controller;

import com.example.facvet.model.Factura;
import com.example.facvet.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping
    public List<Factura> listarFacturas() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO data) {
        if (data.getCliente() == null || data.getCliente().isBlank() || data.getIdsServicios() == null || data.getIdsServicios().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Factura nueva = service.crear(data.getCliente(), data.getIdsServicios());
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Factura> pagarFactura(@PathVariable Long id) {
        Factura pagada = service.pagarFactura(id);
        return pagada != null ? ResponseEntity.ok(pagada) : ResponseEntity.notFound().build();
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