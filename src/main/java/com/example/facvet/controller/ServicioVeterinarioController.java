package com.example.facvet.controller;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.service.ServicioVeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/servicios")
public class ServicioVeterinarioController {

    @Autowired
    private ServicioVeterinarioService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ServicioVeterinario>>> listar() {
        List<EntityModel<ServicioVeterinario>> servicios = service.obtenerTodos().stream()
                .map(servicio -> EntityModel.of(servicio,
                        linkTo(methodOn(ServicioVeterinarioController.class).obtener(servicio.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(servicios, linkTo(methodOn(ServicioVeterinarioController.class).listar()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ServicioVeterinario>> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(servicio -> {
                    EntityModel<ServicioVeterinario> resource = EntityModel.of(servicio);
                    resource.add(linkTo(methodOn(ServicioVeterinarioController.class).obtener(id)).withSelfRel());
                    resource.add(linkTo(methodOn(ServicioVeterinarioController.class).listar()).withRel("servicios"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicioVeterinario> crear(@RequestBody ServicioVeterinario servicio) {
        return ResponseEntity.ok(service.guardar(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioVeterinario> actualizar(@PathVariable Long id, @RequestBody ServicioVeterinario servicio) {
        return ResponseEntity.ok(service.actualizar(id, servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
