package com.formacion.examen_JPA_cascada;

import com.formacion.examen_JPA_cascada.application.ClienteService;
import com.formacion.examen_JPA_cascada.domain.CabeceraFra;
import com.formacion.examen_JPA_cascada.domain.Cliente;
import com.formacion.examen_JPA_cascada.domain.LineasFra;
import com.formacion.examen_JPA_cascada.repository.CabeceraFraRepository;
import com.formacion.examen_JPA_cascada.repository.ClienteRepository;
import com.formacion.examen_JPA_cascada.repository.LineasFraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CabeceraFraRepository cabeceraFraRepository;
    @Autowired
    LineasFraRepository lineasFraRepository;
    @Override
    public void run(String... args) throws Exception {

        // Se crea el cliente
        Cliente clienteDB = new Cliente();
        clienteDB.setTiempo_en_empresa(1);
        clienteDB.setNombre("Cliente Prueba");
        clienteDB.setPoblacion("Población");
        clienteDB.setDireccion("Dirección");
        clienteDB = clienteRepository.save(clienteDB);

        // Se crea la factura para el cliente creado anteriormente
        CabeceraFra cabeceraFraDB = new CabeceraFra();
        cabeceraFraDB.setImporteFra(20.50);
        cabeceraFraDB.setCliente(clienteDB);

        // Se crea una línea para la factura
        LineasFra lineasFraDB1 = new LineasFra();
        lineasFraDB1.setProNomb("Nombre del producto 1");
        lineasFraDB1.setCantidad(1);
        lineasFraDB1.setPrecio(10.25);
        lineasFraDB1.setCabeceraFra(cabeceraFraDB);

        // Se crea una segunda línea para la factura
        LineasFra lineasFraDB2 = new LineasFra();
        lineasFraDB2.setProNomb("Nombre del producto 2");
        lineasFraDB2.setCantidad(1);
        lineasFraDB2.setPrecio(10.25);
        lineasFraDB2.setCabeceraFra(cabeceraFraDB);

        cabeceraFraDB.getLineasFraList().add(lineasFraDB1); // asigno las líneas a la fra porque el save se hace en fra, pero guarda también las líneas
        cabeceraFraDB.getLineasFraList().add(lineasFraDB2);

        cabeceraFraRepository.save(cabeceraFraDB);
    }
}
