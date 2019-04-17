package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.CategoryDTO;
import by.naakcii.adminka.backend.entity.Category;
import by.naakcii.adminka.backend.entity.Subcategory;
import by.naakcii.adminka.backend.repositories.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService, CrudService<CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final ObjectFactory objectFactory;
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ObjectFactory objectFactory) {
    	this.categoryRepository = categoryRepository;
    	this.objectFactory = objectFactory;
    }

    @Override
    public List<String> getAllActiveCategoriesNames() {
        return categoryRepository.findAllByIsActiveTrueOrderByPriorityAsc()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAllByOrderByName()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> checkIsActive(String filter) {
        if (filter.equals("Активные")) {
            return categoryRepository.findAllByIsActiveTrueOrderByPriorityAsc()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Category category) -> objectFactory.getInstance(CategoryDTO.class, category))
                    .collect(Collectors.toList());
        } else {
            return categoryRepository.findAllByIsActiveFalseOrderByPriorityAsc()
                    .stream()
                    .filter(Objects::nonNull)
                    .map((Category category) -> objectFactory.getInstance(CategoryDTO.class, category))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<CategoryDTO> findAllDTOs() {
        return categoryRepository.findAllByOrderByPriority()
                .stream()
                .filter(Objects::nonNull)
                .map((Category category) -> objectFactory.getInstance(CategoryDTO.class, category))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> searchName(String name) {
        return categoryRepository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .filter(Objects::nonNull)
                .map((Category category) -> objectFactory.getInstance(CategoryDTO.class, category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createNewDTO() {
        return new CategoryDTO();
    }

    @Override
    public CategoryDTO saveDTO(CategoryDTO entityDTO) {
        Optional<Category> category = categoryRepository.findByNameIgnoreCase(entityDTO.getName());
        if (!category.isPresent()) {
            return new CategoryDTO(categoryRepository.save(new Category(entityDTO)));
        } else {
            Notification.show("Данная категория уже внесена в базу");
            return null;
        }
   }

    @Override
    @Transactional
    public void deleteDTO(CategoryDTO entityDTO) {
        Category category = categoryRepository.findById(entityDTO.getId()).orElse(null);
        if(category == null) {
            throw new EntityNotFoundException();
        } else {
            Category indefiniteCategory = getIndefiniteCategory();
            Set<Subcategory> subcategories = category.getSubcategories();
            for (Subcategory tempSubcategory : subcategories) {
                tempSubcategory.setCategory(indefiniteCategory);
            }
            subcategories.clear();
            categoryRepository.delete(category);
        }
    }

    private Category getIndefiniteCategory() {
        Category categoryIndefinite = categoryRepository.findByNameIgnoreCase("Indefinite category").orElse(null);
        if(categoryIndefinite == null) {
            return categoryRepository.save(new Category("Indefinite category", false));
        } else {
            return categoryIndefinite;
        }
    }
}
