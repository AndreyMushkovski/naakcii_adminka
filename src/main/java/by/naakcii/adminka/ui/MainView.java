package by.naakcii.adminka.ui;

import by.naakcii.adminka.ui.components.AppNavigation;
import by.naakcii.adminka.ui.components.PageInfo;
import by.naakcii.adminka.ui.utils.AppConsts;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Route(AppConsts.PAGE_MAIN)
@Theme(Material.class)
@PageTitle(AppConsts.TITLE_MAIN)
@Viewport(AppConsts.VIEWPORT)
public class MainView extends VerticalLayout implements RouterLayout, HasUrlParameter<String>, PageConfigurator {

    private final AppNavigation appNavigation;

    @Value("${adminka.token}")
    private String adminkaPath;

    @Autowired
    public MainView(@Value("${adminka.token}") String path) {
        setSizeFull();
        List<PageInfo> pages = new ArrayList<>();
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_PRODUCT + "/" + path,
                AppConsts.ICON_PRODUCT, AppConsts.TITLE_PRODUCT));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_COUNTRY + "/" + path,
                AppConsts.ICON_COUNTRY, AppConsts.TITLE_COUNTRY));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CATEGORY + "/" + path,
                AppConsts.ICON_CATEGORY, AppConsts.TITLE_CATEGORY));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_SUBCATEGORY + "/" + path,
                AppConsts.ICON_SUBCATEGORY, AppConsts.TITLE_SUBCATEGORY));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CHAIN + "/" + path,
                AppConsts.ICON_CHAIN, AppConsts.TITLE_CHAIN));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CHAINPRODUCT + "/" + path,
                AppConsts.ICON_CHAINPRODUCT, AppConsts.TITLE_CHAINPRODUCT));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CHAINPRODUCTTYPE + "/" + path,
                AppConsts.ICON_CHAINPRODUCTTYPE, AppConsts.TITLE_CHAINPRODUCTTYPE));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_MEASURE + "/" + path,
                AppConsts.ICON_MEASURE, AppConsts.TITLE_MEASURE));
        pages.add(new PageInfo(AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_SCHEDULER + "/" + path,
                AppConsts.ICON_SCHEDULER, AppConsts.TITLE_SCHEDULER));
        appNavigation = new AppNavigation();
        appNavigation.init(pages, AppConsts.PAGE_DEFAULT, path);
        add(appNavigation);
        setHorizontalComponentAlignment(Alignment.CENTER, appNavigation);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals(adminkaPath)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addFavIcon("icon", "frontend/icons/favicon.ico", "256x256");
    }
}
