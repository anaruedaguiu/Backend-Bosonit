package com.formacion.backend.domain;

import com.formacion.backend.controller.dto.input.ClientInputDto;
import com.formacion.backend.controller.dto.output.ClientOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idClient;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Integer phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTrip")
    private Trip trip;

    public Client(ClientInputDto clientInputDto) {
        this.idClient = clientInputDto.getIdClient();
        this.name = clientInputDto.getName();
        this.surname = clientInputDto.getSurname();
        this.age = clientInputDto.getAge();
        this.email = clientInputDto.getEmail();
        this.phone = clientInputDto.getPhone();
    }

    public ClientOutputDto clientToClientOutputDto() {
        return new ClientOutputDto(
                this.idClient,
                this.name,
                this.surname,
                this.age,
                this.email,
                this.phone
        );
    }
}
