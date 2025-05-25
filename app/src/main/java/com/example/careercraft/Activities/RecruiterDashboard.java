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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careercraft.Adapter.JobSeekerAdapter;
import com.example.careercraft.Fragments.HomeFragment;
import com.example.careercraft.Fragments.JobFragment;
import com.example.careercraft.Fragments.ProfileFragment;
import com.example.careercraft.Fragments.SearchFragment;
import com.example.careercraft.Model.JobSeeker;
import com.example.careercraft.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecruiterDashboard extends AppCompatActivity {

    ImageView homeicon, searchicon, jobicon, profileicon;
    FragmentManager FM;
    FragmentTransaction FT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter_dashboard);

        homeicon = findViewById(R.id.homeicon);
        searchicon = findViewById(R.id.searchicon);
        jobicon = findViewById(R.id.jobicon);
        profileicon = findViewById(R.id.profileicon);

        FM=getSupportFragmentManager();

        // Set up navigation icons
        homeicon.setOnClickListener(view -> {
            FT=FM.beginTransaction();
            FT.replace(R.id.box, HomeFragment.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack("HomeFragment")
                    .commit();
        });

        searchicon.setOnClickListener(view -> {
            FT=FM.beginTransaction();
            FT.replace(R.id.box, SearchFragment.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack("SearchFragment")
                    .commit();
        });

        jobicon.setOnClickListener(view -> {
            FT=FM.beginTransaction();
            FT.replace(R.id.box, JobFragment.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack("JobFragment")
                    .commit();
        });

        profileicon.setOnClickListener(view -> {
            FT=FM.beginTransaction();
            FT.replace(R.id.box, ProfileFragment.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack("ProfileFragment")
                    .commit();
        });

    }

}
