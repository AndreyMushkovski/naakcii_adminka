package by.naakcii.adminka.ui.views.subcategory;

import by.naakcii.adminka.backend.DTO.SubcategoryDTO;
import by.naakcii.adminka.backend.service.CategoryService;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SubcategoryForm extends VerticalLayout implements CrudForm<SubcategoryDTO> {

    private final TextField name;
    private final ComboBox<String> categoryName;
    private final TextField priority;
    private final Checkbox isActive;
    private final FormButtonsBar buttons;

    private SubcategoryDTO subcategoryDTO;

    @Autowired
    public SubcategoryForm(CategoryService categoryService) {
        setSizeFull();
        name = new TextField("Подкатегория");
        name.focus();
        name.setWidth("100%");

        categoryName = new ComboBox<>("Категория");
        categoryName.setItems(categoryService.getAllCategoriesNames());
        categoryName.setWidth("100%");

        priority = new TextField("Порядок отображения");
        priority.setWidth("100%");
        isActive = new Checkbox("Активна");
        buttons = new FormButtonsBar();

        add(name, categoryName, priority, isActive, buttons);
    }

    @Override
    public FormButtonsBar getButtons() {
        return buttons;
    }

    @Override
    public void setBinder(Binder<SubcategoryDTO> binder, SubcategoryDTO subcategoryDTO) {
        this.subcategoryDTO = subcategoryDTO;
        binder.forField(name)
                .asRequired("Поле не может быть пустым")
                .withValidator(field -> field.trim().length()>=3, "Не менее 3-х символов")
                .withValidator(field -> field.trim().length()<=50, "Не более 50 символов")
                .bind(SubcategoryDTO::getName, SubcategoryDTO::setName);
        binder.forField(categoryName)
                .asRequired("Поле не может быть пустым")
                .bind(SubcategoryDTO::getCategoryName, SubcategoryDTO::setCategoryName);
        binder.forField(priority)
                .withValidator(new RegexpValidator("Должны быть только цифры","^[0-9]*$"))
                .withConverter(new StringToIntegerConverter("Должны быть только цифры"))
                .bind(SubcategoryDTO::getPriority, SubcategoryDTO::setPriority);
        binder.bind(isActive, "isActive");
    }

    @Override
    public SubcategoryDTO getDTO() {
        return subcategoryDTO;
    }

    @Override
    public String getChangedDTOName() {
        return subcategoryDTO.getName();
    }

    @Override
    public TextField getImageField() {
        return null;
    }
}
