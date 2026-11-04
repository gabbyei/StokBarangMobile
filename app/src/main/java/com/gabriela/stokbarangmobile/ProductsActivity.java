package com.gabriela.stokbarangmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gabriela.stokbarangmobile.model.Product;
import com.gabriela.stokbarangmobile.model.ProductAdapter;
import com.gabriela.stokbarangmobile.viewmodel.AuthViewModel;
import com.gabriela.stokbarangmobile.viewmodel.ProductViewModel;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private Button btnAddProduct, btnLogout;

    private ProductViewModel productViewModel;
    private AuthViewModel authViewModel;
    private ProductAdapter adapter;

    private String token;
    private int selectedProductId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listViewProducts = findViewById(R.id.listViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnLogout = findViewById(R.id.btnLogout);

        token = getIntent().getStringExtra("TOKEN");

        adapter = new ProductAdapter(this, new ArrayList<>());
        listViewProducts.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);

        authViewModel = new ViewModelProvider(this)
                .get(AuthViewModel.class);

        productViewModel.products.observe(this, products -> {
            if (products != null) {
                adapter.clear();
                adapter.addAll(products);
                adapter.notifyDataSetChanged();
            }
        });

        productViewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        productViewModel.productDetail.observe(this, product -> {
            if (product != null && product.getId() == selectedProductId) {
                tampilkanDetail(product);
            }
        });

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddProductActivity.class);
            intent.putExtra("TOKEN", token);
            startActivityForResult(intent, 100);
        });

        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product product = adapter.getItem(position);

            if (product != null) {
                selectedProductId = product.getId();
                productViewModel.loadProductDetail(token, selectedProductId);
            }
        });

        btnLogout.setOnClickListener(v -> {
            btnLogout.setEnabled(false);
            authViewModel.logout(token);
        });

        authViewModel.message.observe(this, message -> {
            if (message == null) return;

            if (message.equals("Logout berhasil.")) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                );

                startActivity(intent);
                finish();

            } else if (message.startsWith("Logout gagal")
                    || message.startsWith("Gagal logout")
                    || message.equals("Token tidak ditemukan.")) {

                btnLogout.setEnabled(true);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        loadProducts();
    }

    private void tampilkanDetail(Product product) {

        String detail = "Kode Barang: " + product.getCode()
                + "\nNama Barang: " + product.getName()
                + "\nKategori: " + product.getCategory()
                + "\nSatuan: " + product.getUnit()
                + "\nStok: " + product.getStock()
                + "\nStok Minimum: " + product.getMinimum_stock()
                + "\nLokasi Gudang: " + product.getWarehouse_location()
                + "\nStatus: "
                + (product.getStock() <= product.getMinimum_stock()
                ? "Stok Menipis" : "Stok Aman");

        new AlertDialog.Builder(this)
                .setTitle("Detail Barang")
                .setMessage(detail)
                .setPositiveButton("Edit", (dialog, which) -> {
                    bukaFormEdit(product);
                })
                .setNegativeButton("Hapus", (dialog, which) -> {
                    konfirmasiHapus(product);
                })
                .setNeutralButton("Tutup", null)
                .show();
    }

    private void bukaFormEdit(Product product) {
        Intent intent = new Intent(this, AddProductActivity.class);

        intent.putExtra("TOKEN", token);
        intent.putExtra("PRODUCT_ID", product.getId());
        intent.putExtra("CODE", product.getCode());
        intent.putExtra("NAME", product.getName());
        intent.putExtra("CATEGORY", product.getCategory());
        intent.putExtra("UNIT", product.getUnit());
        intent.putExtra("STOCK", product.getStock());
        intent.putExtra("MINIMUM_STOCK", product.getMinimum_stock());
        intent.putExtra("WAREHOUSE_LOCATION", product.getWarehouse_location());

        startActivityForResult(intent, 100);
    }

    private void konfirmasiHapus(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Barang")
                .setMessage("Yakin ingin menghapus barang "
                        + product.getName() + "?")
                .setPositiveButton("Ya, Hapus", (dialog, which) -> {
                    productViewModel.deleteProduct(token, product.getId());
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void loadProducts() {
        if (token == null || token.isEmpty()) {
            Toast.makeText(this,
                    "Token login tidak ditemukan",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        productViewModel.loadProducts(token);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            loadProducts();
        }
    }
}