package hn.uth.bibliotecavirtual.data.service;

import java.io.IOException;

import hn.uth.bibliotecavirtual.data.entity.AutoresResponse;
import retrofit2.Call;
import retrofit2.Response;

public class LibrosRepositoryImpl {

	private static LibrosRepositoryImpl INSTANCE;
	private LibrosClient client;
	
	private LibrosRepositoryImpl(String url, Long timeout) {
		client = new LibrosClient(url, timeout);
	}
	
	//PATRON DE INGENIERÍA DE SOFTWARE SINGLETON
	public static LibrosRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (LibrosRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new LibrosRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	public AutoresResponse buscarDatosAutor(String nombre) throws IOException {
		Call<AutoresResponse> call = client.getLibrosService().buscarDatosAutor(nombre);
		Response<AutoresResponse> respuesta = call.execute();//AQUI ES DONDE SE HACE EL LLAMADO AL SERVICIO DE LIBROS
		if(respuesta.isSuccessful()){
			return respuesta.body();
		}else {
			return null;
		}
	}
}
