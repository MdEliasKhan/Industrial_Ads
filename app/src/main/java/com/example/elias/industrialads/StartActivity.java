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

public class StartActivity extends AppCompatActivity {
    private EditText login_emailET,login_passwowdET;
    private Button btn_login;
    private String email,password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseAuth = FirebaseAuth.getInstance();

        login_emailET = findViewById(R.id.login_emailET_id);
        login_passwowdET = findViewById(R.id.login_passwowdET_id);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = login_emailET.getText().toString().trim();
                password = login_passwowdET.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            goAppHome();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(StartActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void goAppHome() {
        Intent startIntent  = new Intent(StartActivity.this,MainActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent);
        finish();
    }

    public void needAccount(View view) {
        Intent startIntent  = new Intent(StartActivity.this,RegisterUserActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent);
    }
}
