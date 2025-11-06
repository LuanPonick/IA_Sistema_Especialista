package com.example.demo.controller;

import com.example.demo.Models.AnimalModel;
import com.example.demo.Models.RuleModel;
import com.example.demo.Models.SituacaoAnimalModel;
import com.example.demo.repo.AnimalRepo;
import com.example.demo.repo.SituacaoAnimalRepo;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.Resource;

@RestController
public class APIcoleiraStarter {

    @Autowired
    SituacaoAnimalRepo situacaoAnimalRepo;

    @Autowired
    AnimalRepo animalRepo;

    @PatchMapping("startdetection/{idColeira}")
        public ResponseEntity<Resource> updateResourcePartial(@PathVariable int idColeira) throws InterruptedException {

        int contador = 0;

        AnimalModel animalModel = animalRepo.findAnimalModelBycoleiraid(idColeira);
        SituacaoAnimalModel situacaoAnimalModel = situacaoAnimalRepo.findById(animalModel.getId_animal()).orElse(null);
        RuleModel ruleModel = new RuleModel();

        if (situacaoAnimalModel == null) {
            return ResponseEntity.notFound().build();
        }

        do {
            KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
            KieSession kieSession = kieContainer.newKieSession("session");

            kieSession.insert(situacaoAnimalModel);
            kieSession.insert(animalModel);
            kieSession.insert(ruleModel);

            kieSession.fireAllRules();

            contador++;
            Thread.sleep(1000);
        }while (animalModel.getColeira_on() && contador < 6);

        if (contador == 6){
            System.out.println("*****************************");
            System.out.println("logica falhou");
            System.out.println("*****************************");
        }

        return ResponseEntity.ok().build();
    }
}