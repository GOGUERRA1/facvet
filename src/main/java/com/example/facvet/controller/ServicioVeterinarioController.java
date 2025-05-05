package com.example.facvet.controller;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.service.ServicioVeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioVeterinarioController {

    @Autowired
    private ServicioVeterinarioService service;

    @GetMapping
    public List<ServicioVeterinario> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioVeterinario> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicioVeterinario> crear(@RequestBody ServicioVeterinario nuevo) {
        if (nuevo.getNombre() == null || nuevo.getNombre().isBlank() || nuevo.getCosto() < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.crear(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioVeterinario> actualizar(@PathVariable Long id, @RequestBody ServicioVeterinario actualizado) {
        if (actualizado.getNombre() == null || actualizado.getNombre().isBlank() || actualizado.getCosto() < 0) {
            return ResponseEntity.badRequest().build();
        }
        ServicioVeterinario modificado = service.actualizar(id, actualizado);
        return modificado != null ? ResponseEntity.ok(modificado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
