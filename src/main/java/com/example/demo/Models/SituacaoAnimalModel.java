package com.example.demo.Models;

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
    private int id_situacao;

    @ManyToOne
    @JoinColumn(name = "id_animal")
    private AnimalModel animal;

    private int batimentos;
    private int presao_arterial_sistolica;
    private int presao_arterial_diastolica;
    private int temperatura;
    private boolean situacao_ja_analisada;

    public void setColeira_on(boolean b) {
        this.situacao_ja_analisada = b;
    }
}
