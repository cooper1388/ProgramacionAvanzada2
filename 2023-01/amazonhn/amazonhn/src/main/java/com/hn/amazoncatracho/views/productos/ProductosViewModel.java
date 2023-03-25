package com.hn.amazoncatracho.views.productos;

import java.util.List;

import com.hn.amazoncatracho.data.entity.Producto;

public interface ProductosViewModel {

	void refrescarGridProductos(List<Producto> productos);
	void mostrarMensajeCreacionProducto(Producto nuevo, String mensaje);
	void mostrarMensajeActualizacionProducto(Producto nuevo, String mensaje);
	void mostrarMensajeEliminacionProducto(Producto nuevo, String mensaje);
	void mostrarMensajeProductoAgregadoCarrito(String mensaje);
	
}
