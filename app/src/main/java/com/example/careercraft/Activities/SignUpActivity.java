package com.example.careercraft.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.careercraft.Model.Recruiter;
import com.example.careercraft.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,password;
    TextView createAccount,login;
    String Name,Email,Password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        createAccount=findViewById(R.id.createAccount);
        login=findViewById(R.id.login);
        FirebaseApp.initializeApp(this);
        auth=FirebaseAuth.getInstance();
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=name.getText().toString();
                Email=email.getText().toString();
                Password=password.getText().toString();
                if(Name.isEmpty()||Email.isEmpty()||Password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Enter the credentials !", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id=auth.getCurrentUser().getUid();
                                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Recruiter").child(id);
                                Recruiter R=new Recruiter(Name,Email,"","","","");
                                ref.setValue(R).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this, "Saved Successfully !", Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(SignUpActivity.this,RecruiterActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        else{
                                            String err=task.getException().getMessage();
                                            Toast.makeText(SignUpActivity.this, err+" !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                String err=task.getException().getMessage();
                                Toast.makeText(SignUpActivity.this, err+" !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,RecruiterLogin.class);
                startActivity(i);
                finish();
            }
        });
    }
}