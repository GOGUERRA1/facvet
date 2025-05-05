package com.example.facvet;

import com.example.facvet.controller.ServicioVeterinarioController;
import com.example.facvet.entity.ServicioVeterinario;
import com.example.facvet.service.ServicioVeterinarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioVeterinarioControllerTest {

    private ServicioVeterinarioService servicioService;
    private ServicioVeterinarioController servicioController;

    @BeforeEach
    void setUp() {
        servicioService = Mockito.mock(ServicioVeterinarioService.class);
        servicioController = new ServicioVeterinarioController(servicioService);
    }

    @Test
    void testGetServicioById() {
        ServicioVeterinario servicio = new ServicioVeterinario();
        servicio.setId(1L);
        when(servicioService.obtenerServicioPorId(1L)).thenReturn(Optional.of(servicio));

        ResponseEntity<EntityModel<ServicioVeterinario>> response = servicioController.obtenerServicioPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getContent().getId());
        assertTrue(response.getBody().hasLink("self"));
    }

    @Test
    void testServicioNotFound() {
        when(servicioService.obtenerServicioPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<ServicioVeterinario>> response = servicioController.obtenerServicioPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
