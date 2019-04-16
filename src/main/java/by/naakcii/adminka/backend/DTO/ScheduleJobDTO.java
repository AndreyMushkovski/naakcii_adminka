package by.naakcii.adminka.backend.DTO;

import by.naakcii.adminka.backend.entity.ScheduleJob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
public class ScheduleJobDTO extends AbstractDTOEntity {

    private Integer id;
    private String name;
    private String cronExpression;
    private String scheduleJobTypeName;
    private String beanName;

    public ScheduleJobDTO(ScheduleJob scheduleJob) {
        this.id = scheduleJob.getId();
        this.name = scheduleJob.getName();
        this.cronExpression = scheduleJob.getCronExpression();
        this.scheduleJobTypeName = scheduleJob.getScheduleJobType().getName();
        this.beanName = scheduleJob.getScheduleJobType().getBeanName();
    }
}
