package com.example.facvet.service;

import com.example.facvet.model.Factura;
import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.repository.FacturaRepository;
import com.example.facvet.repository.ServicioVeterinarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    private FacturaRepository facturaRepo;
    private ServicioVeterinarioRepository servicioRepo;
    private FacturaService facturaService;

    @BeforeEach
    void setUp() {
        facturaRepo = mock(FacturaRepository.class);
        servicioRepo = mock(ServicioVeterinarioRepository.class);
        facturaService = new FacturaService();
        facturaService.facturaRepo = facturaRepo;
        facturaService.servicioRepo = servicioRepo;
    }

    @Test
    void testCrearFactura() {
        String cliente = "12345678-9";
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ServicioVeterinario> servicios = Arrays.asList(
                new ServicioVeterinario(1L, "Vacuna", "Gato", 10000),
                new ServicioVeterinario(2L, "Cirugía", "Perro", 30000)
        );

        when(servicioRepo.findAllById(ids)).thenReturn(servicios);
        when(facturaRepo.save(Mockito.any(Factura.class))).thenAnswer(inv -> inv.getArgument(0));

        Factura creada = facturaService.crear(cliente, ids);

        assertNotNull(creada);
        assertEquals(cliente, creada.getRutCliente());
        assertEquals(2, creada.getServicios().size());
        verify(facturaRepo, times(1)).save(any(Factura.class));
    }
}
