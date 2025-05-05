package com.example.facvet.service;

import com.example.facvet.model.Factura;
import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.repository.FacturaRepository;
import com.example.facvet.repository.ServicioVeterinarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    @InjectMocks
    private FacturaService facturaService;

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private ServicioVeterinarioRepository servicioVeterinarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearFactura() {
        Factura factura = new Factura();
        factura.setCliente("Juan");
        ServicioVeterinario s1 = new ServicioVeterinario(1L, "Consulta", "Revisión general", 10000.0);
        List<Long> idsServicios = List.of(1L);
        when(servicioVeterinarioRepository.findById(1L)).thenReturn(Optional.of(s1));
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        Factura result = facturaService.crearFactura("Juan", idsServicios);

        assertNotNull(result);
        assertEquals("Juan", result.getCliente());
        assertEquals(1, result.getServicios().size());
    }

    @Test
    void testListarFacturas() {
        List<Factura> lista = List.of(new Factura());
        when(facturaRepository.findAll()).thenReturn(lista);
        List<Factura> result = facturaService.listarFacturas();
        assertEquals(1, result.size());
    }
}
