package com.example.c196.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.c196.R;
import com.example.c196.database.rModel.RegisteredUser;
import com.example.c196.database.rModel.RegistrationRepository;
import com.example.c196.ui.login.LoginActivity;

public class RegistrationActivity extends AppCompatActivity {

    private TextView studentIdLabel, adminPermissionsLabel;
    private Spinner userTypeSpinner, adminPermissionsSpinner;
    private EditText usernameEditText, passwordEditText;
    private Button registerButton;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize ViewModel with factory
        RegistrationViewModelFactory factory = new RegistrationViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        // Initialize UI components
        studentIdLabel = findViewById(R.id.studentIdLabel);
        adminPermissionsLabel = findViewById(R.id.adminPermissionsLabel);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        adminPermissionsSpinner = findViewById(R.id.adminPermissionsSpinner);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        //Setup spinners
        ArrayAdapter<CharSequence> userTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeAdapter);

        ArrayAdapter<CharSequence> adminPermissionsAdapter = ArrayAdapter.createFromResource(this,
                R.array.admin_permissions, android.R.layout.simple_spinner_item);
        adminPermissionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminPermissionsSpinner.setAdapter(adminPermissionsAdapter);

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                if (selectedItem.equals("Student")) {
                    studentIdLabel.setVisibility(View.VISIBLE);
                    adminPermissionsLabel.setVisibility(View.GONE);
                    adminPermissionsSpinner.setVisibility(View.GONE);
                } else if (selectedItem.equals("Admin")) {
                    studentIdLabel.setVisibility(View.GONE);
                    adminPermissionsLabel.setVisibility(View.VISIBLE);
                    adminPermissionsSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String userType = userTypeSpinner.getSelectedItem().toString();
        String permissions = adminPermissionsSpinner.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //Debugging
        Log.d("RegistrationActivity", "Attempting to register user: " + username);

        viewModel.register(username, password, userType, permissions, new RegistrationRepository.RegistrationCallback() {
            @Override
            public void onSuccess(RegisteredUser user) {
                Log.d("RegistrationActivity", "User registered successfully. Redirecting to login.");
                runOnUiThread(() -> {
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    redirectToLogin();
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("RegistrationActivity", "Registration failed: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}

