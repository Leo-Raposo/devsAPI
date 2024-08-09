package com.example.devs.devs.controllers;


import com.example.devs.devs.repositories.MutanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutantes")
public class MutanteController {

    @Autowired
    private MutanteRepository mutanteRepository;

}