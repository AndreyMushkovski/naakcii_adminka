package by.naakcii.adminka.ui.views.scheduler;

import by.naakcii.adminka.backend.DTO.ScheduleJobDTO;
import by.naakcii.adminka.backend.entity.ScheduleJobType;
import by.naakcii.adminka.backend.service.ScheduleJobTypeService;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchedulerForm extends VerticalLayout implements CrudForm<ScheduleJobDTO> {

    private final TextField name;
    private final TextField cronExpression;
    private final ComboBox<String> jobTypeName;
//    private final TextField beanName;

    private final FormButtonsBar buttons;
    private ScheduleJobDTO scheduleJobDTO;

    @Autowired
    public SchedulerForm(ScheduleJobTypeService scheduleJobTypeService) {
        setSizeFull();

        name = new TextField("Название");
        name.focus();
        name.setWidth("100%");

        cronExpression = new TextField("Cron");
        cronExpression.setWidth("100%");

        jobTypeName = new ComboBox<>("Тип задачи");
        jobTypeName.setItems(scheduleJobTypeService.getAllJobTypeNames());
        jobTypeName.setWidth("100%");

//        beanName = new TextField("Имя бина");
//        beanName.setWidth("100%");
        buttons = new FormButtonsBar();
//        getButtons().getSaveButton().setEnabled(false);
        getButtons().getDeleteButton().setEnabled(false);
        add(name, cronExpression, jobTypeName, buttons);
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public TextField getImageField() {
        return null;
    }

    @Override
    public void setBinder(Binder<ScheduleJobDTO> binder, ScheduleJobDTO scheduleJobDTO) {
        this.scheduleJobDTO = scheduleJobDTO;
        binder.forField(name).bind(ScheduleJobDTO::getName, ScheduleJobDTO::setName);
        binder.forField(cronExpression).bind(ScheduleJobDTO::getCronExpression, ScheduleJobDTO::setCronExpression);
        binder.forField(jobTypeName).bind(ScheduleJobDTO::getScheduleJobTypeName, ScheduleJobDTO::setScheduleJobTypeName);
//        binder.forField(beanName).bind(ScheduleJobDTO::getBeanName, ScheduleJobDTO::setBeanName);
    }

    @Override
    public ScheduleJobDTO getDTO() {
        return scheduleJobDTO;
    }

    @Override
    public String getChangedDTOName() {
        return scheduleJobDTO.getName();
    }
}
