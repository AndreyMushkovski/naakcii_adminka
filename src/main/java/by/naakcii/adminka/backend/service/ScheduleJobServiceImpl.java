package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.DTO.ScheduleJobDTO;
import by.naakcii.adminka.backend.entity.ScheduleJob;
import by.naakcii.adminka.backend.repositories.ScheduleJobRepository;
import by.naakcii.adminka.backend.utils.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScheduleJobServiceImpl implements CrudService<ScheduleJobDTO> {

    private final ScheduleJobRepository scheduleJobRepository;
    private final ObjectFactory objectFactory;

    @Autowired
    public ScheduleJobServiceImpl(ScheduleJobRepository scheduleJobRepository, ObjectFactory objectFactory) {
        this.scheduleJobRepository = scheduleJobRepository;
        this.objectFactory = objectFactory;
    }

    @Override
    public List<ScheduleJobDTO> findAllDTOs() {
        return scheduleJobRepository.findAllByOrderByName()
                .stream()
                .filter(Objects::nonNull)
                .map((ScheduleJob scheduleJob) -> objectFactory.getInstance(ScheduleJobDTO.class, scheduleJob))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleJobDTO> searchName(String name) {
        return null;
    }

    @Override
    public ScheduleJobDTO createNewDTO() {
        return null;
    }

    @Override
    public ScheduleJobDTO saveDTO(ScheduleJobDTO entityDTO) {
        return null;
    }

    @Override
    public void deleteDTO(ScheduleJobDTO entityDTO) {

    }
}