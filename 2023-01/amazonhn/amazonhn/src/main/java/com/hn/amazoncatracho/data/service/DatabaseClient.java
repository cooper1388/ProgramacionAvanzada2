package com.hn.amazoncatracho.data.service;

import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseClient {

	private Retrofit retrofit;
	private HttpLoggingInterceptor interceptor = null;
	
	public DatabaseClient(String url, Long timeout) {
		interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.connectTimeout(timeout, TimeUnit.MILLISECONDS)
				.writeTimeout(timeout, TimeUnit.MILLISECONDS)
				.readTimeout(timeout, TimeUnit.MILLISECONDS)
				.build();
		
		retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()))
				.build();
	}
	
	public DatabaseService getDatabaseService() {
		return retrofit.create(DatabaseService.class);
	}
}
