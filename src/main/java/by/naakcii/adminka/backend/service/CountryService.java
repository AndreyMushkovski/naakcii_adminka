package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.entity.Country;

import java.util.List;


public interface CountryService {

    Country findByName(String name);

    List<String> getAllCountryNames();
}
