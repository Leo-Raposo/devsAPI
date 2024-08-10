package com.example.devs.devs.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mutante")
@Entity
public class MutanteModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String senha;

    private String nome;

    private String poder;

    private int idade;

    private int inimigosDerrotados;

    private boolean estaNaEscola;
}