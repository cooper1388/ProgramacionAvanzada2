package hn.uth.bibliotecavirtual.views.buscarporautor;

import java.util.List;

import hn.uth.bibliotecavirtual.data.entity.Autor;

public interface BuscarPorAutorViewModel {

	void mostrarRespuestaBusqueda(List<Autor> autores);
}
