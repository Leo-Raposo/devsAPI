package com.example.devs.devs.repositories;

import com.example.devs.devs.models.MutanteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MutanteRepository extends JpaRepository<MutanteModel, Long> {
    List<MutanteModel> findByEstaNaEscola(boolean estaNaEscola);
}