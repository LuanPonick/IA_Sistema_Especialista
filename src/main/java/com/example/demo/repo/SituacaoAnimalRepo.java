package com.example.demo.repo;

import com.example.demo.Models.SituacaoAnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface SituacaoAnimalRepo extends JpaRepository<SituacaoAnimalModel, Long> {
}
