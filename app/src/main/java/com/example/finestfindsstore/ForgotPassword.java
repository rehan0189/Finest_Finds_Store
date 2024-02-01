package com.example.finestfindsstore;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {

    TextInputEditText etEmail, etNewPassword;
    Button btnUpdatePassword;
    ImageView btnIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

       init();

        btnIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldEmail = etEmail.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString();

                if (oldEmail.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Please fill all requirements", Toast.LENGTH_SHORT).show();
                } else {
                    String replaceOldEmail = oldEmail.replace(".", ",");
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(replaceOldEmail)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        // Update the password in the database
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Users").child(replaceOldEmail)
                                                .child("Password").setValue(newPassword)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ForgotPassword.this, "Password changed successfully Login again", Toast.LENGTH_SHORT).show();
                                                        Intent i=new Intent(ForgotPassword.this, Login.class);
                                                        startActivity(i);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(ForgotPassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(ForgotPassword.this, "Old Email not registered", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Toast.makeText(ForgotPassword.this, "Data error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    void init(){
        etEmail = findViewById(R.id.et_email);
        etNewPassword = findViewById(R.id.et_password);
        btnUpdatePassword = findViewById(R.id.btn_update_password);
        btnIvBack = findViewById(R.id.btn_back);
    }
}