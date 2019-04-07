package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.SubcategoryDTO;
import by.naakcii.adminka.backend.entity.Category;
import by.naakcii.adminka.backend.entity.Product;
import by.naakcii.adminka.backend.entity.Subcategory;
import by.naakcii.adminka.backend.repositories.CategoryRepository;
import by.naakcii.adminka.backend.repositories.SubcategoryRepository;
import by.naakcii.adminka.backend.utils.ObjectFactory;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService, CrudService<SubcategoryDTO> {
	   
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectFactory objectFactory;
    
    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository,
                                  ObjectFactory objectFactory) {
    	this.subcategoryRepository = subcategoryRepository;
    	this.categoryRepository = categoryRepository;
    	this.objectFactory = objectFactory;
    }

    @Override
    public List<String> getAllSubcategoriesNames(String categoryName) {
        return subcategoryRepository.findByCategory(categoryRepository.findByNameIgnoreCase(categoryName))
                .stream()
                .map(Subcategory::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDTO> checkIsActive(String filter) {
        if (filter.equals("Активные")) {
            return subcategoryRepository.findAllByIsActiveTrueOrderByName()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                    .collect(Collectors.toList());
        } else {
            return subcategoryRepository.findAllByIsActiveFalseOrderByName()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Subcategory findByNameAndCategoryName(String subcategoryName, String categoryName) {
        return subcategoryRepository.findByNameAndCategoryName(subcategoryName, categoryName).orElse(null);
    }

    @Override
    public List<SubcategoryDTO> findByCategoryName(String categoryName) {
        return subcategoryRepository.findAllByCategoryName(categoryName)
                .stream()
                .filter(Objects::nonNull)
                .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDTO> findAllByFilter(String subcategoryName, boolean isActive, String categoryName) {
        if (subcategoryName!=null) {
            subcategoryName = subcategoryName.toLowerCase();
        }
        return subcategoryRepository.findAllByFilter(subcategoryName, isActive, categoryName)
                .stream()
                .filter(Objects::nonNull)
                .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDTO> findAllDTOs() {
        return subcategoryRepository.findAllByOrderByName()
                .stream()
                .filter(Objects::nonNull)
                .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDTO> searchName(String name) {
        return subcategoryRepository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .filter(Objects::nonNull)
                .map((Subcategory subcategory) -> objectFactory.getInstance(SubcategoryDTO.class, subcategory))
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDTO createNewDTO() {
        return new SubcategoryDTO();
    }

    @Override
    public SubcategoryDTO saveDTO(SubcategoryDTO entityDTO) {
        Subcategory subcategory = new Subcategory(entityDTO);
        subcategory.setCategory(categoryRepository.findByNameIgnoreCase(entityDTO.getCategoryName()).orElse(null));
        Optional<Subcategory> subcategoryDB = subcategoryRepository.findByNameAndCategoryName(subcategory.getName(), entityDTO.getCategoryName());
        if (subcategoryDB.isPresent() && entityDTO.getId()==null) {
            Notification.show("Данная подкатегория уже внесена в базу");
            return null;
        } else {
            return new SubcategoryDTO(subcategoryRepository.save(subcategory));
        }
    }

    @Override
    @Transactional
    public void deleteDTO(SubcategoryDTO entityDTO) {
        Subcategory subcategory = subcategoryRepository.findById(entityDTO.getId()).orElse(null);
        if(subcategory == null) {
            throw new EntityNotFoundException();
        } else {
            Subcategory indefiniteSubcategory = getIndefiniteSubcategory();
            Set<Product> products = subcategory.getProducts();
            for (Product tempProduct : products) {
                tempProduct.setSubcategory(indefiniteSubcategory);
            }
            products.clear();
            subcategoryRepository.delete(subcategory);
        }
    }

    private Subcategory getIndefiniteSubcategory() {
        Subcategory subcategoryIndefinite =
                subcategoryRepository.findByNameIgnoreCaseAndCategoryNameIgnoreCase("Indefinite subcategory",
                        "Indefinite category").orElse(null);
        Category categoryIndefinite = categoryRepository.findByNameIgnoreCase("Indefinite category").orElse(null);
        if(subcategoryIndefinite==null && categoryIndefinite==null) {
            Category category = categoryRepository.save(new Category("Indefinite category", false));
            return subcategoryRepository.save(new Subcategory("Indefinite category", false, category));
        } else if (subcategoryIndefinite==null) {
            return subcategoryRepository.save(new Subcategory("Indefinite category", false, categoryIndefinite));
        } else {
            return subcategoryIndefinite;
        }
    }
}
