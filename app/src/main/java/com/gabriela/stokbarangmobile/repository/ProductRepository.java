package com.gabriela.stokbarangmobile.repository;

import com.gabriela.stokbarangmobile.api.ApiService;
import com.gabriela.stokbarangmobile.api.RetrofitClient;
import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductRepository {

    private final ApiService apiService;

    public ProductRepository() {
        apiService = RetrofitClient.getApiService();
    }

    // Mengambil daftar produk dari Laravel
    public void getProducts(
            String token,
            Callback<ApiResponse<List<Product>>> callback) {

        Call<ApiResponse<List<Product>>> call =
                apiService.getProducts("Bearer " + token);

        call.enqueue(callback);
    }

    // Menambahkan produk ke Laravel
    public void addProduct(
            String token,
            Product product,
            Callback<ApiResponse<Product>> callback) {

        Call<ApiResponse<Product>> call =
                apiService.addProduct("Bearer " + token, product);

        call.enqueue(callback);
    }
}
