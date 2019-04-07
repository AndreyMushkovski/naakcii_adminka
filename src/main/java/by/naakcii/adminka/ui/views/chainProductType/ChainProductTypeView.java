package by.naakcii.adminka.ui.views.chainProductType;

import by.naakcii.adminka.backend.DTO.ChainProductTypeDTO;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.ui.MainView;
import by.naakcii.adminka.ui.utils.AppConsts;
import by.naakcii.adminka.ui.views.CrudForm;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;

@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CHAINPRODUCTTYPE, layout = MainView.class)
@PageTitle(AppConsts.TITLE_CHAINPRODUCTTYPE)
public class ChainProductTypeView extends CrudView<ChainProductTypeDTO> {

    @Value("${no.image}")
    private String noImage;

    private final Binder<ChainProductTypeDTO> binder;

    public ChainProductTypeView(CrudForm<ChainProductTypeDTO> form, CrudService<ChainProductTypeDTO> crudService) {
        super(form, crudService, null);
        binder = new Binder<>(ChainProductTypeDTO.class);
    }

    @Override
    public Binder<ChainProductTypeDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(ChainProductTypeDTO::getName).setSortable(true).setHeader("Акция");
        getGrid().addColumn(ChainProductTypeDTO::getSynonym).setSortable(true).setHeader("Синоним");
        getGrid().addColumn(ChainProductTypeDTO::getTooltip).setHeader("Тултип");
    }
}
