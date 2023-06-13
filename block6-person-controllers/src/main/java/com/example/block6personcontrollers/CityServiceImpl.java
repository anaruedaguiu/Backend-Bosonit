package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    public List<City> cities = new ArrayList<>();

    @Override
    public City addCity(City city) {
        cities.add(city);
        return city;
    }

    @Override
    public List<City> getCities() {
        return cities;
    }
}