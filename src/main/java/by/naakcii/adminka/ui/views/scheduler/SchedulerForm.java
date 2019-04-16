package by.naakcii.adminka.ui.views.scheduler;

import by.naakcii.adminka.backend.DTO.ScheduleJobDTO;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.views.CrudForm;
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

    private final FormButtonsBar buttons;
    private ScheduleJobDTO scheduleJobDTO;

    @Autowired
    public SchedulerForm() {
        setSizeFull();
        buttons = new FormButtonsBar();
        add(buttons);
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
    public void setBinder(Binder<ScheduleJobDTO> binder, ScheduleJobDTO entity) {

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
