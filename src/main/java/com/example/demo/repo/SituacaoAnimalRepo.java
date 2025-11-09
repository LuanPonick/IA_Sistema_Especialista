package com.example.demo.repo;

import com.example.demo.Models.SituacaoAnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface SituacaoAnimalRepo extends JpaRepository<SituacaoAnimalModel, Long> {

    // find AnimalModel By coleiraid
    SituacaoAnimalModel findFirstSituacaoanimalModelByanimalid (Long animalid);

    @Query("FROM SituacaoAnimalModel s WHERE s.animalid = :aimalId")
    List<SituacaoAnimalModel> GetFirstSituacaoAnimalModelByanimalID(@Param("aimalId") long animalId);
}
