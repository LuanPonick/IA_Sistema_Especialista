package com.example.demo.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AnomaliaModelAgrupador {
    public Set<AnomaliaModel> anomaliaModels = new HashSet<>();
}
