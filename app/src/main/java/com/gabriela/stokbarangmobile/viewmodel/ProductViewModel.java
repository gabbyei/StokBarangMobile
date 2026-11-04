package com.gabriela.stokbarangmobile.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabriela.stokbarangmobile.model.ApiResponse;
import com.gabriela.stokbarangmobile.model.Product;
import com.gabriela.stokbarangmobile.repository.ProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {

    private final ProductRepository repository;

    public MutableLiveData<List<Product>> products = new MutableLiveData<>();
    public MutableLiveData<Product> productDetail = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public ProductViewModel() {
        repository = new ProductRepository();
    }

    // Mengambil daftar barang
    public void loadProducts(String token) {
        isLoading.setValue(true);

        repository.getProducts(token,
                new Callback<ApiResponse<List<Product>>>() {
                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<Product>>> call,
                            Response<ApiResponse<List<Product>>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            products.postValue(response.body().getData());
                            message.postValue(response.body().getMessage());

                        } else {
                            message.postValue("Gagal mengambil data barang.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<Product>>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue("Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }

    // Menambahkan barang
    public void addProduct(String token, Product product) {
        isLoading.setValue(true);

        repository.addProduct(token, product,
                new Callback<ApiResponse<Product>>() {
                    @Override
                    public void onResponse(
                            Call<ApiResponse<Product>> call,
                            Response<ApiResponse<Product>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            message.postValue("Barang berhasil ditambahkan.");
                            loadProducts(token);

                        } else {
                            message.postValue("Gagal menambahkan barang.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Product>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue("Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }

    // Mengambil detail barang
    public void loadProductDetail(String token, int id) {
        isLoading.setValue(true);

        repository.getProductDetail(token, id,
                new Callback<ApiResponse<Product>>() {
                    @Override
                    public void onResponse(
                            Call<ApiResponse<Product>> call,
                            Response<ApiResponse<Product>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            productDetail.postValue(response.body().getData());

                        } else {
                            message.postValue("Gagal mengambil detail barang.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Product>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue("Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }

    // Mengedit barang
    public void updateProduct(String token, int id, Product product) {
        isLoading.setValue(true);

        repository.updateProduct(token, id, product,
                new Callback<ApiResponse<Product>>() {
                    @Override
                    public void onResponse(
                            Call<ApiResponse<Product>> call,
                            Response<ApiResponse<Product>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            message.postValue("Barang berhasil diperbarui.");
                            loadProducts(token);

                        } else {
                            message.postValue("Gagal memperbarui barang.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Product>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue("Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }

    // Menghapus barang
    public void deleteProduct(String token, int id) {
        isLoading.setValue(true);

        repository.deleteProduct(token, id,
                new Callback<ApiResponse<Object>>() {
                    @Override
                    public void onResponse(
                            Call<ApiResponse<Object>> call,
                            Response<ApiResponse<Object>> response) {

                        isLoading.postValue(false);

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            message.postValue("Barang berhasil dihapus.");
                            loadProducts(token);

                        } else {
                            message.postValue("Gagal menghapus barang.");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Object>> call,
                            Throwable t) {

                        isLoading.postValue(false);
                        message.postValue("Gagal terhubung ke server: " + t.getMessage());
                    }
                });
    }
}
