package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class RecruiterSearch extends AppCompatActivity {
    RecyclerView recyclerView;
    JobClick jobClick;
    DatabaseReference ref;
    List<Job> jobs;
    List<Job> filteredJobs;
    RecyclerAdapter adapter;
    EditText search;
    ImageView homeicon,searchicon,jobicon,profileicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter_search);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        homeicon=findViewById(R.id.homeicon);
        searchicon=findViewById(R.id.searchicon);
        jobicon=findViewById(R.id.jobicon);
        profileicon=findViewById(R.id.profileicon);
        search=findViewById(R.id.search_bar);
        recyclerView=findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference("Job");

        jobs = new ArrayList<>();
        filteredJobs = new ArrayList<>();
        adapter = new RecyclerAdapter(this, filteredJobs, null);// Use filteredJobs
        recyclerView.setAdapter(adapter);

        fetchJobs();

        // **Search filter implementation**
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterJobs(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RecruiterSearch.this,RecruiterDashboard.class);
                startActivity(i);
                finish();
            }
        });
        jobicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RecruiterSearch.this,MyJobs.class);
                startActivity(i);
                finish();
            }
        });
        profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecruiterSearch.this, RecruiterProfile.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void fetchJobs() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                filteredJobs.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        jobs.add(job);
                    }
                }
                filteredJobs.addAll(jobs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecruiterSearch.this, "Failed to load jobs", Toast.LENGTH_SHORT);
            }
        });
    }
    private void filterJobs(String query) {
        filteredJobs.clear();
        if (query.isEmpty()) {
            filteredJobs.addAll(jobs);
        } else {
            for (Job job : jobs) {
                if (job.getName().toLowerCase().contains(query.toLowerCase()) ||
                        job.getLocation().toLowerCase().contains(query.toLowerCase()) ||
                        job.getCompany().toLowerCase().contains(query.toLowerCase()) ||
                        job.getJobtype().toLowerCase().contains(query.toLowerCase())||
                        job.getSalary().toLowerCase().contains(query.toLowerCase())) {
                    filteredJobs.add(job);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}