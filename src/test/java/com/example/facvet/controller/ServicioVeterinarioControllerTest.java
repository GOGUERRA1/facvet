
package com.example.facvet.controller;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.service.ServicioVeterinarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicioVeterinarioController.class)
public class ServicioVeterinarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioVeterinarioService servicioVeterinarioService;

    @Test
    void testListarServicios() throws Exception {
        ServicioVeterinario servicio = new ServicioVeterinario();
        servicio.setId(1L);
        servicio.setNombre("Consulta General");

        when(servicioVeterinarioService.obtenerTodos()).thenReturn(Collections.singletonList(servicio));

        mockMvc.perform(get("/api/servicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Consulta General"));
    }
}
