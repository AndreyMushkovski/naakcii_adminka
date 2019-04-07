package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.AbstractDTOEntity;

import java.util.List;

public interface CrudService<DTO extends AbstractDTOEntity> {

    List<DTO> findAllDTOs();

    List<DTO> searchName(String name);

    DTO createNewDTO();

    DTO saveDTO(DTO entityDTO);

    void deleteDTO(DTO entityDTO);
}