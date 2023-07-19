package com.formacion.examen_JPA_cascada.controllers.output;

import com.formacion.examen_JPA_cascada.domain.Cliente;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteOutputDto {

    public int idCliente;
    public int tiempo_en_empresa;
    public String nombre; // nombre del cliente
    public String poblacion;
    public String direccion;

    public ClienteOutputDto(Cliente cliente) { // Método para pasar entidad a outputDto
        setIdCliente(cliente.getIdCliente());
        setTiempo_en_empresa(cliente.getTiempo_en_empresa());
        setNombre(cliente.getNombre());
        setPoblacion(cliente.getPoblacion());
        setDireccion(cliente.getDireccion());
    }
}
