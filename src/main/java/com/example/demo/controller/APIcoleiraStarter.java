package com.example.demo.controller;

import com.example.demo.Models.*;
import com.example.demo.repo.AnimalRepo;
import com.example.demo.repo.AnomaliaRepo;
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

    @Autowired
    AnomaliaRepo anomaliaRepo;

    @PatchMapping("startdetection/{idColeira}")
        public ResponseEntity<Resource> updateResourcePartial(@PathVariable int idColeira) throws InterruptedException {



        AnimalModel animalModel = animalRepo.findAnimalModelBycoleiraid(idColeira);
        SituacaoAnimalModel situacaoAnimalModel = situacaoAnimalRepo.findById(animalModel.getId_animal()).orElse(null);
        AnomaliaModelAgrupador anomaliaModelAgrupador = new AnomaliaModelAgrupador();

        RuleModel ruleModel = new RuleModel();

        if (situacaoAnimalModel == null) {
            return ResponseEntity.notFound().build();
        }
        new Thread(() -> {
            try{
                System.out.println("\n" + Thread.currentThread().getName() + "\n");
                situacaoAnimalModel.setSituacao_ja_analisada(false);
                do {
                    KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
                    KieSession kieSession = kieContainer.newKieSession("session");

                    kieSession.insert(situacaoAnimalModel);
                    kieSession.insert(animalModel);
                    kieSession.insert(anomaliaModelAgrupador);
                    kieSession.insert(ruleModel);

                    kieSession.fireAllRules();
                    System.out.println(anomaliaModelAgrupador.anomaliaModels.toString());
                    Thread.sleep(5000);
                    System.out.println("--------------");

                    anomaliaRepo.saveAll(anomaliaModelAgrupador.anomaliaModels);
                    anomaliaModelAgrupador.anomaliaModels.clear();
                    situacaoAnimalRepo.save(situacaoAnimalModel);

                    situacaoAnimalModel.getId_situacao();
                }while (animalModel.getColeira_on());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        return ResponseEntity.ok().build();
    }
}