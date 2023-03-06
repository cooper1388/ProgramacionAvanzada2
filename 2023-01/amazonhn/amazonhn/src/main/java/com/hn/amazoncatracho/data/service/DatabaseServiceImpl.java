package com.hn.amazoncatracho.data.service;

import java.io.IOException;

import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductosResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DatabaseServiceImpl {

	private static DatabaseServiceImpl INSTANCE;
	private DatabaseClient client;
	
	private DatabaseServiceImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}
	
	//PATRON DE INGENIERÍA DE SOFTWARE SINGLETON
	public static DatabaseServiceImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DatabaseServiceImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DatabaseServiceImpl(url, timeout);
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
}
