package com.example.demo.controller;

import com.example.demo.Models.AnimalModel;
import com.example.demo.Models.SituacaoAnimalModel;
import com.example.demo.repo.AnimalRepo;
import com.example.demo.repo.SituacaoAnimalRepo;
import com.example.demo.service.EmailService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIsituacaoAnimal {

    @Autowired
    AnimalRepo animalRepo;

    @Autowired
    SituacaoAnimalRepo situacaoAnimalRepo;

    @GetMapping("/teste")
    public void teste(){
        EmailService emailService = new EmailService();

        emailService.enviar("teste", "teste", "luanponick07@gmail.com");
    }

    @PostMapping("situacaoAnimal/{idColeira}")
    public ResponseEntity<Resource> createData(@PathVariable long idColeira, @RequestBody SituacaoAnimalModel situacaoAnimalModel){
        AnimalModel animalModel = animalRepo.findAnimalModelBycoleiraid(idColeira);


        System.out.println(situacaoAnimalModel);
        if (animalModel == null){
            return ResponseEntity.notFound().build();
        }
        situacaoAnimalModel.setAnimalid(animalModel.getId_animal());
        situacaoAnimalRepo.save(situacaoAnimalModel);

        return ResponseEntity.ok().build();
    }
}
