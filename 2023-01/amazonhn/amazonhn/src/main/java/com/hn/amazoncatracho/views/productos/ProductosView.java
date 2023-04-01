package com.hn.amazoncatracho.views.productos;

import com.hn.amazoncatracho.controller.ProductosInteractor;
import com.hn.amazoncatracho.controller.ProductosInteractorImpl;
import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductoCarrito;
import com.hn.amazoncatracho.data.entity.ProductosReport;
import com.hn.amazoncatracho.data.service.ReportGenerator;
import com.hn.amazoncatracho.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Productos")
@Route(value = "productos/:productoID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ProductosView extends Div implements BeforeEnterObserver, ProductosViewModel {

    private final String PRODUCTO_ID = "productoID";
    private final String PRODUCTO_EDIT_ROUTE_TEMPLATE = "productos/%s/edit";

    private final Grid<Producto> grid = new Grid<>(Producto.class, false);

    private ComboBox<String> marca;
    private TextField nombre;
    private NumberField precio;
    private TextArea descripcion;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private Producto producto;
    
    private ProductosInteractor controlador;
    private List<Producto> productos;

    public ProductosView() {
        addClassNames("productos-view");
        
        controlador = new ProductosInteractorImpl(this);

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        LitRenderer<Producto> imagenRenderer = LitRenderer.<Producto>of(
                "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; width: 64px; height: 64px'></span>");
        grid.setColumnReorderingAllowed(true);

        grid.addColumn("marca").setAutoWidth(true);
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn("descripcion").setAutoWidth(true).setResizable(true).setSortable(false);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PRODUCTO_EDIT_ROUTE_TEMPLATE, event.getValue().getIdproducto()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ProductosView.class);
            }
        });
        
        GridContextMenu<Producto> menu = grid.addContextMenu();
        
        GridMenuItem<Producto> comprar = menu.addItem("Agregar al Carrito", event -> {
        	if (event != null && event.getItem() != null) {
        		Producto prodAgregar = event.getItem().get();
        	
        		ProductoCarrito productoCarrito = new ProductoCarrito();
        		productoCarrito.setIdproducto(prodAgregar.getIdproducto());
        		productoCarrito.setCantidad(1);
    			controlador.agregarProductoCarrito(productoCarrito);
        	}
        	
        });
        GridMenuItem<Producto> generarReporte = menu.addItem("Generar Reporte PDF", event -> {
        	Notification.show("Generando reporte PDF...");
    		generarReporte();
        	
        });
        menu.add(new Hr());
        GridMenuItem<Producto> delete = menu.addItem("Eliminar", event -> {
        	if (event != null && event.getItem() != null) {
        		Producto prodEliminar = event.getItem().get();
        		
        		ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("¿Eliminar \'"+prodEliminar.getNombre() + "("+prodEliminar.getMarca()+")\'?");
                dialog.setText("¿Estás seguro de que deseas eliminar de forma permanente este producto?");

                dialog.setCancelable(true);

                dialog.setConfirmText("Eliminar");
                dialog.setCancelText("Cancelar");
                dialog.setConfirmButtonTheme("error primary");

                dialog.addConfirmListener(eventDialog -> {
                	controlador.eliminarProducto(prodEliminar);
                });
        		
                dialog.open();
        	}
        });
        delete.addComponentAsFirst(createIcon(VaadinIcon.TRASH));
        comprar.addComponentAsFirst(createIcon(VaadinIcon.SHOP));
        generarReporte.addComponentAsFirst(createIcon(VaadinIcon.ADD_DOCK));
        
        consultarProductos();

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.producto == null) {
                	//CREACIÓN
                    this.producto = new Producto();
                    this.producto.setNombre(nombre.getValue());
                    this.producto.setMarca(marca.getValue());
                    this.producto.setPrecio(precio.getValue());
                    this.producto.setDescripcion(descripcion.getValue());
                    
                    if(this.producto.getPrecio() == null) {
                    	Notification.show("El precio es requerido, favor digitar un valor valido");
                    }else if(this.producto.getNombre() == null || this.producto.getNombre().isEmpty()) {
                    	Notification.show("El Nombre es requerido, favor proporcione un nombre de producto");
                    }else {
                    	controlador.crearProducto(producto);
                    }
                    
                }else {
                	//ACTUALIZACION
                    this.producto.setNombre(nombre.getValue());
                    this.producto.setMarca(marca.getValue());
                    this.producto.setPrecio(precio.getValue());
                    this.producto.setDescripcion(descripcion.getValue());
                    
                	if(this.producto.getPrecio() == null) {
                    	Notification.show("El precio es requerido, favor digitar un valor valido");
                    }else if(this.producto.getNombre() == null || this.producto.getNombre().isEmpty()) {
                    	Notification.show("El Nombre es requerido, favor proporcione un nombre de producto");
                    }else {
                    	controlador.actualizarProducto(producto);
                    }
                }
                

                
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

	private void generarReporte() {
		ReportGenerator generador = new ReportGenerator();
		ProductosReport datasource = new ProductosReport();
		datasource.setProductos(productos);
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("UBICACION_IMAGEN_DINAMICA", "C://XXX.JPG"); 
		boolean generado = generador.generarReportePDF("reporteproductos", datasource, parameters );
		if(generado) {
			Anchor url = new Anchor(generador.getReportPath(), "Reporte");
			url.setTarget("_blank");
			Notification.show("Reporte Generado: "+generador.getReportPath(), 5000, Notification.Position.TOP_CENTER);
		}else {
			Notification.show("Ocurrió un problema al generar el reporte.");
		}
	}

	private void consultarProductos() {
		controlador.consultarProductos();
	}
	
	private Component createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("color", "var(--lumo-secondary-text-color)")
                .set("margin-inline-end", "var(--lumo-space-s")
                .set("padding", "var(--lumo-space-xs");
        return icon;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> productoId = event.getRouteParameters().get(PRODUCTO_ID).map(Long::parseLong);
        if (productoId.isPresent()) {
            for (Producto producto : productos) {
				if(producto.getIdproducto() == productoId.get()) {
					populateForm(producto);
					break;
				}
			}
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        marca = new ComboBox<>("Marca");
        marca.setItems("Samsung", "Huawei", "One Plus", "Motorola");
        
        
        nombre = new TextField("Nombre");
        
        precio = new NumberField();
        precio.setLabel("Precio");
        precio.setValue(0.0);
        Div dollarPrefix = new Div();
        dollarPrefix.setText("L");
        precio.setPrefixComponent(dollarPrefix);
        
        int charLimit = 600;
        descripcion = new TextArea();
        descripcion.setWidthFull();
        descripcion.setLabel("Descripción");
        descripcion.setMaxLength(charLimit);
        descripcion.setValueChangeMode(ValueChangeMode.EAGER);
        descripcion.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + charLimit);
        });
        
        formLayout.add(marca, nombre, precio, descripcion);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Producto value) {
        this.producto = value;
        if(value == null) {
        	nombre.setValue("");
        	marca.setValue("");
        	precio.setValue(0d);
        	descripcion.setValue("");
        }else {
        	nombre.setValue(value.getNombre());
        	marca.setValue(value.getMarca());
        	precio.setValue(value.getPrecio());
        	descripcion.setValue(value.getDescripcion());
        }
    }
    
    private void actualizarPantalla() {
    	clearForm();
		refreshGrid();
		consultarProductos();
		UI.getCurrent().navigate(ProductosView.class);
    }

	@Override
	public void refrescarGridProductos(List<Producto> productos) {
		Collection<Producto> coleccionItems = productos;
		grid.setItems(coleccionItems);
		this.productos = productos;
	}

	@Override
	public void mostrarMensajeCreacionProducto(Producto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombre()));
		
		actualizarPantalla();
	}

	@Override
	public void mostrarMensajeActualizacionProducto(Producto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombre()));
		
		actualizarPantalla();
	}

	@Override
	public void mostrarMensajeEliminacionProducto(Producto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombre()));
		
		actualizarPantalla();
	}

	@Override
	public void mostrarMensajeProductoAgregadoCarrito(String mensaje) {
		Notification.show(mensaje);
	}
}
