package by.naakcii.adminka.ui.views;

import by.naakcii.adminka.backend.DTO.AbstractDTOEntity;
import by.naakcii.adminka.ui.components.FormButtonsBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public interface CrudForm<E extends AbstractDTOEntity> {

    FormButtonsBar getButtons();

    TextField getImageField();

    void setBinder(Binder<E> binder, E entity);

    E getDTO();

    String getChangedDTOName();
}
