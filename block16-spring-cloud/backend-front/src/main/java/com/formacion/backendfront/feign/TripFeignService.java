package com.formacion.backendfront.feign;

import com.formacion.backendfront.controller.dto.TripOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://backend:8080", name = "trip-service")
public interface TripFeignService {
    @GetMapping("/trip/{idTrip}")
    public TripOutputDto getTripById(@PathVariable Integer idTrip);
}
