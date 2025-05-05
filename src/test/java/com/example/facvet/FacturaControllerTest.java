package com.example.facvet;

import com.example.facvet.controller.FacturaController;
import com.example.facvet.entity.Factura;
import com.example.facvet.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacturaControllerTest {

    private FacturaService facturaService;
    private FacturaController facturaController;

    @BeforeEach
    void setUp() {
        facturaService = Mockito.mock(FacturaService.class);
        facturaController = new FacturaController(facturaService);
    }

    @Test
    void testGetFacturaById() {
        Factura factura = new Factura();
        factura.setId(1L);
        when(facturaService.obtenerFacturaPorId(1L)).thenReturn(Optional.of(factura));

        ResponseEntity<EntityModel<Factura>> response = facturaController.obtenerFacturaPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getContent().getId());
        assertTrue(response.getBody().hasLink("self"));
    }

    @Test
    void testFacturaNotFound() {
        when(facturaService.obtenerFacturaPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<Factura>> response = facturaController.obtenerFacturaPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
