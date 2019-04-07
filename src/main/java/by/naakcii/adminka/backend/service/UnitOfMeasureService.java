package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.entity.UnitOfMeasure;

import java.util.List;

public interface UnitOfMeasureService {

    UnitOfMeasure findUnitOfMeasureByName(String name);

    List<String> getAllUnitOfMeasureNames();

}
