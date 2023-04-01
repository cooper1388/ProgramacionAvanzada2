package hn.uth.bibliotecavirtual.data.service;

import hn.uth.bibliotecavirtual.data.entity.AutoresResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface LibrosRepository {

	@Headers({ 
		"Accept: application/json", 
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("/search/authors.json")
	Call<AutoresResponse> buscarDatosAutor(@Query("q") String nombre);


}
