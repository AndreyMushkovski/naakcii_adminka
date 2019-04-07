package by.naakcii.adminka.ui.views.chainProductType;

import by.naakcii.adminka.backend.DTO.ChainProductTypeDTO;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import by.naakcii.adminka.ui.views.CrudForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChainProductTypeForm extends VerticalLayout implements CrudForm<ChainProductTypeDTO> {

    private final TextField name;
    private final TextArea tooltip;
    private final TextField synonym;

    private final FormButtonsBar buttons;
    private ChainProductTypeDTO chainProductTypeDTO;

    @Autowired
    public ChainProductTypeForm() {
        setSizeFull();
        name = new TextField("Акция");
        name.focus();
        name.setWidth("100%");

        tooltip = new TextArea("Тултип");
        tooltip.setWidth("100%");

        synonym = new TextField("Синоним");
        synonym.setWidth("100%");
        buttons = new FormButtonsBar();

        add(name, synonym, tooltip, buttons);
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
    public void setBinder(Binder<ChainProductTypeDTO> binder, ChainProductTypeDTO chainProductTypeDTO) {
        this.chainProductTypeDTO = chainProductTypeDTO;
        binder.forField(name)
                .asRequired("Поле не может быть пустым")
                .withValidator(field -> field.trim().length()>=3, "Не менее 3-х символов")
                .withValidator(field -> field.trim().length()<=25, "Не более 25 символов")
                .bind(ChainProductTypeDTO::getName, ChainProductTypeDTO::setName);
        binder.forField(synonym)
                .asRequired("Поле не может быть пустым")
                .withValidator(field -> field.trim().length()>=3, "Не менее 3-х символов")
                .withValidator(field -> field.trim().length()<=25, "Не более 25 символов")
                .bind(ChainProductTypeDTO::getSynonym, ChainProductTypeDTO::setSynonym);
        binder.forField(tooltip)
                .withValidator(field -> field.length()<=255, "Не более 255 символов")
                .bind(ChainProductTypeDTO::getTooltip, ChainProductTypeDTO::setTooltip);
    }

    @Override
    public ChainProductTypeDTO getDTO() {
        return chainProductTypeDTO;
    }

    @Override
    public String getChangedDTOName() {
        return chainProductTypeDTO.getName();
    }
}
