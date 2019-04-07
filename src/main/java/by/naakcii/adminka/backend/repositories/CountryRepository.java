package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long> {

	Optional<Country> findByAlphaCode2IgnoreCase(String alphaCode2);
	Optional<Country> findByAlphaCode3IgnoreCase(String alphaCode3);
	Optional<Country> findByNameIgnoreCase(String name);
	List<Country> findAllByOrderByName();
	List<Country> findAllByNameContainingIgnoreCase(String name);
}
