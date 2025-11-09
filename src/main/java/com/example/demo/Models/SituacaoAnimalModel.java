package com.example.demo.Models;

import com.example.demo.repo.SituacaoAnimalRepo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Data
@Entity
@Table(name = "situacao")
@Setter
@Getter
public class SituacaoAnimalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situacao")
    private long idSituacao;

//    @ManyToOne
    @Column(name = "id_animal")
    private long animalid;

    @JsonAlias("bpm")
    private int batimentos;
    @JsonAlias("pressao_sistolica")
    private int presao_arterial_sistolica;
    @JsonAlias("pressao_diastolica")
    private int presao_arterial_diastolica;
    @JsonAlias("temperatura")
    private int temperatura;
    private boolean situacao_ja_analisada;

    public void setColeira_on(boolean b) {
        this.situacao_ja_analisada = b;
    }
}
