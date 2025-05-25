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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JobClick {
    RecyclerView recyclerView;
    DatabaseReference ref;
    List<Job> jobs;
    RecyclerAdapter adp;

    ImageView homeicon, searchicon, jobicon, profileicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeicon = findViewById(R.id.homeicon);
        searchicon = findViewById(R.id.searchicon);
        jobicon = findViewById(R.id.jobicon);
        profileicon = findViewById(R.id.profileicon);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("Job");
        jobs = new ArrayList<>();
        adp = new RecyclerAdapter(MainActivity.this, jobs, this);  // Passing `this` as JobClick
        recyclerView.setAdapter(adp);

        fetchJobs();

        searchicon.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        });

        jobicon.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, JobListActivity.class);
            startActivity(i);
        });

        profileicon.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i);
        });
    }

    public void fetchJobs() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        jobs.add(job);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onJobClick(Job job) {
        Intent intent = new Intent(MainActivity.this, ApplyNow.class);
        intent.putExtra("jobid", job.getJobId());
        startActivity(intent);
    }
}
