package com.formacion.block17springbatch.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiempoRiesgo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTiempoRiesgo;
    private Date fechaPrediccion;
    private String riesgo; // Alto (+36) - Medio (+32) - Bajo (-24)
    @OneToOne
    @JoinColumn(name = "tiempo_id")
    private Tiempo tiempo;
}
