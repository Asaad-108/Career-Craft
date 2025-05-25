package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.careercraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        auth= FirebaseAuth.getInstance();
        if(!checkIfLoggedIn()){
            new Handler().postDelayed(()->{
                Intent i=new Intent(SplashActivity.this,InfoActivity.class);
                startActivity(i);
                finish();
            },3000);
        }
    }
    boolean checkIfLoggedIn(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Recruiter");
        FirebaseUser current = auth.getCurrentUser();
        if(current!=null) {
            String id = current.getUid();
            ref.child(id).child("role").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String role = task.getResult().getValue(String.class);
                    if ("Recruiter".equals(role)) {
                        Intent i=new Intent(SplashActivity.this,RecruiterDashboard.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, "Failed to check role", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }
        return false;
    }
}