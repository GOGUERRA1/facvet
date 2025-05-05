package com.example.facvet.service;

import com.example.facvet.model.Factura;
import com.example.facvet.model.ServicioVeterinario;
import com.example.facvet.repository.FacturaRepository;
import com.example.facvet.repository.ServicioVeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ServicioVeterinarioRepository servicioVeterinarioRepository;

    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerPorId(Long id) {
        return facturaRepository.findById(id);
    }

    public Factura guardar(Factura factura) {
        return facturaRepository.save(factura);
    }

    public void eliminar(Long id) {
        facturaRepository.deleteById(id);
    }

    public Factura crearFactura(String cliente, List<Long> idsServicios) {
        List<ServicioVeterinario> servicios = servicioVeterinarioRepository.findAllById(idsServicios);
        Factura nuevaFactura = new Factura();
        nuevaFactura.setCliente(cliente);
        nuevaFactura.setServicios(servicios);
        return facturaRepository.save(nuevaFactura);
    }

    public String resumenFactura(Factura nuevaFactura) {
        double total = nuevaFactura.getServicios().stream().mapToDouble(ServicioVeterinario::getCosto).sum();
        return String.format("Cliente: %s - Servicios: %s - Total: %.2f",
                nuevaFactura.getCliente(),
                nuevaFactura.getServicios().stream().map(ServicioVeterinario::getId).toList(),
                total);
    }
}
