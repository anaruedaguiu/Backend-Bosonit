package com.example.block6personcontrollers.services;

import com.example.block6personcontrollers.models.City;

import java.util.List;

public interface CityService {
    public City addCity(City city);

    public List<City> getCities();
}