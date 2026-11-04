package com.gabriela.stokbarangmobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gabriela.stokbarangmobile.model.Product;
import com.gabriela.stokbarangmobile.viewmodel.ProductViewModel;

public class AddProductActivity extends AppCompatActivity {

    private EditText etCode, etName, etCategory, etUnit;
    private EditText etStock, etMinimumStock, etWarehouseLocation;
    private Button btnSaveProduct;

    private ProductViewModel productViewModel;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        etCategory = findViewById(R.id.etCategory);
        etUnit = findViewById(R.id.etUnit);
        etStock = findViewById(R.id.etStock);
        etMinimumStock = findViewById(R.id.etMinimumStock);
        etWarehouseLocation = findViewById(R.id.etWarehouseLocation);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);

        productViewModel = new ViewModelProvider(this)
                .get(ProductViewModel.class);

        token = getIntent().getStringExtra("TOKEN");

        btnSaveProduct.setOnClickListener(v -> saveProduct());

        productViewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();

                if (message.equals("Barang berhasil ditambahkan.")) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void saveProduct() {

        String code = etCode.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String stockText = etStock.getText().toString().trim();
        String minimumText = etMinimumStock.getText().toString().trim();
        String location = etWarehouseLocation.getText().toString().trim();

        if (code.isEmpty() || name.isEmpty() || category.isEmpty()
                || unit.isEmpty() || stockText.isEmpty()
                || minimumText.isEmpty() || location.isEmpty()) {

            Toast.makeText(this, "Semua kolom harus diisi",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token login tidak ditemukan",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setCategory(category);
        product.setUnit(unit);
        product.setStock(Integer.parseInt(stockText));
        product.setMinimum_stock(Integer.parseInt(minimumText));
        product.setWarehouse_location(location);

        productViewModel.addProduct(token, product);
    }
}
