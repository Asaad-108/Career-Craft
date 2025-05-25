package com.example.careercraft.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.careercraft.Activities.RecruiterDashboard;
import com.example.careercraft.Adapter.JobSeekerAdapter;
import com.example.careercraft.Model.JobSeeker;
import com.example.careercraft.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference ref;
    List<JobSeeker> users;
    List<JobSeeker> filteredUsers;
    JobSeekerAdapter adp;
    EditText search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.userRecycler);
        search=view.findViewById(R.id.searchUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        users = new ArrayList<>();
        filteredUsers = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("JobSeeker");

        adp = new JobSeekerAdapter(getContext(), filteredUsers);  // Use filteredUsers
        recyclerView.setAdapter(adp);

        fetchUsers();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        // Inflate the layout for this fragment
        return view;
    }
    // Fetch job seekers from Firebase
    public void fetchUsers() {
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                filteredUsers.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    JobSeeker temp = userSnapshot.getValue(JobSeeker.class);
                    if (temp != null) {
                        users.add(temp);
                        filteredUsers.add(temp);
                    }
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void filterUser(String query) {
        filteredUsers.clear();
        if (query.isEmpty()) {
            filteredUsers.addAll(users);
        } else {
            for (JobSeeker user : users) {
                if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                        user.getSkill().toLowerCase().contains(query.toLowerCase()) ||
                        user.getDegree().toLowerCase().contains(query.toLowerCase()) ||
                        user.getLocation().toLowerCase().contains(query.toLowerCase())) {
                    filteredUsers.add(user);
                }
            }
        }
        adp.notifyDataSetChanged();
    }

}