package by.naakcii.adminka.ui.views.scheduler;

import by.naakcii.adminka.backend.DTO.ScheduleJobDTO;
import by.naakcii.adminka.backend.service.ScheduleJobTypeService;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
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
        binder.forField(name)
                .asRequired("Поле не может быть пустым")
                .bind(ScheduleJobDTO::getName, ScheduleJobDTO::setName);
        binder.forField(cronExpression)
                .asRequired("Поле не может быть пустым")
                .withValidator(new RegexpValidator("Неверное cron выражение", "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$"))
                .bind(ScheduleJobDTO::getCronExpression, ScheduleJobDTO::setCronExpression);
        binder.forField(jobTypeName)
                .asRequired("Поле не может быть пустым")
                .bind(ScheduleJobDTO::getScheduleJobTypeName, ScheduleJobDTO::setScheduleJobTypeName);
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
