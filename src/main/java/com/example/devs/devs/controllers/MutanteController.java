package com.example.devs.devs.controllers;


import com.example.devs.devs.models.MutanteModel;
import com.example.devs.devs.repositories.MutanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/mutantes")
public class MutanteController {

    @Autowired
    private MutanteRepository mutanteRepository;

    @GetMapping
    public ResponseEntity<List<MutanteModel>> getAllMutantes(){
        return ResponseEntity.status(HttpStatus.OK).body(mutanteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMutanteById(@PathVariable(value = "id") UUID id) {
        Optional<MutanteModel> mutante0 = mutanteRepository.findById(id);
        if(mutante0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutante não encontrado");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(mutante0.get());
        }
    }

}