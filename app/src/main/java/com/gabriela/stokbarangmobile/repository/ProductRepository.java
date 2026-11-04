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

    // Mengambil daftar produk
    public void getProducts(
            String token,
            Callback<ApiResponse<List<Product>>> callback) {

        Call<ApiResponse<List<Product>>> call =
                apiService.getProducts("Bearer " + token);

        call.enqueue(callback);
    }

    // Menambahkan produk
    public void addProduct(
            String token,
            Product product,
            Callback<ApiResponse<Product>> callback) {

        Call<ApiResponse<Product>> call =
                apiService.addProduct("Bearer " + token, product);

        call.enqueue(callback);
    }

    // Mengambil detail produk
    public void getProductDetail(
            String token,
            int id,
            Callback<ApiResponse<Product>> callback) {

        Call<ApiResponse<Product>> call =
                apiService.getProductDetail("Bearer " + token, id);

        call.enqueue(callback);
    }

    // Mengedit produk
    public void updateProduct(
            String token,
            int id,
            Product product,
            Callback<ApiResponse<Product>> callback) {

        Call<ApiResponse<Product>> call =
                apiService.updateProduct("Bearer " + token, id, product);

        call.enqueue(callback);
    }

    // Menghapus produk
    public void deleteProduct(
            String token,
            int id,
            Callback<ApiResponse<Object>> callback) {

        Call<ApiResponse<Object>> call =
                apiService.deleteProduct("Bearer " + token, id);

        call.enqueue(callback);
    }
}
