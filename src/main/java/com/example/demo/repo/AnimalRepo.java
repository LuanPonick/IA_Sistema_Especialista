package com.example.demo.repo;

import com.example.demo.Models.AnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AnimalRepo extends JpaRepository<AnimalModel, Long> {

    AnimalModel findAnimalModelBycoleiraid(long coleiraid);
}
