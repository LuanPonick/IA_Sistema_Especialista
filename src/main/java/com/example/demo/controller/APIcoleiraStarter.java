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

import java.util.HashSet;
import java.util.Set;

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
        if (animalModel == null){
            return ResponseEntity.notFound().build();
        }
        animalModel.setColeira_on(true);
        animalRepo.save(animalModel);

        AnomaliaModelAgrupador anomaliaModelAgrupador = new AnomaliaModelAgrupador();
        RuleModel ruleModel = new RuleModel();

        new Thread(() -> {
            try{
                SituacaoAnimalModel situacaoAnimalModel;
                Set<String> teste = new HashSet<>();
                System.out.println("\n" + Thread.currentThread().getName() + "\n");

                do {
                    situacaoAnimalModel = situacaoAnimalRepo.GetFirstSituacaoAnimalModelByanimalID(animalModel.getId_animal()).getLast();

                    if(situacaoAnimalModel == null) {
                        throw new InterruptedException("Nenhuma situacao cadastrada!!!");
                    }
                    teste.add(String.valueOf(situacaoAnimalModel.getIdSituacao()));

                    KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
                    KieSession kieSession = kieContainer.newKieSession("session");

                    kieSession.insert(situacaoAnimalModel);
                    kieSession.insert(animalModel);
                    kieSession.insert(anomaliaModelAgrupador);
                    kieSession.insert(ruleModel);

                    kieSession.fireAllRules();
                    System.out.println(anomaliaModelAgrupador.anomaliaModels.toString());
                    System.out.println("--------------");

                    anomaliaRepo.saveAll(anomaliaModelAgrupador.anomaliaModels);
                    anomaliaModelAgrupador.anomaliaModels.clear();
                    situacaoAnimalRepo.save(situacaoAnimalModel);
                    if(!animalModel.getColeira_on()){
                        break;
                    }
                    Thread.sleep(2000);
                }while (true);
                System.out.println(teste.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                animalModel.setColeira_on(false);
                animalRepo.save(animalModel);
            }

        }).start();

        return ResponseEntity.ok().build();
    }
}