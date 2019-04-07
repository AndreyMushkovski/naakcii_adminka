package by.naakcii.adminka.ui.views.unitOfMeasure;

import by.naakcii.adminka.backend.DTO.UnitOfMeasureDTO;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.ui.MainView;
import by.naakcii.adminka.ui.utils.AppConsts;
import by.naakcii.adminka.ui.views.CrudForm;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_MEASURE, layout = MainView.class)
@PageTitle(AppConsts.TITLE_MEASURE)
public class UnitOfMeasureView extends CrudView<UnitOfMeasureDTO> {

    private final Binder<UnitOfMeasureDTO> binder;

    @Autowired
    public UnitOfMeasureView(CrudForm<UnitOfMeasureDTO> form, CrudService<UnitOfMeasureDTO> crudService) {
        super(form, crudService, null);
        binder = new Binder<>(UnitOfMeasureDTO.class);
    }


    @Override
    public Binder<UnitOfMeasureDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(UnitOfMeasureDTO::getName).setHeader("Единица измерения");
        getGrid().addColumn(UnitOfMeasureDTO::getStep).setHeader("Шаг изменения");
    }
}
