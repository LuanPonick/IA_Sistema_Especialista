package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "anomalia")
@Setter
@Getter
public class AnomaliaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id_anomalia;
    long id_animal;
    long id_situacao;
    int porcetagem_acuracia;
    String descricao_anomalia;

    public AnomaliaModel(long id_animal, long id_situacao, String descricao_anomalia){
        this.descricao_anomalia = descricao_anomalia;
        this.id_animal = id_animal;
        this.id_situacao = id_situacao;
    }
}
