package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "animal")
public class AnimalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_animal;
    private String nome;
    private Date idade;
    @Column(name = "coleira_id")
    private long coleiraid;
    private boolean coleira_on;
    private int limite_tentativas;
    private int contador_tentativa;
    private int porte;
    private boolean idade_avancada;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "animal")
    private Set<SituacaoAnimalModel> situacaoAnimal = new HashSet<>();

    public boolean getColeira_on (){
        return coleira_on;
    }
}
