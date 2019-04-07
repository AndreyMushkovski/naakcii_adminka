package by.naakcii.adminka.ui.components;

import by.naakcii.adminka.backend.DTO.AbstractDTOEntity;
import by.naakcii.adminka.ui.views.CrudView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SearchBar<E  extends AbstractDTOEntity> extends HorizontalLayout {

    private final CrudView<E> crudView;
    private final Button addEntity;

    public SearchBar(CrudView<E> crudView, Component filterComponent) {
        this.crudView = crudView;
        TextField search = new TextField("Поиск");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.setPlaceholder("Введите название");
        search.setClearButtonVisible(true);
        search.addValueChangeListener(e-> getCrudView().updateList(e.getValue()));
        addEntity = new Button("Добавить");
        addEntity.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        addEntity.setHeight("70%");
        addEntity.addClickListener(e -> onComponentEvent());

        if (filterComponent!=null) {
            add(search, filterComponent, addEntity);
        } else {
            add(search, addEntity);
        }
        setWidth("100%");
        addEntity.getStyle().set("margin-left", "auto").set("margin-top", "auto");
    }

    private void onComponentEvent() {
        getCrudView().getGrid().asSingleSelect().clear();
        getCrudView().getForm().setBinder(getCrudView().getBinder(), getCrudView().getCrudService().createNewDTO());
        getCrudView().getDialog().open();
    }

    private CrudView<E> getCrudView() {
        return crudView;
    }

    public Button getAddEntity() {
        return addEntity;
    }
}
