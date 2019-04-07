package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.Country;
import by.naakcii.adminka.backend.entity.Product;
import by.naakcii.adminka.backend.entity.UnitOfMeasure;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	Optional<Product> findByNameAndBarcodeAndUnitOfMeasure(String productName, String productBarcode, UnitOfMeasure unitOfMeasure);

    List<Product> findAllByOrderByName();
    List<Product> findAllByNameContainingIgnoreCase(String search);

    List<Product> findAllByIsActiveTrueOrderByName();
    List<Product> findAllByIsActiveFalseOrderByName();
    List<Product> findAllByUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    List<Product> findAllByCountryOfOrigin(Country country);
    Product findByNameIgnoreCase(String name);
}
