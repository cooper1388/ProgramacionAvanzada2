package com.hn.amazoncatracho.views.buscarproducto;

import java.time.LocalTime;

import com.hn.amazoncatracho.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Buscar Producto")
@Route(value = "buscar-producto", layout = MainLayout.class)
public class BuscarProductoView extends HorizontalLayout {

	private EmailField validEmailField;
	private PasswordField passwordField;
	private TimePicker timePicker;
	private DateTimePicker dateTimePicker;

    public BuscarProductoView() {
    	FormLayout form1 = new FormLayout();
    	
    	form1.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), 
    			new FormLayout.ResponsiveStep("300px", 2));
    	
    	validEmailField = new EmailField();
    	validEmailField.setLabel("Correo Electrónico");
    	validEmailField.getElement().setAttribute("name", "email");
    	validEmailField.setValue("kenny.cooper@uth.hn");
    	validEmailField.setErrorMessage("Ingrese un correo Válido");
    	validEmailField.setClearButtonVisible(true);
    	
    	passwordField = new PasswordField();
    	passwordField.setLabel("Contraseña");
    	passwordField.setValue("Ex@mplePassw0rd");
    	
    	timePicker = new TimePicker();
    	timePicker.setLabel("Inicio de la Clase");
    	timePicker.setValue(LocalTime.of(5, 40));
    	
    	dateTimePicker = new DateTimePicker();
    	dateTimePicker.setLabel("Meeting date and time");
    	
    	form1.add(validEmailField, passwordField, timePicker, dateTimePicker);
    	
    	form1.setColspan(validEmailField, 2);
    	form1.setColspan(dateTimePicker, 2);
    	
    	add(form1);
    	
    }
}
