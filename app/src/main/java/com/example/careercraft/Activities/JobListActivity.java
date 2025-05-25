package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careercraft.Adapter.RecyclerAdapter;
import com.example.careercraft.Interface.JobClick;
import com.example.careercraft.Model.Job;
import com.example.careercraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference ref;
    List<Job> jobs;
    List<Job> appliedJobs;
    RecyclerAdapter adp;
    FirebaseAuth auth;
    String userId;
    ImageView homeicon, searchicon, jobicon, profileicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_list);

        homeicon = findViewById(R.id.homeicon);
        searchicon = findViewById(R.id.searchicon);
        jobicon = findViewById(R.id.jobicon);
        profileicon = findViewById(R.id.profileicon);
        recyclerView = findViewById(R.id.jobRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid();
        jobs = new ArrayList<>();
        appliedJobs=new ArrayList<>();
        adp = new RecyclerAdapter(JobListActivity.this, appliedJobs, null);  // Using `this` as JobClick
        recyclerView.setAdapter(adp);

        fetchAppliedJobs();

        homeicon.setOnClickListener(view -> {
            Intent i = new Intent(JobListActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        searchicon.setOnClickListener(view -> {
            Intent i = new Intent(JobListActivity.this, SearchActivity.class);
            startActivity(i);
            finish();
        });

        profileicon.setOnClickListener(view -> {
            Intent i = new Intent(JobListActivity.this, ProfileActivity.class);
            startActivity(i);
            finish();
        });
    }
    private void fetchAppliedJobs() {
        ref = FirebaseDatabase.getInstance().getReference("Applied Jobs").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appliedJobs.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        appliedJobs.add(job);
                    }
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobListActivity.this, "Failed to load applied jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
