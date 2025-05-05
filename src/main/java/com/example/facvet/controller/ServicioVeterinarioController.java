package com.example.facvet.controller;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.service.ServicioVeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioVeterinarioController {

    @Autowired
    private ServicioVeterinarioService service;

    @GetMapping
    public List<EntityModel<ServicioVeterinario>> listar() {
        return service.obtenerTodos().stream()
                .map(s -> EntityModel.of(s,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicioVeterinarioController.class).obtener(s.getId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ServicioVeterinario>> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(s -> EntityModel.of(s,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicioVeterinarioController.class).obtener(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ServicioVeterinario>> crear(@RequestBody ServicioVeterinario nuevo) {
        if (nuevo.getNombre() == null || nuevo.getNombre().isBlank() || nuevo.getCosto() < 0) {
            return ResponseEntity.badRequest().build();
        }
        ServicioVeterinario creado = service.crear(nuevo);
        return ResponseEntity.ok(EntityModel.of(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ServicioVeterinario>> actualizar(@PathVariable Long id, @RequestBody ServicioVeterinario actualizado) {
        if (actualizado.getNombre() == null || actualizado.getNombre().isBlank() || actualizado.getCosto() < 0) {
            return ResponseEntity.badRequest().build();
        }
        ServicioVeterinario modificado = service.actualizar(id, actualizado);
        return modificado != null ? ResponseEntity.ok(EntityModel.of(modificado)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
