package com.example.elias.industrialads;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText signup_emailET,signup_passwordET,signup_cpasswordET;
    private Button btn_registerUser;
    private String email,password,cpassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        firebaseAuth = FirebaseAuth.getInstance();

        signup_emailET = findViewById(R.id.signup_emailET_id);
        signup_passwordET = findViewById(R.id.signup_passwordET_id);
        signup_cpasswordET = findViewById(R.id.signup_cpasswordET_id);
        btn_registerUser = findViewById(R.id.btn_registerUser);

        btn_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = signup_emailET.getText().toString().trim();
                password = signup_passwordET.getText().toString().trim();
                cpassword = signup_cpasswordET.getText().toString().trim();
                
                if(email.isEmpty() || password.isEmpty() || cpassword.isEmpty()){
                    Toast.makeText(RegisterUserActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                    
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUserActivity.this, "Account has been created successfully.", Toast.LENGTH_SHORT).show();
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(RegisterUserActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void haveAccount(View view) {
        Intent startIntent  = new Intent(RegisterUserActivity.this,StartActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent);
    }
}
