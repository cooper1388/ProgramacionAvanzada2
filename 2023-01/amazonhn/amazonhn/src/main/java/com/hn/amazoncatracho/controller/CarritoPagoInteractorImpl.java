package com.hn.amazoncatracho.controller;

import java.io.IOException;

import com.hn.amazoncatracho.data.entity.ProductosCarritoResponse;
import com.hn.amazoncatracho.data.service.DatabaseRepositoryImpl;
import com.hn.amazoncatracho.views.carritopago.CarritoPagoViewModel;

public class CarritoPagoInteractorImpl implements CarritoPagoInteractor {

	private DatabaseRepositoryImpl modelo;
	private CarritoPagoViewModel vista;
	
	public CarritoPagoInteractorImpl(CarritoPagoViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	
	@Override
	public void consultarProductosCarrito() {
		try {
			ProductosCarritoResponse respuesta = this.modelo.consultarProductosCarrito();
			this.vista.mostrarProductosEnCarrito(respuesta.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
