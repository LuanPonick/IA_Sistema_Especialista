package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleModel {
    private int temperaturaAlta;
    private int temperaturaBaixa;

    private int batimentosCaridiacosAlta;
    private int batimentosCaridiacosBaixa;

    private int presaoArterialSistolicaAlta;
    private int presaoArterialSistolicaBaixa;

    private int presaoArterialdastolicaAlta;
    private int presaoArterialdastolicaBaixa;

    private int numeroTentativas;
    private boolean coleiraOn;

    public RuleModel(){

    }
}
