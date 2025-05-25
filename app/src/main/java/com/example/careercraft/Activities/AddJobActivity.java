package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.careercraft.Model.Job;
import com.example.careercraft.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJobActivity extends AppCompatActivity {
    EditText name,location,desc,company,salary,jobType,search;
    String Name,Location,Desc,Company,Salary,JobType;
    FirebaseAuth auth;
    DatabaseReference ref;
    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_job);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        desc=findViewById(R.id.desc);
        company=findViewById(R.id.company);
        salary=findViewById(R.id.salary);
        jobType=findViewById(R.id.jobType);
        add=findViewById(R.id.add);
        auth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference("Job");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=name.getText().toString();
                Location=location.getText().toString();
                Desc=desc.getText().toString();
                Company=company.getText().toString();
                Salary=salary.getText().toString();
                JobType=jobType.getText().toString();
                if(Name.isEmpty()||Location.isEmpty()||Desc.isEmpty()||Company.isEmpty()||Salary.isEmpty()||JobType.isEmpty()){
                    Toast.makeText(AddJobActivity.this, "Enter Credentials !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Job j=new Job(Name,Location,Desc,Company,Salary,JobType);
                    saveJob(j);

                }
            }
        });
    }
    public void saveJob(Job job){
        String jobId=ref.push().getKey();
        String recruiterId=auth.getCurrentUser().getUid();
        if(jobId!=null && recruiterId!= null){
            job.setJobId(jobId);
            job.setRecruiterId(recruiterId);
            ref.child(jobId).setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddJobActivity.this, "Job Added Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddJobActivity.this, MyJobs.class));
                        finish();
                    } else {
                        Toast.makeText(AddJobActivity.this, "Failed to add job!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}