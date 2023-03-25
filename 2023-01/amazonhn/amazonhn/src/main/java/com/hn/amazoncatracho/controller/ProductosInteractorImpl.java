package com.hn.amazoncatracho.controller;

import java.io.IOException;

import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductoCarrito;
import com.hn.amazoncatracho.data.entity.ProductosResponse;
import com.hn.amazoncatracho.data.service.DatabaseRepositoryImpl;
import com.hn.amazoncatracho.views.productos.ProductosViewModel;

public class ProductosInteractorImpl implements ProductosInteractor {
	
	private DatabaseRepositoryImpl modelo;
	private ProductosViewModel vista;
	
	public ProductosInteractorImpl(ProductosViewModel vista) {
		super();
		this.vista = vista;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	

	@Override
	public void consultarProductos() {
		try {
			ProductosResponse respuesta = this.modelo.consultarProductos();
			this.vista.refrescarGridProductos(respuesta.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearProducto(Producto nuevo) {
		try {
			boolean creado = this.modelo.crearProducto(nuevo);
			String mensaje = creado?"El producto %s fue creado correctamente":"Hubo un inconveniente al crear el producto %s";
			this.vista.mostrarMensajeCreacionProducto(nuevo, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarProducto(Producto actualizar) {
		try {
			boolean actualizado = this.modelo.actualizarProducto(actualizar);
			String mensaje = actualizado?"El producto %s fue actualizado correctamente":"Hubo un inconveniente al actualizar el producto %s";
			this.vista.mostrarMensajeActualizacionProducto(actualizar, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarProducto(Producto eliminar) {
		try {
			boolean eliminado = this.modelo.eliminarProducto(eliminar.getIdproducto());
			String mensaje = eliminado?"El producto %s fue eliminado correctamente":"Hubo un inconveniente al eliminar el producto %s";
			this.vista.mostrarMensajeEliminacionProducto(eliminar, mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void agregarProductoCarrito(ProductoCarrito nuevo) {
		try {
			boolean creado = this.modelo.agregarProductoCarrito(nuevo);
			String mensaje = creado?"El producto fue agregado al carrito":"Hubo un problema al agregar el producto al carrito";
			this.vista.mostrarMensajeProductoAgregadoCarrito(mensaje);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
