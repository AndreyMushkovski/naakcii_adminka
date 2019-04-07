package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.SubcategoryDTO;
import by.naakcii.adminka.backend.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {

    List<String> getAllSubcategoriesNames(String categoryName);
    List<SubcategoryDTO> checkIsActive(String filter);
    Subcategory findByNameAndCategoryName(String subcategoryName, String categoryName);
    List<SubcategoryDTO> findByCategoryName(String categoryName);
    List<SubcategoryDTO> findAllByFilter(String subcategoryName, boolean isActive, String categoryName);
}
