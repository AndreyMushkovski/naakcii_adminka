package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.CountryDTO;
import by.naakcii.adminka.backend.entity.Country;
import by.naakcii.adminka.backend.entity.Product;
import by.naakcii.adminka.backend.repositories.CountryRepository;
import by.naakcii.adminka.backend.repositories.ProductRepository;
import by.naakcii.adminka.backend.utils.ObjectFactory;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService, CrudService<CountryDTO> {

    private final CountryRepository countryRepository;
    private final ProductRepository productRepository;
    private final ObjectFactory objectFactory;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ObjectFactory objectFactory,
                              ProductRepository productRepository) {
        this.countryRepository = countryRepository;
        this.productRepository = productRepository;
        this.objectFactory = objectFactory;
    }

    @Override
    public Country findByName(String name) {
        return countryRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<String> getAllCountryNames() {
        return countryRepository.findAllByOrderByName()
                .stream()
                .map(Country::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountryDTO> findAllDTOs() {
        return countryRepository.findAllByOrderByName()
                .stream()
                .filter(Objects::nonNull)
                .map((Country country) -> objectFactory.getInstance(CountryDTO.class, country))
                .collect(Collectors.toList());
    }

    @Override
    public List<CountryDTO> searchName(String name) {
        return countryRepository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .filter(Objects::nonNull)
                .map((Country country) -> objectFactory.getInstance(CountryDTO.class, country))
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO createNewDTO() {
        return new CountryDTO();
    }

    @Override
    public CountryDTO saveDTO(CountryDTO entityDTO) {
        if (entityDTO.getId() == null) {
            if (countryRepository.findByNameIgnoreCase(entityDTO.getName()).isPresent()) {
                Notification.show("Данная страна уже внесена в базу");
                return null;
            } else if (countryRepository.findByAlphaCode2IgnoreCase(entityDTO.getAlphaCode2()).isPresent()) {
                Notification.show("Данный код2 уже внесен в базу");
                return null;
            } else if (countryRepository.findByAlphaCode3IgnoreCase(entityDTO.getAlphaCode3()).isPresent()) {
                Notification.show("Данный код3 уже внесен в базу");
                return null;
            }
            return new CountryDTO(countryRepository.save(new Country(entityDTO)));
        } else {
            return new CountryDTO(countryRepository.save(new Country(entityDTO)));
        }
    }

    @Override
    public void deleteDTO(CountryDTO entityDTO) {
        Country country = countryRepository.findById(entityDTO.getId()).orElse(null);
        List<Product> products = productRepository
                .findAllByCountryOfOrigin(countryRepository.findByNameIgnoreCase(entityDTO.getName()).orElse(null));
        if (products.size() > 0) {
            Notification.show("Данная страна присвоена " + products.size() + " товарам");
            throw new RuntimeException("Удаление невозможно");
        } else {
            if (country == null) {
                throw new EntityNotFoundException();
            } else {
                countryRepository.delete(country);
            }
        }
    }
}
