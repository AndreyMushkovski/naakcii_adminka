package by.naakcii.adminka.ui.views;

import by.naakcii.adminka.backend.DTO.AbstractDTOEntity;
import by.naakcii.adminka.backend.service.CrudService;
import by.naakcii.adminka.ui.components.SearchBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public abstract class CrudView<E extends AbstractDTOEntity> extends VerticalLayout implements HasUrlParameter<String> {

    @Value("${adminka.token}")
    private String adminkaPath;

    private final CrudService<E> crudService;
    private final Dialog dialog = new Dialog();
    private final CrudForm<E> form;
    private final Grid<E> grid;
    private final Component filterComponent;
    private final SearchBar searchBar;

    public abstract Binder<E> getBinder();

    protected abstract void setupGrid();

    public CrudView(CrudForm<E> form, CrudService<E> crudService, Component filterComponent) {
        this.form = form;
        this.crudService = crudService;
        this.filterComponent = filterComponent;
        setSizeFull();

        dialog.add((Component) getForm());

        searchBar = new SearchBar(this, filterComponent);
        
        grid = new Grid<>();
        setupGrid();
        updateList(null);
        add(searchBar, grid);

        grid.asSingleSelect().addValueChangeListener(e-> {
            getForm().setBinder(getBinder(), e.getValue());
            getBinder().readBean(e.getValue());
            dialog.open();
        });

        //drag and drop columns order
        grid.setColumnReorderingAllowed(true);

        setupEventListeners();
        grid.getDataProvider().refreshAll();

        getForm().getButtons().getSaveButton().addClickShortcut(Key.ENTER);
    }

    private void setupEventListeners() {
        getForm().getButtons().addSaveListener(e -> save());
        getForm().getButtons().addCancelListener(e -> cancel());
        getForm().getButtons().addDeleteListener(e -> delete());

        getDialog().getElement().addEventListener("opened-changed", e -> {
            if(!getDialog().isOpened()) {
                cancel();
            }
        });
    }

    private void save() {
        E entityDTO = getForm().getDTO();
        boolean isValid = getBinder().writeBeanIfValid(entityDTO);
        if(isValid) {
                try {
                    E savedEntity = crudService.saveDTO(entityDTO);
                    if (savedEntity != null) {
                        Notification.show(getForm().getChangedDTOName() + " сохранён");
                        closeUpdate();
                    }
                } catch (Exception e) {
                    errorNotification(e);
                }
            }
    }

    private void errorNotification(Exception e) {
        e.printStackTrace();
        NativeButton button = new NativeButton("Закрыть");
        Label error = new Label(e.toString());
        Notification notification = new Notification(error, button);
        notification.setPosition(Notification.Position.TOP_STRETCH);
        notification.open();
        button.addClickListener(event -> notification.close());
    }

    private void cancel() {
        getGrid().asSingleSelect().clear();
        getDialog().close();
        getGrid().getDataProvider().refreshAll();

    }

    private void delete() {
        E entityDTO = getForm().getDTO();
        try {
            crudService.deleteDTO(entityDTO);
            Notification.show(getForm().getChangedDTOName() + " удалён");
            closeUpdate();
        } catch (Exception e) {
            errorNotification(e);
        }
    }

    private void closeUpdate() {
        getGrid().asSingleSelect().clear();
        getDialog().close();
        updateList(null);
        grid.getDataProvider().refreshAll();
    }

    public void updateList(String search) {
        if(StringUtils.isEmpty(search)) {
            grid.setItems(crudService.findAllDTOs());
        } else {
            grid.setItems(crudService.searchName(search));
        }
    }

    public CrudForm<E> getForm() {
        return form;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public Grid<E> getGrid() {return grid;}

    public CrudService<E> getCrudService() {
        return crudService;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public Component getFilterComponent() {
        return filterComponent;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals(adminkaPath)) {
            throw new IllegalArgumentException();
        }
    }
}
