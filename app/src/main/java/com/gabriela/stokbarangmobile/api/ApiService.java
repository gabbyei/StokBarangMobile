package com.gabriela.stokbarangmobile.api;

import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.Product;
import com.gabriela.stokbarangmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Login
    @POST("login")
    Call<ApiResponse<User>> login(@Body LoginRequest request);

    // mengambil daftar produk
    @GET("products")
    Call<ApiResponse<List<Product>>> getProducts(
            @Header("Authorization") String token
    );

    // menambahkan produk
    @POST("products")
    Call<ApiResponse<Product>> addProduct(
            @Header("Authorization") String token,
            @Body Product product
    );

    // mengambil detail produk
    @GET("products/{id}")
    Call<ApiResponse<Product>> getProductDetail(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    // mengedit produk
    @PUT("products/{id}")
    Call<ApiResponse<Product>> updateProduct(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body Product product
    );

    // menghapus produk
    @DELETE("products/{id}")
    Call<ApiResponse<Object>> deleteProduct(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    // Logout
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
