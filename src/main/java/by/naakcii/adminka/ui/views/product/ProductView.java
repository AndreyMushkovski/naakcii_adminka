package by.naakcii.adminka.ui.views.product;

import by.naakcii.adminka.backend.DTO.ProductDTO;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.backend.service.ProductService;
import by.naakcii.adminka.ui.MainView;
import by.naakcii.adminka.ui.utils.AppConsts;
import by.naakcii.adminka.ui.views.CrudForm;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@JavaScript("styles/script.js")
//@HtmlImport("styles/styles.html")
@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_PRODUCT, layout = MainView.class)
@PageTitle(AppConsts.TITLE_PRODUCT)
public class ProductView extends CrudView<ProductDTO> {

    @Value("${no.image}")
    private String noImage;

    private final Binder<ProductDTO> binder;

    @Autowired
    public ProductView(CrudForm<ProductDTO> form, CrudService<ProductDTO> crudService, ProductService productService) {
        super(form, crudService, new ComboBox<String>("Фильтр"));
        binder = new Binder<>(ProductDTO.class);
        ComboBox<String> filter = (ComboBox<String>) getFilterComponent();
        filter.setItems("Активные", "Неактивные");
        filter.setValue("Активные");
        getGrid().setItems(productService.checkIsActive(filter.getValue()));
        filter.addValueChangeListener(e-> getGrid().setItems(productService.checkIsActive(e.getValue())));
    }

    @Override
    public Binder<ProductDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(new ComponentRenderer<>(productDTO -> {
            if ((productDTO.getPicture() != null) && !StringUtils.isEmpty(productDTO.getPicture())) {
                Image image = new Image(productDTO.getPicture(), productDTO.getName());
                image.setWidth("50px");
                image.setHeight("50px");
                image.addClassNames("small-image");
                return image;
            } else {
                Image imageEmpty = new Image(noImage, "No image");
                imageEmpty.setHeight("50px");
                imageEmpty.setWidth("50px");
                return imageEmpty;
            }}
        ))
            .setHeader("Изображение");
        getGrid().addColumn(ProductDTO::getName).setFlexGrow(4).setHeader("Товар").setSortable(true);
        getGrid().addColumn(ProductDTO::getCategoryName).setHeader("Категория").setSortable(true);
        getGrid().addColumn(ProductDTO::getSubcategoryName).setHeader("Подкатегория").setSortable(true);
//        getGrid().addColumn(new ComponentRenderer<>(productDTO -> {
//            Checkbox isActive = new Checkbox();
//            isActive.setReadOnly(true);
//            isActive.setValue(productDTO.getIsActive());
//            return isActive;
//        })).setHeader("Активный").setSortable(true).setFlexGrow(0);
    }
}
