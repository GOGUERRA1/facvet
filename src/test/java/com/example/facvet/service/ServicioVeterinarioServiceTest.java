package com.example.facvet.service;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.repository.ServicioVeterinarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioVeterinarioServiceTest {

    private ServicioVeterinarioRepository repository;
    private ServicioVeterinarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(ServicioVeterinarioRepository.class);
        service = new ServicioVeterinarioService();
        service.repository = repository;
    }

    @Test
    void testCrearServicio() {
        ServicioVeterinario nuevo = new ServicioVeterinario(null, "Consulta", "General", 15000);
        when(repository.save(nuevo)).thenReturn(new ServicioVeterinario(1L, "Consulta", "General", 15000));

        ServicioVeterinario creado = service.crear(nuevo);
        assertNotNull(creado);
        assertEquals("Consulta", creado.getNombre());
        verify(repository, times(1)).save(nuevo);
    }

    @Test
    void testActualizarServicioExistente() {
        ServicioVeterinario original = new ServicioVeterinario(1L, "Consulta", "General", 15000);
        ServicioVeterinario actualizado = new ServicioVeterinario(null, "Cirugía", "Mayor", 50000);

        when(repository.findById(1L)).thenReturn(Optional.of(original));
        when(repository.save(any())).thenReturn(actualizado);

        ServicioVeterinario result = service.actualizar(1L, actualizado);

        assertNotNull(result);
        assertEquals("Cirugía", result.getNombre());
        verify(repository).save(any());
    }
}
