package hn.uth.bibliotecavirtual.controller;

import java.io.IOException;

import hn.uth.bibliotecavirtual.data.entity.AutoresResponse;
import hn.uth.bibliotecavirtual.data.service.LibrosRepositoryImpl;
import hn.uth.bibliotecavirtual.views.buscarporautor.BuscarPorAutorViewModel;

public class BuscarPorAutorInteractorImpl implements BuscarPorAutorInteractor {

	
	private LibrosRepositoryImpl modelo;
	private BuscarPorAutorViewModel vista;
	
	public BuscarPorAutorInteractorImpl(BuscarPorAutorViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = LibrosRepositoryImpl.getInstance("https://openlibrary.org", 30000L);
	}
	
	@Override
	public void buscarAutorPorNombre(String nombre) {
		try {
			AutoresResponse respuesta = this.modelo.buscarDatosAutor(nombre);
			this.vista.mostrarRespuestaBusqueda(respuesta.getDocs());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
