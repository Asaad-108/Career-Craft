package com.example.careercraft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careercraft.Fragments.HomeFragment;
import com.example.careercraft.Model.JobSeeker;
import com.example.careercraft.R;

import java.util.List;

public class JobSeekerAdapter extends RecyclerView.Adapter<JobSeekerAdapter.ViewHolder> {
    List<JobSeeker> users;
    Context context;
    public JobSeekerAdapter(Context context, List<JobSeeker> users){
        this.users=users;
        if(context!=null){
            this.context=context;
        }
        else{
            throw new IllegalArgumentException("Context cannot be null");
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobSeeker temp=users.get(position);
        if (temp != null) {
            holder.name.setText(temp.getName() != null ? temp.getName() : "N/A");
            holder.location.setText(temp.getLocation() != null ? temp.getLocation() : "N/A");
            holder.skill.setText(temp.getSkill() != null ? temp.getSkill() : "N/A");
            holder.degree.setText(temp.getDegree() != null ? temp.getDegree() : "N/A");
            holder.email.setText(temp.getEmail() != null ? temp.getEmail() : "N/A");
            holder.phone.setText(temp.getPhone() != null ? temp.getPhone() : "N/A");
        } else {
            Toast.makeText(context, "Error loading job data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,skill,degree,location,phone;
        public ViewHolder(View v){
            super(v);
            name=v.findViewById(R.id.userName);
            email=v.findViewById(R.id.email);
            skill=v.findViewById(R.id.skills);
            degree=v.findViewById(R.id.degree);
            location=v.findViewById(R.id.loc);
            phone=v.findViewById(R.id.phone);
        }
    }
}
