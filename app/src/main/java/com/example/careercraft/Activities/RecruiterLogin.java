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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecruiterLogin extends AppCompatActivity {
    EditText loginemail,loginpassword;
    TextView btnlogin,btnSignUp,btnForget;
    String userEmail,userPass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        loginemail=findViewById(R.id.loginEmail);
        loginpassword=findViewById(R.id.loginPass);
        btnlogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignup);
        btnForget=findViewById(R.id.btnForget);
        auth=FirebaseAuth.getInstance();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail=loginemail.getText().toString();
                userPass=loginpassword.getText().toString();
                auth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i=new Intent(RecruiterLogin.this,RecruiterDashboard.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            String err=task.getException().getMessage();
                            Toast.makeText(RecruiterLogin.this, err+" !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget(view);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RecruiterLogin.this,InfoActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void forget(View v) {
        String userEmail = loginemail.getText().toString();
        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecruiterLogin.this, "Please Check your Email Address", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().getMessage();
                            Toast.makeText(RecruiterLogin.this, err + " !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}