package com.formacion.backendfront.feign;

import com.formacion.backendfront.controller.dto.ClientOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://backend:8080", name = "client-service")
public interface ClientFeignService {
    @GetMapping("/client/{idClient}")
    public ClientOutputDto getClientById(@PathVariable Integer idClient);
}
