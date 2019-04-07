package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> searchName(String search);

    List<ProductDTO> checkIsActive(String filter);

    List<String> getAllProductNames();
}
