package com.formacion.block17springbatch.repository;

import com.formacion.block17springbatch.domain.Tiempo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiempoRepository extends JpaRepository<Tiempo, Integer> {
}
