package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    private List<City> cities = new ArrayList<>();
    public City addCity(City city) {
        cities.add(city);
        return city;
    }

    public List<City> getCities() {

        return cities;
    }
}