package com.gabriela.stokbarangmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this, AddProductActivity.class);

            intent.putExtra("TOKEN", token);
            startActivityForResult(intent, 100);
        });

        btnLogout.setOnClickListener(v -> {
            btnLogout.setEnabled(false);

            // Mengirim token login ke proses logout
            authViewModel.logout(token);
        });

        authViewModel.message.observe(this, message -> {
            if (message == null) return;

            if (message.equals("Logout berhasil.")) {

                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(
                        this, MainActivity.class);

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            } else if (message.startsWith("Logout gagal")
                    || message.startsWith("Gagal logout")
                    || message.equals("Token tidak ditemukan.")) {

                btnLogout.setEnabled(true);

                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();
            }
        });

        loadProducts();
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
