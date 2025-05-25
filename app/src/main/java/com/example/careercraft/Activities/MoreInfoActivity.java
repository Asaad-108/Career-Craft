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

public class MoreInfoActivity extends AppCompatActivity {
    EditText skills,degree,location,Phone;
    TextView btnContinue;
    FirebaseAuth auth;
    DatabaseReference recruiter= FirebaseDatabase.getInstance().getReference().child("JobSeeker");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_more_info);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        skills=findViewById(R.id.skills);
        degree=findViewById(R.id.degree);
        location=findViewById(R.id.userLoc);
        Phone=findViewById(R.id.userPhone);
        btnContinue=findViewById(R.id.userContinue);
        auth=FirebaseAuth.getInstance();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Skills,Degree,Location,phone;
                Skills=skills.getText().toString();
                Degree=degree.getText().toString();
                Location=location.getText().toString();
                phone=Phone.getText().toString();
                String id=auth.getCurrentUser().getUid();
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("JobSeeker").child(id);

                ref.child("skill").setValue(Skills);
                ref.child("degree").setValue(Degree);
                ref.child("location").setValue(Location);
                ref.child("phone").setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MoreInfoActivity.this, "Details Saved !", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MoreInfoActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(MoreInfoActivity.this, "Failed to save details !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}