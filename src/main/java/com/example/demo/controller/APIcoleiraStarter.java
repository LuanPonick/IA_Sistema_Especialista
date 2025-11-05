package com.example.demo.controller;

import com.example.demo.Models.AnimalModel;
import com.example.demo.Models.SituacaoAnimalModel;
import com.example.demo.repo.AnimalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.repo.SituacaoAnimalRepo;

import jakarta.annotation.Resource;

import java.util.HashSet;
import java.util.Set;

@RestController
public class APIcoleiraStarter {

    @Autowired
    SituacaoAnimalRepo situacaoAnimalRepo;

    @Autowired
    AnimalRepo animalRepo;

    @PatchMapping("startdetection/{idColeira}")
        public ResponseEntity<Resource> updateResourcePartial(@PathVariable int idColeira) {
        long a = 1;

        SituacaoAnimalModel situacaoAnimalModel = situacaoAnimalRepo.findById(a).orElse(null);
        AnimalModel animalModel = animalRepo.findAnimalModelBycoleiraid(idColeira);

        System.out.println("\n\n");
        System.out.println(animalModel.getSituacaoAnimal().toString());
        System.out.println("\n\n");

        System.out.println("#############################");
        animalModel.getSituacaoAnimal().stream().forEach(l-> System.out.println(l.getBatimentos()));
        System.out.println("#############################");
//        if (situacaoAnimalModel == null)
//        {
//            return ResponseEntity.notFound().build();
//        }
//
//        System.out.println("\n\n\n teste");
//        System.out.println(situacaoAnimalModel.getAnimalModel().getNome());
//        System.out.println("teste \n\n\n");
//
//    	KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
//        KieSession kieSession = kieContainer.newKieSession("session");
//        kieSession.insert(situacaoAnimalModel);
//		kieSession.fireAllRules();

        return ResponseEntity.ok().build();


    }
}