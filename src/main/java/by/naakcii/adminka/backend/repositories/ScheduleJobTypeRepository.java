package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.ScheduleJobType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleJobTypeRepository extends CrudRepository<ScheduleJobType, Byte> {

    ScheduleJobType findByNameIgnoreCase(String name);
    List<ScheduleJobType> findAllByOrderByName();
}
