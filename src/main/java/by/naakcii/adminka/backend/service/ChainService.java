package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.ChainDTO;

import java.util.List;

public interface ChainService {

    List<ChainDTO> checkIsActive(String filter);
    List<String> getAllChainNames();
}
