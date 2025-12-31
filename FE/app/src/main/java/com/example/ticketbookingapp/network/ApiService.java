package com.example.ticketbookingapp.network;

import com.example.ticketbookingapp.models.BookingDraft;
import com.example.ticketbookingapp.models.Movie;

import com.example.ticketbookingapp.network.dto.AuthResponse;
import com.example.ticketbookingapp.network.dto.BookingDetailDto;
import com.example.ticketbookingapp.network.dto.CheckoutConfirmRequest;
import com.example.ticketbookingapp.network.dto.CheckoutConfirmResponse;
import com.example.ticketbookingapp.network.dto.FakeBookingRequestDto;
import com.example.ticketbookingapp.network.dto.FakeBookingResponse;
import com.example.ticketbookingapp.network.dto.FilmDto;
import com.example.ticketbookingapp.network.dto.FoodDto;
import com.example.ticketbookingapp.network.dto.HoldRequest;
import com.example.ticketbookingapp.network.dto.LoginRequest;
import com.example.ticketbookingapp.network.dto.MomoPaymentRequest;
import com.example.ticketbookingapp.network.dto.MomoPaymentResponse;
import com.example.ticketbookingapp.network.dto.MovieDetailResponse;
import com.example.ticketbookingapp.network.dto.RegisterRequest;
import com.example.ticketbookingapp.network.dto.SeatDto;
import com.example.ticketbookingapp.network.dto.ShowtimeDetailDto;
import com.example.ticketbookingapp.network.dto.TicketDto;
import com.example.ticketbookingapp.network.dto.TicketHistoryDto;
import com.example.ticketbookingapp.network.dto.UserProfileDto;

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
    @GET("api/films/home")
    Call<List<FilmDto>> getAllFilms();

    @GET("/api/films/{id}")
    Call<MovieDetailResponse> getFilmDetail(@Path("id") int id);
//
    @POST("/api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest body);

    @POST("/api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest body);

    @GET("/api/foods") // VD: /api/foods  -> nếu BE bạn là /api thì baseUrl đã có /api
    Call<List<FoodDto>> getFoods();

    @GET("api/showtimes/film/{filmId}")
    Call<List<ShowtimeDetailDto>> getShowtimesByFilm(@Path("filmId") Integer filmId);

    // Detail 1 showtime (nếu cần)
    @GET("api/showtimes/{showtimeId}")
    Call<ShowtimeDetailDto> getShowtimeDetail(@Path("showtimeId") Integer showtimeId);

    // ApiService.java
    @GET("api/showtimes/{showtimeId}/seats")
    Call<List<SeatDto>> getSeatsByShowtime(@Path("showtimeId") int showtimeId);

    @POST("api/showtimes/{showtimeId}/hold")
    Call<List<SeatDto>> holdSeats(@Path("showtimeId") int showtimeId, @Body HoldRequest body);

    @GET("/api/users/{id}/profile")
    Call<UserProfileDto> getProfile(@Path("id") int userId);

    @POST("momo/pay")
    Call<MomoPaymentResponse> momoPay(@Body MomoPaymentRequest req);

    @POST("momo/confirm")
    Call<MomoPaymentResponse> momoConfirm(@Body MomoPaymentResponse paidInfo);

    @POST("api/showtimes/{showtimeId}/checkout/confirm")
    Call<CheckoutConfirmResponse> checkoutConfirm(
            @Path("showtimeId") int showtimeId,
            @Body CheckoutConfirmRequest req
    );
    @POST("api/bookings/fake")
    Call<FakeBookingResponse> createFakeBooking();
    @POST("/api/bookings/{bookingId}/pay/success")
    Call<Void> paySuccess(@Path("bookingId") int bookingId);

    @POST("api/bookings/fake")
    Call<FakeBookingResponse> createFakeBooking(@Body FakeBookingRequestDto req);

    @GET("api/users/{userId}/profile")
    Call<UserProfileDto> getUserProfile(@Path("userId") int userId);

    // Lịch sử vé theo user
    @GET("api/tickets/user/{userId}")
    Call<List<TicketHistoryDto>> getTicketHistory(@Path("userId") int userId);

    @GET("api/bookings/{bookingId}")
    Call<BookingDetailDto> getBookingDetail(@Path("bookingId") int bookingId);








}
