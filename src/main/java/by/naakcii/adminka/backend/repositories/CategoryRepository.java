package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	List<Category> findAllByIsActiveTrueOrderByPriorityAsc();
	List<Category> findAllByIsActiveFalseOrderByPriorityAsc();
	List<Category> findAllByOrderByPriority();
	List<Category> findAllByNameContainingIgnoreCase(String name);
	Optional<Category> findByNameIgnoreCase(String name);
	List<Category> findAllByOrderByName();
}
