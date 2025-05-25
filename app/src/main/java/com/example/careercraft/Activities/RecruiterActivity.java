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

import com.example.careercraft.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecruiterActivity extends AppCompatActivity {
    EditText companyName,Designation,location,Phone;
    TextView btnContinue;
    FirebaseAuth auth;
    DatabaseReference recruiter= FirebaseDatabase.getInstance().getReference().child("Recruiter");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        companyName=findViewById(R.id.companyName);
        Designation=findViewById(R.id.designation);
        location=findViewById(R.id.location);
        Phone=findViewById(R.id.phone);
        btnContinue=findViewById(R.id.btnContinue);
        auth=FirebaseAuth.getInstance();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CompanyName,designation,Location,phone;
                CompanyName=companyName.getText().toString();
                designation=Designation.getText().toString();
                Location=location.getText().toString();
                phone=Phone.getText().toString();
                String id=auth.getCurrentUser().getUid();
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Recruiter").child(id);

                ref.child("companyName").setValue(CompanyName);
                ref.child("designation").setValue(designation);
                ref.child("location").setValue(Location);
                ref.child("phone").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RecruiterActivity.this, "Details Saved !", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RecruiterActivity.this,RecruiterDashboard.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(RecruiterActivity.this, "Failed to save details !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}