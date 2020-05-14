package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * TODO: Complete Documentation
 */
public class SignUpActivity extends AppCompatActivity {

    // initializing fields
    EditText mEmailRegistration, mPasswordRegistration;
    Button mButtonSignUp;
    TextView mSignInLink;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    /**
     * TODO: Complete Documentation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // getting input and fields from activity
        mEmailRegistration = findViewById(R.id.email);
        mPasswordRegistration = findViewById(R.id.password);

        mButtonSignUp = findViewById(R.id.signUp);
        mSignInLink = findViewById(R.id.signInLink);

        // Firebase Listener
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            /**
             * TODO: Complete Documentation
             *
             * @param firebaseAuth
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        // Listener for Sign Up Button
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            /**
             * TODO: Complete Documentation
             *
             * @param view
             */
            @Override
            public void onClick(View view) {

                // Converting input from user to String
                String email = mEmailRegistration.getText().toString();
                String password = mPasswordRegistration.getText().toString();

                // Checking if both fields are empty
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                // Checking if email is empty
                else if (email.isEmpty()) {
                    mEmailRegistration.setError("Please enter your email.");
                    mEmailRegistration.requestFocus();
                }
                // Checking if password is empty
                else if (password.isEmpty()) {
                    mPasswordRegistration.setError("Please enter your password.");
                    mPasswordRegistration.requestFocus();
                }
                else {
                    // If both fields are NOT empty: Register User
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Report Error
                            if (!task.isSuccessful())
                                Toast.makeText(SignUpActivity.this, "Sign Up Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            // Go to ConfirmLoginActivity
                            else
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                    });
                }
                
            }
        });

        // Listener for Sign in Link at Bottom
        mSignInLink.setOnClickListener(new View.OnClickListener() {
            /**
             * TODO: Complete Documentation
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });

    }

}
