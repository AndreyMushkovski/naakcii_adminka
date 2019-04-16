package by.naakcii.adminka.ui.views.scheduler;

import by.naakcii.adminka.backend.DTO.ScheduleJobDTO;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.ui.MainView;
import by.naakcii.adminka.ui.utils.AppConsts;
import by.naakcii.adminka.ui.views.CrudForm;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_SCHEDULER, layout = MainView.class)
@PageTitle(AppConsts.TITLE_SCHEDULER)
public class SchedulerView extends CrudView<ScheduleJobDTO> {

    private final Binder<ScheduleJobDTO> binder;

    @Autowired
    public SchedulerView(CrudForm<ScheduleJobDTO> form, CrudService<ScheduleJobDTO> crudService) {
        super(form, crudService, null);
        binder = new Binder<>(ScheduleJobDTO.class);
    }
    @Override
    public Binder<ScheduleJobDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(ScheduleJobDTO::getName).setHeader("Job Name").setSortable(true);
        getGrid().addColumn(ScheduleJobDTO::getCronExpression).setHeader("Cron");
        getGrid().addColumn(ScheduleJobDTO::getScheduleJobTypeName).setHeader("Job Type Name");
        getGrid().addColumn(ScheduleJobDTO::getBeanName).setHeader("Bean name");
    }
}
