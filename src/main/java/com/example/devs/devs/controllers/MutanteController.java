package com.example.devs.devs.controllers;


import com.example.devs.devs.dtos.MutanteRecordDto;
import com.example.devs.devs.models.MutanteModel;
import com.example.devs.devs.repositories.MutanteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

@RestController
@RequestMapping("/mutantes")
public class MutanteController {

    @Autowired
    private MutanteRepository mutanteRepository;

    private static final String CORRECT_PASSWORD = "apocalipse";

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

    @GetMapping("/convocacao-espada/{id}")
    public ResponseEntity<Boolean> verificaConvocacaoESPADA(@PathVariable(value = "id") UUID id){
        Optional<MutanteModel> mutante = mutanteRepository.findById(id);
        if (mutante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } else {
            MutanteModel m = mutante.get();
            int alienigenas = (int) (m.getInimigosDerrotados() * 0.268);
            return ResponseEntity.ok(alienigenas > 20);
        }
    }

    @GetMapping("/alistar-espada/{id}")
    public ResponseEntity<String> verificaAlistamentoESPADA(@PathVariable(value = "id") UUID id) {
        Optional<MutanteModel> mutante = mutanteRepository.findById(id);
        if (mutante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutante não encontrado");
        } else  {
            MutanteModel mutanteModel = mutante.get();
            int aliens = (int) (mutanteModel.getInimigosDerrotados() * 0.268);
            if (aliens > 20) {
                return ResponseEntity.ok("O mutante "+ mutanteModel.getNome() + " deve se cadastrar à organização ESPADA");
            } else  {
                return  ResponseEntity.ok("O mutante "+ mutanteModel.getNome() + " não deve se cadastrar à organização ESPADA");
            }
        }
    }



    @GetMapping("/calcular-inimigos/{id}")
    public ResponseEntity<String> getCalcularInimigos(@PathVariable(value = "id") UUID id) {
        Optional<MutanteModel> mutanteO = mutanteRepository.findById(id);
        
        if (mutanteO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutante não encontrado");
        }
        
        MutanteModel mutante = mutanteO.get();
        int totalInimigos = mutante.getInimigosDerrotados();
        
        int alienigenasDerrotados = (int) (totalInimigos * 0.268);
        int demoniosDerrotados = (int) (totalInimigos * 0.432);
        int outrosInimigosDerrotados = totalInimigos - (alienigenasDerrotados + demoniosDerrotados);

        String resposta = String.format("Inimigos derrotados da semana: Alienígenas: %d | Demônios: %d | Outross: %d",
                alienigenasDerrotados, demoniosDerrotados, outrosInimigosDerrotados);

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @GetMapping("/quantidade-mutantes-na-escola")
    public ResponseEntity<Long> countMutantesNaEscola() {
        Long count = mutanteRepository.countByInSchool(true);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @GetMapping("/lista-mutantes-na-escola")
    public ResponseEntity<List<MutanteModel>> listMutantesNaEscola() {
        List<MutanteModel> mutantes = mutanteRepository.findByEstaNaEscola(true);
        return ResponseEntity.status(HttpStatus.OK).body(mutantes);
    }



    @PostMapping("cadastrar")
    public ResponseEntity<String> cadastrarMutante(@RequestBody @Valid MutanteRecordDto mutanteDto) {
        MutanteModel mutanteModel = new MutanteModel();
        BeanUtils.copyProperties(mutanteDto, mutanteModel);

        mutanteRepository.save(mutanteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                String.format("Mutante registrado com sucesso! ID: %s",
                        mutanteModel.getId().toString()));
    }

    @PostMapping("/saida-escola/{id}")
    public ResponseEntity<String> saidaEscola(@PathVariable(value = "id") UUID id) {
        MutanteModel mutanteModel = mutanteRepository.findById(id).orElseThrow();
        mutanteRepository.save(mutanteModel);

        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("Mutante %s saiu da escola", mutanteModel.getNome()));
    }

    @PostMapping("/entrada-com-senha")
    public ResponseEntity<String> entraComSenha(@RequestParam UUID id, @RequestParam String senha) {
        if (!CORRECT_PASSWORD.equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }
        Optional<MutanteModel> mutanteModel = mutanteRepository.findById(id);
        if (mutanteModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mutante não encontrado");
        } else {
            MutanteModel m = mutanteModel.get();
            m.setEstaNaEscola(true);
            mutanteRepository.save(m);
            return ResponseEntity.ok("Entrada permitida");
        }
    }
}