package com.hn.amazoncatracho.controller;

import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductoCarrito;

public interface ProductosInteractor {

	void consultarProductos();
	void crearProducto(Producto nuevo);
	void agregarProductoCarrito(ProductoCarrito nuevo);
	void actualizarProducto(Producto actualizar);
	void eliminarProducto(Producto eliminar);
}
