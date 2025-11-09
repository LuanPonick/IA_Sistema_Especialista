package com.example.demo.repo;

import com.example.demo.Models.AnimalModel;
import com.example.demo.Models.AnomaliaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AnomaliaRepo extends JpaRepository<AnomaliaModel, Long> {

}
