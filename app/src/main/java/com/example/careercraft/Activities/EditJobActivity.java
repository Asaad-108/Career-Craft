package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careercraft.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditJobActivity extends AppCompatActivity {
    EditText etJobTitle, etJobDesc, etCompanyName, etSalary, etJobType, etLocation;
    TextView btnUpdate;
    DatabaseReference jobRef;
    String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_job);

        etJobTitle = findViewById(R.id.etJobTitle);
        etJobDesc = findViewById(R.id.etJobDesc);
        etCompanyName = findViewById(R.id.etCompanyName);
        etSalary = findViewById(R.id.etSalary);
        etJobType = findViewById(R.id.etJobType);
        etLocation = findViewById(R.id.etLocation);
        btnUpdate = findViewById(R.id.btnUpdate);

        jobRef = FirebaseDatabase.getInstance().getReference("Job");

        // Get job details from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobId")) {
            jobId = intent.getStringExtra("jobId");
            etJobTitle.setText(intent.getStringExtra("jobTitle"));
            etJobDesc.setText(intent.getStringExtra("jobDesc"));
            etCompanyName.setText(intent.getStringExtra("companyName"));
            etSalary.setText(intent.getStringExtra("salary"));
            etJobType.setText(intent.getStringExtra("jobType"));
            etLocation.setText(intent.getStringExtra("location"));
        }

        btnUpdate.setOnClickListener(v -> updateJob());
    }

    private void updateJob() {
        String newTitle = etJobTitle.getText().toString().trim();
        String newDesc = etJobDesc.getText().toString().trim();
        String newCompany = etCompanyName.getText().toString().trim();
        String newSalary = etSalary.getText().toString().trim();
        String newJobType = etJobType.getText().toString().trim();
        String newLocation = etLocation.getText().toString().trim();

        if (newTitle.isEmpty() || newDesc.isEmpty() || newCompany.isEmpty() || newSalary.isEmpty() || newJobType.isEmpty() || newLocation.isEmpty()) {
            Toast.makeText(EditJobActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Firebase Database
        jobRef.child(jobId).child("jobTitle").setValue(newTitle);
        jobRef.child(jobId).child("jobDesc").setValue(newDesc);
        jobRef.child(jobId).child("companyName").setValue(newCompany);
        jobRef.child(jobId).child("salary").setValue(newSalary);
        jobRef.child(jobId).child("jobType").setValue(newJobType);
        jobRef.child(jobId).child("location").setValue(newLocation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditJobActivity.this, "Job updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditJobActivity.this, MyJobs.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditJobActivity.this, "Failed to update job", Toast.LENGTH_SHORT).show());
    }
}
