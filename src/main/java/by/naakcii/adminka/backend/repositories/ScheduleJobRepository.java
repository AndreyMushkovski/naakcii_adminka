package by.naakcii.adminka.backend.repositories;

import by.naakcii.adminka.backend.entity.ScheduleJob;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleJobRepository extends CrudRepository<ScheduleJob, Integer> {

    List<ScheduleJob> findAllByOrderByName();
}
