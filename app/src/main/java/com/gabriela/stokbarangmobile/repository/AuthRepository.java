package com.gabriela.stokbarangmobile.repository;

import com.gabriela.stokbarangmobile.api.ApiService;
import com.gabriela.stokbarangmobile.api.RetrofitClient;
import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final ApiService apiService;

    public AuthRepository() {
        apiService = RetrofitClient.getApiService();
    }

    // Proses login
    public void login(String email, String password,
                      Callback<ApiResponse<User>> callback) {

        ApiService.LoginRequest request =
                new ApiService.LoginRequest(email, password);

        Call<ApiResponse<User>> call = apiService.login(request);
        call.enqueue(callback);
    }

    // Proses logout
    public void logout(String token,
                       Callback<ApiResponse<Object>> callback) {

        Call<ApiResponse<Object>> call =
                apiService.logout("Bearer " + token);

        call.enqueue(callback);
    }
}
