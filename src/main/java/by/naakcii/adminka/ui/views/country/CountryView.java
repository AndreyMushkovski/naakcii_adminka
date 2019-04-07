package by.naakcii.adminka.ui.views.country;

import by.naakcii.adminka.backend.DTO.CountryDTO;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.ui.MainView;
import by.naakcii.adminka.ui.utils.AppConsts;
import by.naakcii.adminka.ui.views.CrudForm;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_COUNTRY, layout = MainView.class)
@PageTitle(AppConsts.TITLE_COUNTRY)
public class CountryView extends CrudView<CountryDTO> {

    private Binder<CountryDTO> binder;

    @Autowired
    public CountryView(CrudForm<CountryDTO> form, CrudService<CountryDTO> crudService) {
        super(form, crudService,null);
        binder = new Binder<>(CountryDTO.class);
    }

    @Override
    public Binder<CountryDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(CountryDTO::getName).setHeader("Страна").setSortable(true);
        getGrid().addColumn(CountryDTO::getAlphaCode2).setHeader("Код2").setSortable(true);
        getGrid().addColumn(CountryDTO::getAlphaCode3).setHeader("Код3").setSortable(true);
    }
}
