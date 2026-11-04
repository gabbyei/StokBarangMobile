package com.gabriela.stokbarangmobile.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.User;
import com.gabriela.stokbarangmobile.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<String> token = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public AuthViewModel() {
        repository = new AuthRepository();
    }

    public void login(String email, String password) {
        isLoading.setValue(true);

        repository.login(email, password,
                new Callback<ApiResponse<User>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<User>> call,
                            Response<ApiResponse<User>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            ApiResponse<User> result = response.body();

                            user.postValue(result.getUser());
                            token.postValue(result.getToken());
                            message.postValue(result.getMessage());

                        } else {
                            message.postValue(
                                    "Login gagal. Periksa email dan password.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<User>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue(
                                "Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }

    public void logout(String savedToken) {

        if (savedToken == null || savedToken.isEmpty()) {
            message.setValue("Token tidak ditemukan.");
            return;
        }

        repository.logout(savedToken,
                new Callback<ApiResponse<Object>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<Object>> call,
                            Response<ApiResponse<Object>> response) {

                        if (response.isSuccessful()) {
                            token.postValue(null);
                            user.postValue(null);
                            message.postValue("Logout berhasil.");
                        } else {
                            message.postValue("Logout gagal.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Object>> call,
                            Throwable t) {

                        message.postValue(
                                "Gagal logout: " + t.getMessage());
                    }
                });
    }
}
