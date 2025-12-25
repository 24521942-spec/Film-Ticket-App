package com.example.ticketbookingapp.network;

import com.example.ticketbookingapp.models.Movie;
import com.example.ticketbookingapp.network.dto.ApiFilmDetail;
import com.example.ticketbookingapp.network.dto.ApiMovie;
import com.example.ticketbookingapp.network.dto.AuthResponse;
import com.example.ticketbookingapp.network.dto.LoginRequest;
import com.example.ticketbookingapp.network.dto.RegisterRequest;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/films")
    Call<List<ApiMovie>> getMovies();

    @GET("/api/films/{id}")
    Call<ApiFilmDetail> getFilmDetail(@Path("id") int filmId);

    @POST("/api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest body);

    @POST("/api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest body);

}
