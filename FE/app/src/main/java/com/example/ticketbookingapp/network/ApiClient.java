package com.example.ticketbookingapp.network;

import android.content.Context;
import android.util.Log;

import com.example.ticketbookingapp.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://10.0.2.2:8080/"; // phải có "/"

    private static Retrofit retrofit;
    private static Context appContext; // giữ application context để các nơi có thể gọi getRetrofit() cũ

    // Gọi hàm này ở Activity (nên dùng hàm này)
    public static Retrofit getRetrofit(Context context) {
        if (appContext == null) {
            appContext = context.getApplicationContext();
        }

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor authInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    SessionManager session = new SessionManager(appContext);
                    String token = session.getToken(); // <<< QUAN TRỌNG: lấy token từ SessionManager của bạn

                    if (token == null || token.trim().isEmpty()) {
                        return chain.proceed(original);
                    }

                    Request authed = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build();

                    // log nhanh để chắc chắn header đang được gắn
                    Log.d("ApiClient", "Authorization added: Bearer " + token.substring(0, Math.min(10, token.length())) + "...");

                    return chain.proceed(authed);
                }
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor) // để logging thấy header
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    // GIỮ COMPAT cho code cũ đang gọi getRetrofit()
    public static Retrofit getRetrofit() {
        if (appContext == null) {
            // nếu chưa init bằng getRetrofit(context) thì sẽ không thể attach token
            // tránh crash, nhưng bạn nên đảm bảo gọi getRetrofit(this) ở Activity trước
            return buildWithoutAuth();
        }
        return getRetrofit(appContext);
    }

    public static Retrofit getClient(Context context) {
        return getRetrofit(context);
    }

    public static Retrofit getClient() {
        return getRetrofit();
    }

    // Khi login/logout xong nên reset để chắc chắn token mới được dùng ngay
    public static void reset() {
        retrofit = null;
    }

    private static Retrofit buildWithoutAuth() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
