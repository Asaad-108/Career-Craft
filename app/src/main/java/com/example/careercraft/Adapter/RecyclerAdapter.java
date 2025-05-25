package com.example.careercraft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careercraft.Interface.JobClick;
import com.example.careercraft.Model.Job;
import com.example.careercraft.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Job> jobs;
    private Context context;
    private JobClick jobClick;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job temp = jobs.get(position);

        if (temp != null) {
            holder.name.setText(temp.getName() != null ? temp.getName() : "N/A");
            holder.location.setText(temp.getLocation() != null ? temp.getLocation() : "N/A");
            holder.desc.setText(temp.getDesc() != null ? temp.getDesc() : "N/A");
            holder.companyName.setText(temp.getCompany() != null ? temp.getCompany() : "N/A");
            holder.salary.setText(temp.getSalary() != null ? temp.getSalary() : "N/A");
            holder.jobType.setText(temp.getJobtype() != null ? temp.getJobtype() : "N/A");
            if (jobClick != null) {
                holder.itemView.setOnClickListener(view -> jobClick.onJobClick(temp));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (jobs != null) ? jobs.size() : 0;
    }

    public RecyclerAdapter(Context context, List<Job> jobs, JobClick jobClick) {
        if (context != null) {
            this.context = context;
            this.jobs = jobs;
            this.jobClick = jobClick;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, desc, companyName, salary, jobType;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            location = v.findViewById(R.id.location);
            desc = v.findViewById(R.id.desc);
            companyName = v.findViewById(R.id.companyName);
            salary = v.findViewById(R.id.salary);
            jobType = v.findViewById(R.id.jobType);
        }
    }
}
