package com.gabriela.stokbarangmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gabriela.stokbarangmobile.viewmodel.AuthViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private AuthViewModel authViewModel;

    private boolean alreadyOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        authViewModel = new ViewModelProvider(this)
                .get(AuthViewModel.class);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,
                        "Email dan password harus diisi",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            alreadyOpened = false;
            authViewModel.login(email, password);
        });

        authViewModel.isLoading.observe(this, loading -> {
            if (loading != null) {
                progressBar.setVisibility(
                        loading ? View.VISIBLE : View.GONE);
                btnLogin.setEnabled(!loading);
            }
        });

        authViewModel.message.observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message,
                        Toast.LENGTH_SHORT).show();
            }
        });

        authViewModel.token.observe(this, token -> {
            if (token != null && !token.isEmpty()
                    && !alreadyOpened) {

                alreadyOpened = true;

                Intent intent = new Intent(
                        MainActivity.this, ProductsActivity.class);

                intent.putExtra("TOKEN", token);

                startActivity(intent);
            }
        });
    }
}
