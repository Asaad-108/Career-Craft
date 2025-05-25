package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.careercraft.Model.Job;
import com.example.careercraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobDetail extends AppCompatActivity {
    Intent intent;
    String id;
    DatabaseReference ref;
    DatabaseReference applyRef;
    FirebaseAuth auth;
    Job job;
    TextView name,desc,companyName,salary,jobType,location,btnEdit,btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_detail);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        name=findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        companyName=findViewById(R.id.companyName);
        salary=findViewById(R.id.salary);
        jobType=findViewById(R.id.jobType);
        location=findViewById(R.id.location);
        btnEdit=findViewById(R.id.btnEdit);
        btnDel=findViewById(R.id.btnDel);
        auth=FirebaseAuth.getInstance();

        intent = getIntent();
        id=intent.getStringExtra("jobid");
        if (id != null) {
            ref = FirebaseDatabase.getInstance().getReference("Job").child(id);
            fetchJobDetails();
        } else {
            Toast.makeText(this, "Job ID not found", Toast.LENGTH_SHORT).show();
        }
        btnEdit.setOnClickListener(v -> editJob());

        // Delete Job
        btnDel.setOnClickListener(v -> confirmDelete());
    }
    private void editJob() {
        Intent editIntent = new Intent(JobDetail.this, EditJobActivity.class);
        editIntent.putExtra("jobId", id);
        editIntent.putExtra("jobTitle", name.getText().toString());
        editIntent.putExtra("jobDesc", desc.getText().toString());
        editIntent.putExtra("companyName", companyName.getText().toString());
        editIntent.putExtra("salary", salary.getText().toString());
        editIntent.putExtra("jobType", jobType.getText().toString());
        editIntent.putExtra("location", location.getText().toString());
        startActivity(editIntent);
    }

    // 🗑 Show Confirmation Dialog before Deleting
    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Job")
                .setMessage("Are you sure you want to delete this job?")
                .setPositiveButton("Yes", (dialog, which) -> deleteJob())
                .setNegativeButton("No", null)
                .show();
    }

    // 🗑 Delete Job from Firebase
    private void deleteJob() {
        ref.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(JobDetail.this, "Job deleted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(JobDetail.this, MyJobs.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(JobDetail.this, "Failed to delete job", Toast.LENGTH_SHORT).show());
    }
    private void fetchJobDetails() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    job = snapshot.getValue(Job.class);

                    if (job != null) {
                        name.setText(job.getName());
                        desc.setText( job.getDesc());
                        companyName.setText( job.getCompany());
                        salary.setText(job.getSalary());
                        location.setText(job.getLocation());
                        jobType.setText(job.getJobtype());
                    } else {
                        Toast.makeText(JobDetail.this, "Job details not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobDetail.this, "No job data found for this job ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobDetail.this, "Failed to load job details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}