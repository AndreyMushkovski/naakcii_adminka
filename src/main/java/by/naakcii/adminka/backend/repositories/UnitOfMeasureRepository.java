package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long>{

	UnitOfMeasure findByNameIgnoreCase(String unitOfMeasureName);

	List<UnitOfMeasure> findAllByOrderByName();

	List<UnitOfMeasure> findByNameContainingIgnoreCase(String name);
}
