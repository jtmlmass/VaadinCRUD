package com.vaadin.example.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CustomerForm extends FormLayout {
    //Campos
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    private DatePicker birthDate = new DatePicker("Birthdate");

    //Botones
    private Button save = new Button("Save");
    private Button delete = new Button("delete");

    //Data
    private Binder<Customer> binder = new Binder<>(Customer.class);
    private CustomerService service = CustomerService.getInstance();
    private MainView mainView;

    public CustomerForm(MainView mainView){
        this.mainView = mainView;

        status.setItems(CustomerStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> delete());

        add(firstName, lastName, status, birthDate, buttons);
        binder.bindInstanceFields(this);
    }

    public void setCustomer(Customer customer) {
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private void save() {
        Customer customer = binder.getBean();
        service.save(customer);
        mainView.updateList();
        setCustomer(null);
    }

    private void delete() {
        Customer customer = binder.getBean();
        service.delete(customer);
        mainView.updateList();
        setCustomer(null);
    }

}
