package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<String> getAllActiveCategoriesNames();
    List<String> getAllCategoriesNames();
    List<CategoryDTO> checkIsActive(String filter);
}
