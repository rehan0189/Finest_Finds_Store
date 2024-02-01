package com.example.finestfindsstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputEditText etUsername,etPassword;
    TextView tvForgotPassword,tvCreateAcc;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        tvCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();

//                String username = etUsername.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//
//                if (username.isEmpty()||password.isEmpty()){
//                    Toast.makeText(Login.this, "Please Fill requirements", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("users").child(username)
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (snapshot.exists()) {
//                                        String storedPassword = snapshot.child("password").getValue(String.class);
//                                        if (password.equals(storedPassword)) {
//                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                            Intent i=new Intent(Login.this, MainActivity.class);
//                                            startActivity(i);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        Toast.makeText(Login.this, "Username not registered", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                    Toast.makeText(Login.this, "Data error", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//
//                }
            }



        });

    }
    private void init(){
        etPassword=findViewById(R.id.et_password);
        etUsername=findViewById(R.id.et_username);
        tvForgotPassword=findViewById(R.id.tv_forgotPassword);
        tvCreateAcc = findViewById(R.id.tv_create_acc);
        btnLogin=findViewById(R.id.btn_login);
    }

}