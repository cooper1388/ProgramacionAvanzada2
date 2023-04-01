package hn.uth.bibliotecavirtual.views.buscarporautor;

import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import hn.uth.bibliotecavirtual.controller.BuscarPorAutorInteractor;
import hn.uth.bibliotecavirtual.controller.BuscarPorAutorInteractorImpl;
import hn.uth.bibliotecavirtual.data.entity.Autor;
import hn.uth.bibliotecavirtual.views.MainLayout;

@PageTitle("Buscar por Autor")
@Route(value = "buscar", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class BuscarporAutorView extends HorizontalLayout implements BuscarPorAutorViewModel {

    private TextField name;
    private Button btnBuscar;
    
    private BuscarPorAutorInteractor controlador;

    public BuscarporAutorView() {
        name = new TextField("Nombre del Autor");
        btnBuscar = new Button("Buscar");
        
        controlador = new BuscarPorAutorInteractorImpl(this);
        
        btnBuscar.addClickListener(e -> {
        	
        	controlador.buscarAutorPorNombre(name.getValue());
        	
            
        });
        btnBuscar.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, btnBuscar);

        add(name, btnBuscar);
    }

	@Override
	public void mostrarRespuestaBusqueda(List<Autor> autores) {
		if(autores== null || autores.isEmpty()) {
			Notification.show("No se encontraron autores con esta búsqueda");
		}else {
			Notification.show("Encontré " + autores.size() +" autores. \n"+" El primero es: "+autores.get(0).getTop_work());

		}
	}

}
