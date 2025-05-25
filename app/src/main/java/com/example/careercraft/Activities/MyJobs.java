package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
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

public class MyJobs extends AppCompatActivity implements JobClick{
    RecyclerView recyclerView;
    JobClick jobClick;
    DatabaseReference ref;
    List<Job> jobs;
    RecyclerAdapter adp;
    ImageView homeicon,searchicon,jobicon,profileicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_jobs);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        homeicon=findViewById(R.id.homeicon);
        searchicon=findViewById(R.id.searchicon);
        jobicon=findViewById(R.id.jobicon);
        profileicon=findViewById(R.id.profileicon);
        recyclerView=findViewById(R.id.MyJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref= FirebaseDatabase.getInstance().getReference("Job");
        jobs= new ArrayList<>();
        adp=new RecyclerAdapter(this,jobs,this);
        recyclerView.setAdapter(adp);

        fetchJobs();

        homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyJobs.this,RecruiterDashboard.class);
                startActivity(i);
                finish();
            }
        });
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyJobs.this,RecruiterSearch.class);
                startActivity(i);
                finish();
            }
        });
        profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyJobs.this, RecruiterProfile.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void fetchJobs() {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String id=auth.getCurrentUser().getUid();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    if (job != null) {
                        if(job.getRecruiterId().equals(id)) {
                            jobs.add(job);
                        }
                    }
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyJobs.this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onJobClick(Job job) {
        Intent intent = new Intent(MyJobs.this, JobDetail.class);
        intent.putExtra("jobid", job.getJobId());
        startActivity(intent);
    }
}