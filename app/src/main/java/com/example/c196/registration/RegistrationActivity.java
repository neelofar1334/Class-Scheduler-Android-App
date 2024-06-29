package com.example.c196.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.c196.R;

public class RegistrationActivity extends AppCompatActivity {

    private TextView studentIdLabel, adminPermissionsLabel;
    private Spinner userTypeSpinner, adminPermissionsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize ViewModel
        RegistrationViewModelFactory factory = new RegistrationViewModelFactory(getApplication());
        RegistrationViewModel viewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        studentIdLabel = findViewById(R.id.studentIdLabel);
        adminPermissionsLabel = findViewById(R.id.adminPermissionsLabel);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        adminPermissionsSpinner = findViewById(R.id.adminPermissionsSpinner);

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
    }
}
