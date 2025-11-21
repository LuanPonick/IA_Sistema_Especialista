package com.example.demo.controller;

import com.example.demo.Models.*;
import com.example.demo.repo.AnimalRepo;
import com.example.demo.repo.AnomaliaRepo;
import com.example.demo.repo.SituacaoAnimalRepo;
import com.example.demo.service.EmailService;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.Resource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class APIcoleiraStarter {

    @Autowired
    SituacaoAnimalRepo situacaoAnimalRepo;

    @Autowired
    AnimalRepo animalRepo;

    @Autowired
    AnomaliaRepo anomaliaRepo;
    @CrossOrigin(origins = "http://localhost:5173")
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
                String emailTutor = animalModel.getEmail_tutor();

                do {
                    situacaoAnimalModel = situacaoAnimalRepo.GetFirstSituacaoAnimalModelByanimalID(animalModel.getId_animal()).getLast();

                    teste.add(String.valueOf(situacaoAnimalModel.getIdSituacao()));

                    KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
                    KieSession kieSession = kieContainer.newKieSession("session");

                    kieSession.insert(situacaoAnimalModel);
                    kieSession.insert(animalModel);
                    kieSession.insert(anomaliaModelAgrupador);
                    kieSession.insert(ruleModel);

                    kieSession.fireAllRules();
                    // System.out.println(anomaliaModelAgrupador.anomaliaModels.toString());
                    // System.out.println("--------------");
                    if (!anomaliaModelAgrupador.getAnomaliaModels().isEmpty()) {
                        this.EnvioDeEmail(anomaliaModelAgrupador, emailTutor);
                    }

                    anomaliaRepo.saveAll(anomaliaModelAgrupador.anomaliaModels);
                    anomaliaModelAgrupador.anomaliaModels.clear();
                    situacaoAnimalRepo.save(situacaoAnimalModel);
                    if(!animalModel.getColeira_on()){
                        break;
                    }
                    Thread.sleep(5000);
                }while (true);
                // System.out.println(teste.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                animalModel.setColeira_on(false);
                animalRepo.save(animalModel);
            }

        }).start();

        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:5173")
    private void EnvioDeEmail(AnomaliaModelAgrupador anomaliaModelAgrupador, String emailTutor) {
        EmailService emailService = new EmailService();
        String corpoMensagem = anomaliaModelAgrupador.getAnomaliaModels().stream().map(AnomaliaModel -> AnomaliaModel.getDescricao_anomalia() + "<BR>").collect(Collectors.joining());
        corpoMensagem += "HDA Ajuda em monitoramento animal";

        System.out.println(" ---EnvioDeEmail--- ");

        //  anomaliaModelAgrupador.getAnomaliaModels().forEach(System.out::println);
        System.out.println(corpoMensagem);

        emailService.enviar("Informe de anomalia", corpoMensagem, emailTutor, true);

        System.out.println(" ------------------ ");
    }
}
