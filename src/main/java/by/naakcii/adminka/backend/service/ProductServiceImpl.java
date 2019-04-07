package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.ProductDTO;
import by.naakcii.adminka.backend.entity.Product;
import by.naakcii.adminka.backend.repositories.ProductRepository;
import by.naakcii.adminka.backend.utils.ObjectFactory;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, CrudService<ProductDTO> {

    private final ProductRepository productRepository;
    private final SubcategoryService subcategoryService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final CountryService countryService;
    private final ObjectFactory objectFactory;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SubcategoryService subcategoryService,
                              UnitOfMeasureService unitOfMeasureService, CountryService countryService,
                              ObjectFactory objectFactory) {
        this.productRepository = productRepository;
        this.objectFactory = objectFactory;
        this.subcategoryService = subcategoryService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.countryService = countryService;
    }

    @Override
    public List<ProductDTO> searchName(String search) {
        return productRepository.findAllByNameContainingIgnoreCase(search)
                .stream()
                .filter(Objects::nonNull)
                .map((Product product) -> objectFactory.getInstance(ProductDTO.class, product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createNewDTO() {
        return new ProductDTO();
    }

    @Override
    public ProductDTO saveDTO(ProductDTO entityDTO) {
        Product product = new Product(entityDTO);
        product.setSubcategory(subcategoryService.findByNameAndCategoryName(entityDTO.getSubcategoryName(), entityDTO.getCategoryName()));
        product.setUnitOfMeasure(unitOfMeasureService.findUnitOfMeasureByName(entityDTO.getUnitOfMeasureName()));
        product.setCountryOfOrigin(countryService.findByName(entityDTO.getCountryOfOriginName()));
        Optional<Product> productDB = productRepository.findByNameAndBarcodeAndUnitOfMeasure(product.getName(), product.getBarcode(), product.getUnitOfMeasure());
        if(entityDTO.getId()==null) {
            if (productDB.isPresent() || productRepository.findByNameIgnoreCase(entityDTO.getName()) != null) {
                Notification.show("Данный товар уже внесен в базу");
                return null;
            }
            return new ProductDTO(productRepository.save(product));
        } else {
                return new ProductDTO(productRepository.save(product));
            }
    }

    @Override
    public void deleteDTO(ProductDTO entityDTO) {
        Product product = productRepository.findById(entityDTO.getId()).orElse(null);
        if (product == null) {
            throw new EntityNotFoundException();
        } else {
            productRepository.delete(product);
        }
    }

    @Override
    public List<ProductDTO> findAllDTOs() {
        return productRepository.findAllByOrderByName()
                .stream()
                .filter(Objects::nonNull)
                .map((Product product) -> objectFactory.getInstance(ProductDTO.class, product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> checkIsActive(String filter) {
        if (filter.equals("Активные")) {
            return productRepository.findAllByIsActiveTrueOrderByName()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Product product) -> objectFactory.getInstance(ProductDTO.class, product))
                    .collect(Collectors.toList());
        } else {
            return productRepository.findAllByIsActiveFalseOrderByName()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Product product) -> objectFactory.getInstance(ProductDTO.class, product))
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public List<String> getAllProductNames() {
        return productRepository.findAllByIsActiveTrueOrderByName()
                .stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}
