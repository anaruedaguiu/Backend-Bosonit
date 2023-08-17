package com.formacion.block17springbatch.mapper;

import com.formacion.block17springbatch.domain.TiempoRiesgo;
import com.formacion.block17springbatch.repository.TiempoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import com.formacion.block17springbatch.domain.Tiempo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TiempoRiesgoRowMapper implements RowMapper<TiempoRiesgo> {
    @Autowired
    TiempoRepository tiempoRepository;

    @Override
    public TiempoRiesgo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TiempoRiesgo tiempoRiesgo = new TiempoRiesgo();
        tiempoRiesgo.setIdTiempoRiesgo(rs.getInt("id_Tiempo_Riesgo"));
        tiempoRiesgo.setFechaPrediccion(rs.getDate("fecha_prediccion"));
        tiempoRiesgo.setRiesgo(rs.getString("riesgo"));

        Tiempo tiempo = tiempoRepository.findById(rs.getInt("id_Tiempo")).orElseThrow();

        tiempoRiesgo.setTiempo(tiempo);

        return tiempoRiesgo;
    }
}
