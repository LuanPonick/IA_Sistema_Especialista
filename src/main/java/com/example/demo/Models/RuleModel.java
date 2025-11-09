package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleModel {
    private int temperaturaAlta;
    private int temperaturaBaixa;

    private int batimentosCaridiacosAlta;
    private int batimentosCaridiacosBaixo;

    private int presaoArterialSistolicaAlta;
    private int presaoArterialSistolicaBaixa;

    private int presaoArterialDiastolicaAlta;
    private int presaoArterialDiastolicaBaixa;

    private int numeroTentativas;
    private boolean coleiraOn;

    private int limite_tentativas;
    private int contador_tentativa;

    public RuleModel(){

    }
}
