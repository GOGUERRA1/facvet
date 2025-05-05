package com.example.facvet.repository;

import com.example.facvet.model.ServicioVeterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioVeterinarioRepository extends JpaRepository<ServicioVeterinario, Long> {
}
