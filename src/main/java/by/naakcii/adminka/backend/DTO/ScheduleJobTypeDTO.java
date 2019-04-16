package by.naakcii.adminka.backend.DTO;

import by.naakcii.adminka.backend.entity.ScheduleJobType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ScheduleJobTypeDTO extends AbstractDTOEntity {

    private Byte id;
    private String name;
    private String beanName;

    public ScheduleJobTypeDTO(ScheduleJobType scheduleJobType) {
        this.id = scheduleJobType.getId();
        this.name = scheduleJobType.getName();
        this.beanName = scheduleJobType.getBeanName();
    }
}
