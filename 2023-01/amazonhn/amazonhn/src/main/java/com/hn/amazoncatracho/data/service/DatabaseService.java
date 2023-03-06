package com.hn.amazoncatracho.data.service;

import com.hn.amazoncatracho.data.entity.Producto;
import com.hn.amazoncatracho.data.entity.ProductosResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DatabaseService {
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/ingenieria_uth/amazonhn/productos")
	Call<ProductosResponse> listarProductos();
	
	
	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/ingenieria_uth/amazonhn/productos")
	Call<ResponseBody> crearProducto(@Body Producto nuevo);
}
