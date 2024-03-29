package com.hn.amazoncatracho.data.service;

import java.io.IOException;

import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductoCarrito;
import com.hn.amazoncatracho.data.entity.ProductosCarritoResponse;
import com.hn.amazoncatracho.data.entity.ProductosResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DatabaseRepositoryImpl {

	private static DatabaseRepositoryImpl INSTANCE;
	private DatabaseClient client;
	
	private DatabaseRepositoryImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}
	
	//PATRON DE INGENIERÍA DE SOFTWARE SINGLETON
	public static DatabaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DatabaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DatabaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	public ProductosResponse consultarProductos() throws IOException {
		Call<ProductosResponse> call = client.getDatabaseService().listarProductos();
		Response<ProductosResponse> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		if(respuesta.isSuccessful()){
			return respuesta.body();
		}else {
			return null;
		}
	}
	
	public boolean crearProducto(Producto nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().crearProducto(nuevo);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
	}
	
	public boolean actualizarProducto(Producto actualizar) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().actualizarProducto(actualizar);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
	}
	
	public boolean eliminarProducto(Integer idProducto) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().eliminarProducto(idProducto);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
	}
	
	public ProductosCarritoResponse consultarProductosCarrito() throws IOException {
		Call<ProductosCarritoResponse> call = client.getDatabaseService().listarProductosCarrito();
		Response<ProductosCarritoResponse> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		if(respuesta.isSuccessful()){
			return respuesta.body();
		}else {
			return null;
		}
	}
	
	public boolean agregarProductoCarrito(ProductoCarrito nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabaseService().agregarProductoCarrito(nuevo);
		Response<ResponseBody> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE BASE DE DATOS
		
		return respuesta.isSuccessful();
	}
}
