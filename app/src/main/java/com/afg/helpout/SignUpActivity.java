package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afg.helpout.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    // initializing fields
    EditText mEmailRegistration, mPasswordRegistration, mUsernameRegistration;
    Button mButtonSignUp;
    TextView mSignInLink;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        final DatabaseReference users = ref.child("User Information");

        // getting input and fields from activity
        mEmailRegistration = findViewById(R.id.email);
        mPasswordRegistration = findViewById(R.id.password);
        mUsernameRegistration = findViewById(R.id.usernameText);

        mButtonSignUp = findViewById(R.id.signUp);
        mSignInLink = findViewById(R.id.signInLink);

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Current user information. TODO: Add favorites as parameter to user class
                final User user = new User(mUsernameRegistration.getText().toString(),
                        mEmailRegistration.getText().toString(),
                        mPasswordRegistration.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        users.child(mUsernameRegistration.getText().toString()).setValue(user);
                        Toast.makeText(SignUpActivity.this,
                                "Registration is succesful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SignUpActivity.this,
                                "Unable to register. Please try again later.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Listener for Sign in Link at Bottom
        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });

    }

}
