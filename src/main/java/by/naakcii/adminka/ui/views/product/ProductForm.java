package by.naakcii.adminka.ui.views.product;

import by.naakcii.adminka.backend.DTO.ProductDTO;
import by.naakcii.adminka.backend.service.CategoryService;
import by.naakcii.adminka.backend.service.CountryService;
import by.naakcii.adminka.backend.service.SubcategoryService;
import by.naakcii.adminka.backend.service.UnitOfMeasureService;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.components.ImageUpload;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Tag("product-dialog")
//@HtmlImport("styles/product-dialog.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductForm extends VerticalLayout implements CrudForm<ProductDTO> {

    private ProductDTO productDTO;

    private final TextField name;
    private final TextField picture;
    private final ComboBox<String> categoryName;
    private ComboBox<String> subcategoryName;
    private final TextField barcode;
    private final ComboBox<String> unitOfMeasureName;
    private final TextField manufacturer;
    private final TextField brand;
    private final ComboBox<String> countryOfOriginName;
    private final Checkbox isActive;
    private final FormButtonsBar buttons;

    @Autowired
    public ProductForm(CategoryService categoryService, SubcategoryService subcategoryService,
                       UnitOfMeasureService unitOfMeasureService, CountryService countryService,
                       @Value("${upload.location}") String uploadLocation, @Value("${images.path.pattern}") String pathPattern) {

        setSizeFull();
        name = new TextField("Наименование товара");
        name.setWidth("100%");

        picture = new TextField("Адрес картинки");
        picture.setWidth("50%");
        ImageUpload imageUpload = new ImageUpload(this, uploadLocation, pathPattern);
        imageUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        HorizontalLayout chosePic = new HorizontalLayout(picture, imageUpload);

        categoryName = new ComboBox<>("Категория");
        categoryName.setItems(categoryService.getAllActiveCategoriesNames());
        categoryName.addValueChangeListener(event -> subcategoryName.setItems(subcategoryService.getAllSubcategoriesNames(event.getValue())));
        categoryName.setWidth("50%");
        subcategoryName = new ComboBox<>("Подкатегория");
        subcategoryName.setWidth("50%");
        HorizontalLayout categories = new HorizontalLayout(categoryName, subcategoryName);

        barcode = new TextField("Штрих-код");
        barcode.setWidth("50%");
        unitOfMeasureName = new ComboBox<>("Единица измерения");
        unitOfMeasureName.setItems(unitOfMeasureService.getAllUnitOfMeasureNames());
        unitOfMeasureName.setWidth("50%");
        HorizontalLayout layout1 = new HorizontalLayout(barcode, unitOfMeasureName);

        manufacturer = new TextField("Производитель");
        manufacturer.setWidth("50%");
        brand = new TextField("Торговая марка");
        brand.setWidth("50%");
        HorizontalLayout layout2 = new HorizontalLayout(manufacturer, brand);

        countryOfOriginName = new ComboBox<>("Страна происхождения");
        countryOfOriginName.setItems(countryService.getAllCountryNames());
        countryOfOriginName.setWidth("50%");
        isActive = new Checkbox("Активный");
        buttons = new FormButtonsBar();
        add(name, chosePic, categories, layout1, layout2,
                countryOfOriginName, isActive, buttons);
    }

    @Override
    public void setBinder(Binder<ProductDTO> binder, ProductDTO productDTO) {
        this.productDTO = productDTO;
        binder.forField(name).asRequired("Наименование товара не может быть пустым")
                .withValidator(field -> field.trim().length()>=3, "Не менее 3-х символов")
                .withValidator(field -> field.trim().length()<=100, "Не более 100 символов")
                .bind(ProductDTO::getName, ProductDTO::setName);
        binder.forField(picture)
                .withValidator(field -> field.length()<=255, "Не более 255 символов")
                .bind(ProductDTO::getPicture, ProductDTO::setPicture);
        binder.forField(categoryName)
                .asRequired("Поле не может быть пустым")
                .bind(ProductDTO::getCategoryName, ProductDTO::setCategoryName);
        binder.forField(subcategoryName)
                .withValidator(field -> (!field.isEmpty()), "Поле не может быть пустым")
                .bind(ProductDTO::getSubcategoryName, ProductDTO::setSubcategoryName);
        binder.forField(barcode)
                .asRequired("Поле не может быть пустым")
                .withValidator(field -> (field.length() == 4 || field.length() == 8 || field.length() == 12
                    || field.length() == 13 || field.length() == 14), "4, 8, 12, 13 или 14 символов")
                .withValidator(new RegexpValidator("Должны быть только цифры","^[0-9]*$"))
                .bind(ProductDTO::getBarcode, ProductDTO::setBarcode);
        binder.forField(unitOfMeasureName).asRequired("Поле не может быть пустым").bind(ProductDTO::getUnitOfMeasureName, ProductDTO::setUnitOfMeasureName);
        binder.forField(manufacturer)
                .withValidator(field -> field.length()<=50, "Не более 50 символов")
                .bind(ProductDTO::getManufacturer, ProductDTO::setManufacturer);
        binder.forField(brand)
                .withValidator(field -> field.length()<=50, "Не более 50 символов")
                .bind(ProductDTO::getBrand, ProductDTO::setBrand);
        binder.bind(countryOfOriginName, "countryOfOriginName");
        binder.bind(isActive, "isActive");
    }

    @Override
    public ProductDTO getDTO() {
        return productDTO;
    }

    @Override
    public String getChangedDTOName() {
        return productDTO.getName();
    }

    @Override
    public TextField getImageField() {
        return picture;
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }
}
