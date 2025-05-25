package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.careercraft.Model.Recruiter;
import com.example.careercraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecruiterProfile extends AppCompatActivity {
    TextView name,email,companyName,designation,phone,location,btnAddJob,btnLogout;
    FirebaseAuth auth;
    ImageView homeicon,searchicon,jobicon,profileicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        name=findViewById(R.id.rname);
        email=findViewById(R.id.remail);
        companyName=findViewById(R.id.rcompanyName);
        designation=findViewById(R.id.rdesignation);
        phone=findViewById(R.id.rphone);
        location=findViewById(R.id.rlocation);
        btnAddJob=findViewById(R.id.btnAddJob);
        btnLogout=findViewById(R.id.logout);
        homeicon=findViewById(R.id.homeicon);
        searchicon=findViewById(R.id.searchicon);
        jobicon=findViewById(R.id.jobicon);
        profileicon=findViewById(R.id.profileicon);
        auth=FirebaseAuth.getInstance();
        FirebaseUser currUser=auth.getCurrentUser();
        if(currUser==null){
            Toast.makeText(this, "Data not loaded !", Toast.LENGTH_SHORT).show();
        }
        else{
            String id = auth.getCurrentUser().getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Recruiter").child(id);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Toast.makeText(RecruiterProfile.this, "No data found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Recruiter user = snapshot.getValue(Recruiter.class);
                    if (user != null) {
                        Toast.makeText(RecruiterProfile.this, "Data Loaded: " + user.getName(), Toast.LENGTH_SHORT).show();
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        companyName.setText(user.getCompanyName());
                        designation.setText(user.getDesignation());
                        location.setText(user.getLocation());
                        phone.setText(user.getPhone());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RecruiterProfile.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecruiterProfile.this , AddJobActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(RecruiterProfile.this , RecruiterLogin.class);
                startActivity(i);
                finish();
            }
        });
        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RecruiterProfile.this,RecruiterDashboard.class);
                startActivity(i);
                finish();
            }
        });
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RecruiterProfile.this,RecruiterSearch.class);
                startActivity(i);
                finish();
            }
        });
        jobicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(RecruiterProfile.this, MyJobs.class);
                    startActivity(i);
                    finish();
            }
        });
    }
}