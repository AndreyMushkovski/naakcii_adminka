package by.naakcii.adminka.backend.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "SCHEDULE_JOB_TYPE")
public class ScheduleJobType implements Serializable {

    private static final long serialVersionUID = -397384728748404791L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_JOB_TYPE_ID")
    private Byte id;

    @Column(name = "SCHEDULE_JOB_TYPE_NAME")
    private String name;

    @Column(name = "SCHEDULE_JOB_TYPE_BEAN_NAME")
    private String beanName;
}
