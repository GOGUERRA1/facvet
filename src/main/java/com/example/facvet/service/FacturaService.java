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
    private FacturaRepository facturaRepo;

    @Autowired
    private ServicioVeterinarioRepository servicioRepo;

    public List<Factura> obtenerTodas() {
        return facturaRepo.findAll();
    }

    public Optional<Factura> obtenerPorId(Long id) {
        return facturaRepo.findById(id);
    }

    public Factura crear(String rutCliente, List<Long> idsServicios) {
        List<ServicioVeterinario> servicios = servicioRepo.findAllById(idsServicios);
        Factura factura = new Factura(null, rutCliente, servicios);
        int total = servicios.stream().mapToInt(ServicioVeterinario::getCosto).sum();
        factura.setTotal(total);
        return facturaRepo.save(factura);
    }

    public Factura actualizar(Long id, Factura nuevaFactura) {
        return facturaRepo.findById(id)
                .map(f -> {
                    f.setRutCliente(nuevaFactura.getRutCliente());
                    List<ServicioVeterinario> servicios = servicioRepo.findAllById(nuevaFactura.getIdsServicios());
                    f.setServicios(servicios);
                    int total = servicios.stream().mapToInt(ServicioVeterinario::getCosto).sum();
                    f.setTotal(total);
                    return facturaRepo.save(f);
                })
                .orElse(null);
    }

    public Factura pagarFactura(Long id) {
        Optional<Factura> optFactura = facturaRepo.findById(id);
        if (optFactura.isPresent()) {
            Factura f = optFactura.get();
            f.setTotal(0); // Simulación de pago
            return facturaRepo.save(f);
        }
        return null;
    }

    public void eliminar(Long id) {
        facturaRepo.deleteById(id);
    }
}
