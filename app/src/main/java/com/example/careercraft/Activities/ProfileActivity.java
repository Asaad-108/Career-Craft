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

import com.example.careercraft.Model.JobSeeker;
import com.example.careercraft.Model.Recruiter;
import com.example.careercraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView name,email,skills,degree,phone,location,btnAddAob,btnLogout;
    FirebaseAuth auth;
    ImageView homeicon,searchicon,jobicon,profileicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        name = findViewById(R.id.uname);
        email = findViewById(R.id.uemail);
        skills = findViewById(R.id.uskills);
        degree = findViewById(R.id.udegree);
        phone = findViewById(R.id.uphone);
        location = findViewById(R.id.ulocation);
        btnLogout = findViewById(R.id.btnLogout);
        homeicon = findViewById(R.id.homeicon);
        searchicon = findViewById(R.id.searchicon);
        jobicon = findViewById(R.id.jobicon);
        profileicon = findViewById(R.id.profileicon);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currUser = auth.getCurrentUser();
        if (currUser == null) {
            Toast.makeText(this, "Data not loaded !", Toast.LENGTH_SHORT).show();
        } else {
            String id = auth.getCurrentUser().getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("JobSeeker").child(id);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Toast.makeText(ProfileActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JobSeeker user = snapshot.getValue(JobSeeker.class);
                    if (user != null) {
                        Toast.makeText(ProfileActivity.this, "Data Loaded: " + user.getName(), Toast.LENGTH_SHORT).show();
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        skills.setText(user.getSkill());
                        degree.setText(user.getDegree());
                        location.setText(user.getLocation());
                        phone.setText(user.getPhone());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            homeicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            searchicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProfileActivity.this, SearchActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            jobicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ProfileActivity.this, JobListActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}