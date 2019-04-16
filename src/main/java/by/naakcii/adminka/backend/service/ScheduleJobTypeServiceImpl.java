package by.naakcii.adminka.backend.service;

import by.naakcii.adminka.backend.entity.ScheduleJobType;
import by.naakcii.adminka.backend.repositories.ScheduleJobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleJobTypeServiceImpl implements ScheduleJobTypeService {

    private final ScheduleJobTypeRepository scheduleJobTypeRepository;

    @Autowired
    public ScheduleJobTypeServiceImpl(ScheduleJobTypeRepository scheduleJobTypeRepository) {
        this.scheduleJobTypeRepository = scheduleJobTypeRepository;
    }

    @Override
    public List<String> getAllJobTypeNames() {
        return scheduleJobTypeRepository.findAllByOrderByName()
                .stream()
                .map(ScheduleJobType::getName)
                .collect(Collectors.toList());
    }
}
