package by.naakcii.adminka.ui.views.category;

import by.naakcii.adminka.backend.DTO.CategoryDTO;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.components.ImageUpload;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CategoryForm extends VerticalLayout implements CrudForm<CategoryDTO> {

    private final TextField name;
    private final TextField icon;
    private final TextField priority;
    private final Checkbox isActive;

    private final FormButtonsBar buttons;
    private CategoryDTO categoryDTO;

    @Autowired
    public CategoryForm(@Value("${upload.location}") String uploadLocation, @Value("${images.path.pattern}") String pathPattern) {
        setSizeFull();
        name = new TextField("Категория");
        name.focus();
        name.setWidth("100%");

        icon = new TextField("Адрес картинки");
        priority = new TextField("Порядок отображения");
        isActive = new Checkbox("Активна");
        buttons = new FormButtonsBar();
        uploadLocation = uploadLocation + "CategoryIcons/";
        pathPattern = pathPattern + "CategoryIcons/";
        ImageUpload imageUpload = new ImageUpload(this, uploadLocation, pathPattern);
        HorizontalLayout upload = new HorizontalLayout(icon, imageUpload);

        add(name, upload, priority, isActive, buttons);
    }

    @Override
    public void setBinder(Binder<CategoryDTO> binder, CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
        binder.forField(name)
                .asRequired("Поле не может быть пустым")
                .withValidator(field -> field.trim().length()>=3, "Не менее 3-х символов")
                .withValidator(field -> field.trim().length()<=50, "Не более 50 символов")
                .bind(CategoryDTO::getName, CategoryDTO::setName);
        binder.forField(icon)
                .withValidator(field -> field.length()<=255, "Не более 255 символов")
                .bind(CategoryDTO::getIcon, CategoryDTO::setIcon);
        binder.forField(priority)
                .withValidator(new RegexpValidator("Должны быть только цифры","^[0-9]*$"))
                .withConverter(new StringToIntegerConverter("Должны быть только цифры"))
                .bind(CategoryDTO::getPriority, CategoryDTO::setPriority);
        binder.bind(isActive, "isActive");
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public TextField getImageField() {
        return icon;
    }

    @Override
    public CategoryDTO getDTO() {
        return categoryDTO;
    }

    @Override
    public String getChangedDTOName() {
        return categoryDTO.getName();
    }
}
