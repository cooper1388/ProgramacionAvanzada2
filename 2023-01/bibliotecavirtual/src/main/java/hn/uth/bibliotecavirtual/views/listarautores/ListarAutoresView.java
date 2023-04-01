package hn.uth.bibliotecavirtual.views.listarautores;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hn.uth.bibliotecavirtual.data.entity.Libro;
import hn.uth.bibliotecavirtual.views.MainLayout;
import java.util.Optional;

@PageTitle("Listar Autores")
@Route(value = "listar/:libroID?/:action?(edit)", layout = MainLayout.class)
public class ListarAutoresView extends Div implements BeforeEnterObserver {

    private final String LIBRO_ID = "libroID";
    private final String LIBRO_EDIT_ROUTE_TEMPLATE = "listar/%s/edit";

    private final Grid<Libro> grid = new Grid<>(Libro.class, false);

    private TextField autor;
    private TextField obraMasCelebre;
    private DatePicker fechaNacimiento;
    private TextField cantidadLibros;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private Libro libro;

    public ListarAutoresView() {
        addClassNames("listar-autores-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("autor").setAutoWidth(true);
        grid.addColumn("obraMasCelebre").setAutoWidth(true);
        grid.addColumn("fechaNacimiento").setAutoWidth(true);
        grid.addColumn("cantidadLibros").setAutoWidth(true);
        /*grid.setItems(query -> libroService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());*/
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(LIBRO_EDIT_ROUTE_TEMPLATE, event.getValue().getKey()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ListarAutoresView.class);
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });


    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> libroId = event.getRouteParameters().get(LIBRO_ID).map(Long::parseLong);
        if (libroId.isPresent()) {
            //Optional<Libro> libroFromBackend = libroService.get(libroId.get());
            /*if (libroFromBackend.isPresent()) {
                populateForm(libroFromBackend.get());
            } else {
                Notification.show(String.format("The requested libro was not found, ID = %s", libroId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ListarAutoresView.class);
            }*/
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        autor = new TextField("Autor");
        obraMasCelebre = new TextField("Obra Mas Celebre");
        fechaNacimiento = new DatePicker("Fecha Nacimiento");
        cantidadLibros = new TextField("Cantidad Libros");
        formLayout.add(autor, obraMasCelebre, fechaNacimiento, cantidadLibros);

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

    private void populateForm(Libro value) {
        this.libro = value;

    }
}
