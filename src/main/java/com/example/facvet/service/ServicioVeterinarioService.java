package com.example.facvet.service;

import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.repository.ServicioVeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioVeterinarioService {

    @Autowired
    private ServicioVeterinarioRepository repository;

    public List<ServicioVeterinario> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<ServicioVeterinario> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public ServicioVeterinario crear(ServicioVeterinario servicio) {
        return repository.save(servicio);
    }

    public ServicioVeterinario actualizar(Long id, ServicioVeterinario nuevo) {
        return repository.findById(id)
                .map(s -> {
                    s.setNombre(nuevo.getNombre());
                    s.setDescripcion(nuevo.getDescripcion());
                    s.setCosto(nuevo.getCosto());
                    return repository.save(s);
                })
                .orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
