package com.formacion.block17springbatch.mapper;

import com.formacion.block17springbatch.domain.Resultado;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultadoRowMapper implements RowMapper<Resultado> {
    @Override
    public Resultado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resultado resultado = new Resultado();
        resultado.setIdResultado(rowNum);
        resultado.setLocalidad(rs.getString("localidad"));
        resultado.setMes(rs.getInt("mes"));
        resultado.setAnio(rs.getInt("anio"));
        resultado.setNumeroMediciones(rs.getInt("numero_mediciones"));
        resultado.setTemperaturaMedia(rs.getFloat("temperatura_media"));

        return resultado;
    }
}
