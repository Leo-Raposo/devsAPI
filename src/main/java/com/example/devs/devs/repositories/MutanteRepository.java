package com.example.devs.devs.repositories;

import com.example.devs.devs.models.MutanteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MutanteRepository extends JpaRepository<MutanteModel, UUID> {
    List<MutanteModel> findByEstaNaEscola(boolean estaNaEscola);
    Long countByEstaNaEscola(boolean estaNaEscola);
}