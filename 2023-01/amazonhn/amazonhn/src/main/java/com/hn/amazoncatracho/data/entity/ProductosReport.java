package com.hn.amazoncatracho.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ProductosReport implements JRDataSource {

	private List<Producto> productos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
		this.maxCounter = this.productos.size() -1;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getMaxCounter() {
		return maxCounter;
	}
	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}
	@Override
	public boolean next() throws JRException {
		if(counter < maxCounter) {
			counter++;
			return true;
		}
		return false;
	}
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if("NombreProducto".equals(jrField.getName())) {
			return productos.get(counter).getNombre();
		}else if("NombreMarca".equals(jrField.getName())){
			return productos.get(counter).getMarca();
		}else if("Precio".equals(jrField.getName())){
			return String.format("%,.2f", productos.get(counter).getPrecio());
		}

		return "";
	}
	
	
}
