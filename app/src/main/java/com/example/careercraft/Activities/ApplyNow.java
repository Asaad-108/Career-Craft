package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class ApplyNow extends AppCompatActivity {
    Intent intent;
    String id;
    DatabaseReference ref;
    DatabaseReference applyRef;
    FirebaseAuth auth;
    Job job;
    TextView name,desc,companyName,salary,jobType,location,applyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apply_now);
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
        applyBtn=findViewById(R.id.applyBtn);
        auth=FirebaseAuth.getInstance();

        intent = getIntent();
        id=intent.getStringExtra("jobid");
        if (id != null) {
            ref = FirebaseDatabase.getInstance().getReference("Job").child(id);
            fetchJobDetails();
        } else {
            Toast.makeText(this, "Job ID not found", Toast.LENGTH_SHORT).show();
        }
        applyRef=FirebaseDatabase.getInstance().getReference("Applied Jobs");
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyForJob(job);
                Intent i= new Intent(ApplyNow.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void applyForJob(Job job) {
        String userId = auth.getCurrentUser().getUid();

        if (job != null && userId != null) {
            DatabaseReference userJobRef = applyRef.child(userId).child(job.getJobId());

            userJobRef.setValue(job).addOnSuccessListener(aVoid ->
                    Toast.makeText(ApplyNow.this, "Job Applied Successfully!", Toast.LENGTH_SHORT).show()
            ).addOnFailureListener(e ->
                    Toast.makeText(ApplyNow.this, "Failed to Apply. Try Again!", Toast.LENGTH_SHORT).show()
            );

        } else {
            Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
        }
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
                        Toast.makeText(ApplyNow.this, "Job details not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ApplyNow.this, "No job data found ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ApplyNow.this, "Failed to load job details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}