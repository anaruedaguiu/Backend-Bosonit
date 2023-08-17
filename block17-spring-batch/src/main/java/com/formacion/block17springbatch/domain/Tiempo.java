package com.formacion.block17springbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiempo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTiempo;
    private String localidad;
    private Date fecha;
    private Integer temperatura;
    @OneToOne(mappedBy = "tiempo")
    private TiempoRiesgo tiempoRiesgo;
}
