package com.gabriela.stokbarangmobile.api;

import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.Product;
import com.gabriela.stokbarangmobile.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    // API Login
    @POST("login")
    Call<ApiResponse<User>> login(@Body LoginRequest request);

    // API mengambil data produk
    @GET("products")
    Call<ApiResponse<List<Product>>> getProducts(
            @Header("Authorization") String token
    );

    // API menambahkan produk
    @POST("products")
    Call<ApiResponse<Product>> addProduct(
            @Header("Authorization") String token,
            @Body Product product
    );

    // API Logout
    @POST("logout")
    Call<ApiResponse<Object>> logout(
            @Header("Authorization") String token
    );

    // Data untuk login
    class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
